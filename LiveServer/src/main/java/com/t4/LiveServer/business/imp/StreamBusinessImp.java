package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.Wowza.WOWZAStreamBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.entryParam.base.Stream.StreamingForward;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.wowza.WowzaStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class StreamBusinessImp implements StreamBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    @Autowired
    WOWZAStreamBusiness wowzaStreamBusiness;
    @Autowired
    FacebookLiveBusiness facebookLiveBusiness;

    @Override
    public Object create(CreatingStreamEntryParams entryParams) {
        try {
            WowzaStream wowzaLive = wowzaStreamBusiness.create("test");
            if (entryParams.forwards.size() > 0) {
                entryParams.forwards.forEach(fw -> {
                    fw.platform = fw.platform.toUpperCase();
                    if (StreamingForward.ForwardPlatform.FACEBOOK.name().toUpperCase().equals(fw.platform)) {
                        FacebookConfig fbConfig = new FacebookConfig();
                        fbConfig.accessToken = fw.token;
                        Object fbData = facebookLiveBusiness.individualCreate(fbConfig);
                    }
                    else if (StreamingForward.ForwardPlatform.YOUTUBE.name().toUpperCase().equals(fw.platform)) {

                    }
                    else if (StreamingForward.ForwardPlatform.TWITCH.name().toUpperCase().equals(fw.platform)) {

                    }
                    else if (StreamingForward.ForwardPlatform.DISCORD.name().toUpperCase().equals(fw.platform)) {

                    }
                });
            }
        } catch (Exception e) {

        }
        return null;
    }
}
