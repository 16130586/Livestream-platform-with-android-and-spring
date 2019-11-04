package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.WOWZAStreamBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.entryParam.base.Stream.StreamingForward;
import com.t4.LiveServer.entryParam.base.Wowza.AdditionOutputStreamTargetToTransCoderEntryParam;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.facebook.LiveStream;
import com.t4.LiveServer.model.wowza.StreamOutput;
import com.t4.LiveServer.model.wowza.StreamTarget;
import com.t4.LiveServer.model.wowza.WowzaStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class StreamBusinessImp implements StreamBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    @Autowired
    WOWZAStreamBusiness wowzaStreamBusiness;
    @Autowired
    FacebookLiveBusiness facebookLiveBusiness;

    @Override
    public Object create(CreatingStreamEntryParams entryParams) {
        WowzaStream liveWowza = null;
        try {
            // tao wowza stream
            liveWowza = null;//wowzaStreamBusiness.create("that sieu cap dep trai");
//             trong th gap limit ve thoi gian tao -- thi tra ve null neu network van oke
//             lay cai cu
            if (liveWowza == null) {
                liveWowza = getReplacementLive(entryParams);
            }
            // sau khi tao xong kt xem, co forward qua nen tang nao k
            if (entryParams.forwards.size() > 0) {
                // neu co
                for(int i = 0 ; i < entryParams.forwards.size() ; i++){
                    StreamingForward fw = entryParams.forwards.get(i);
                    fw.platform = fw.platform.toUpperCase();

                    if (StreamingForward.ForwardPlatform.FACEBOOK.name().toUpperCase().equals(fw.platform)) {
                        FacebookConfig fbConfig = new FacebookConfig();
                        fbConfig.accessToken = fw.token;
                        // tao live stream o facebook
                        LiveStream liveFbData = facebookLiveBusiness.individualCreate(fbConfig);
                        if(null == liveFbData){
//                            throw new Exception("you'r live now ! not accepted for another live!");
                            continue;
                        }
                        // stream rtmp url , stream key
                        System.out.println(liveFbData);
                        // lay ra dl de tao custom stream target

                        //sercure stream url
                        String cusTargetName = "test";
                        String primaryUrl = liveFbData.secureStreamUrl.substring("rtmps://".length()
                                , liveFbData.secureStreamUrl.indexOf("/rtmp/") + "/rtmp/".length());
                        String streamName = liveFbData.
                                secureStreamUrl.substring("rtmps://".length() + primaryUrl.length());
                        StreamTarget toFacebookOutput = wowzaStreamBusiness.createCustomStreamTarget(
                                new StreamTarget(
                                        StreamTarget.PROVIDER_RTMPS, cusTargetName,
                                        primaryUrl, streamName));

                        List<StreamOutput> outputs = wowzaStreamBusiness.fetchAllOutputOfATransCoder(liveWowza.id);
                        StreamOutput passThroughTranCoder = outputs.stream().filter(transCoder -> {
                            return transCoder.name.contains("Passthrough");
                        }).iterator().next();
                        wowzaStreamBusiness.addOutputStreamTargetToTransCoderOfAStream(
                                new AdditionOutputStreamTargetToTransCoderEntryParam(liveWowza.id,
                                        passThroughTranCoder.id, toFacebookOutput.id));
                    } else if (StreamingForward.ForwardPlatform.YOUTUBE.name().toUpperCase().equals(fw.platform)) {

                    } else if (StreamingForward.ForwardPlatform.TWITCH.name().toUpperCase().equals(fw.platform)) {

                    } else if (StreamingForward.ForwardPlatform.DISCORD.name().toUpperCase().equals(fw.platform)) {

                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return liveWowza;
    }

    @Override
    public Object start(String id) {
        return wowzaStreamBusiness.start(id);
    }

    @Override
    public Object stop(String id) {
        return wowzaStreamBusiness.stop(id);
    }

    private WowzaStream getReplacementLive(CreatingStreamEntryParams entry) {
        List<WowzaStream> existedStreams = wowzaStreamBusiness.fetchAll();
        Optional<WowzaStream> replaceStream = existedStreams
                .stream().filter(ws -> !ws.isRecording())
                .findAny();
        if (replaceStream.isPresent()) {
            WowzaStream op =wowzaStreamBusiness.fetchOne(replaceStream.get().id);
            op.name = "that dep trai sieu cap vu tru!";
            wowzaStreamBusiness.update(op);
            return op;
        }
        return null;
    }
}
