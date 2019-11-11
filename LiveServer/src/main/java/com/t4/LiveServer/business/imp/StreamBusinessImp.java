package com.t4.LiveServer.business.imp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.wowza.WOWZAStreamBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.entryParam.base.Stream.StreamingForward;
import com.t4.LiveServer.entryParam.base.Wowza.AdditionOutputStreamTargetToTransCoderEntryParam;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.ForwardStream;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.facebook.LiveStream;
import com.t4.LiveServer.model.wowza.ListWowzaStream;
import com.t4.LiveServer.model.wowza.StreamOutput;
import com.t4.LiveServer.model.wowza.StreamTarget;
import com.t4.LiveServer.model.wowza.WowzaStream;
import com.t4.LiveServer.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RunAs;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StreamBusinessImp implements StreamBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    @Autowired
    private WOWZAStreamBusiness wowzaStreamBusiness;
    @Autowired
    private FacebookLiveBusiness facebookLiveBusiness;

    @Autowired
    private StreamRepository streamRepository;

    @Override
    public Stream create(CreatingStreamEntryParams entryParams) {

        WowzaStream liveWowza = null;
        try {
            liveWowza = wowzaStreamBusiness.create(entryParams);
            if (liveWowza == null) {
                liveWowza = getReplacementLive(entryParams);
            }
            if (liveWowza == null)
                return null;
            List<StreamTarget> forwardTargets = new ArrayList<>();
            if (entryParams.forwards != null) {
                for (StreamingForward fw : entryParams.forwards) {
                    StreamingForward.ForwardPlatform platform = StreamingForward
                            .ForwardPlatform.valueOf(fw.platform.toUpperCase());
                    if (StreamingForward.ForwardPlatform.FACEBOOK == platform) {
                        StreamTarget toFacebookOutput = this.createStreamTargetToFacebookStream(fw);
                        forwardTargets.add(toFacebookOutput);
                    }
                    if (StreamingForward.ForwardPlatform.YOUTUBE == platform) {
                        StreamTarget toYoutubeOutput = this.createStreamTargetToYoutubeOutput(fw);
                        forwardTargets.add(toYoutubeOutput);
                    }
                    if (StreamingForward.ForwardPlatform.DISCORD == platform) {
                        StreamTarget toDiscordOutput = this.createStreamTargetToDiscordOutput(fw);
                        forwardTargets.add(toDiscordOutput);
                    }
                    if (StreamingForward.ForwardPlatform.TWITCH == platform) {
                        StreamTarget toTwitchOutput = this.createStreamTargetToTwitchOutput(fw);
                        forwardTargets.add(toTwitchOutput);
                    }
                }
            }
            if (!forwardTargets.isEmpty()) {
                StreamOutput passThroughTranCoder = wowzaStreamBusiness.
                        getStreamOutput(liveWowza.id, "Passthrough");
                for (StreamTarget target : forwardTargets) {
                    if (target == null) continue;
                    wowzaStreamBusiness.addOutputStreamTargetToTransCoderOfAStream(
                            new AdditionOutputStreamTargetToTransCoderEntryParam(liveWowza.id,
                                    passThroughTranCoder.id, target.id));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Stream rs = new Stream(liveWowza);
        rs = streamRepository.saveAndFlush(rs);
        return rs;
    }

    private StreamTarget createStreamTargetToTwitchOutput(StreamingForward fw) {
        return null;
    }

    private StreamTarget createStreamTargetToDiscordOutput(StreamingForward fw) {
        return null;
    }

    private StreamTarget createStreamTargetToYoutubeOutput(StreamingForward fw) {
        return null;
    }

    private StreamTarget createStreamTargetToFacebookStream(StreamingForward fw) {
        FacebookConfig fbConfig = new FacebookConfig();
        fbConfig.accessToken = fw.token;
        ForwardStream liveFbData = facebookLiveBusiness.individualCreate(fbConfig);
        if (null == liveFbData) {
            return null;
        }
        return wowzaStreamBusiness.createCustomStreamTarget(
                new StreamTarget(liveFbData));
    }


    @Override
    public Object start(String id) {
        Stream requested = streamRepository.findById(Integer.parseInt(id)).get();
        if (requested == null)
            return null;
        WowzaStream wowzaClosed = wowzaStreamBusiness.start(requested.getWowzaId());
        if(wowzaClosed == null || !"starting".equals(wowzaClosed.state))
            return null;
        try {
            String fullyUrl = WowzaStream.URL_LIVE_STREAM + "/" + requested.getWowzaId() + "/state";
            while (true) {
                Thread.sleep(1000);
                ResponseEntity<String> result = restTemplate.exchange(fullyUrl,
                        HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
                WowzaStream stream = JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE,
                        result.getBody(), WowzaStream.class);
                System.out.println(result.getBody());
                if (stream != null && stream.state.equals("started")) {
                    requested.setStatus(1);
                    Stream rs = streamRepository.saveAndFlush(requested);
                    System.out.println(rs);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return requested;
    }

    @Override
    public Object stop(String id) {
        Stream requested = streamRepository.findById(Integer.parseInt(id)).get();
        if (requested == null)
            return null;
        wowzaStreamBusiness.stop(requested.getWowzaId());
        requested.setStatus(-1);
        requested = streamRepository.saveAndFlush(requested);
        return requested;
    }

    private WowzaStream getReplacementLive(CreatingStreamEntryParams entry) {
        List<WowzaStream> existedStreams = wowzaStreamBusiness.fetchAll();
        Optional<WowzaStream> replaceStream = existedStreams
                .stream().filter(ws -> !ws.isRecording())
                .findAny();
        if (replaceStream.isPresent()) {
            WowzaStream op = wowzaStreamBusiness.fetchOne(replaceStream.get().id);
            op.name = entry.name;
            wowzaStreamBusiness.update(op);
            return op;
        }
        return null;
    }
}
