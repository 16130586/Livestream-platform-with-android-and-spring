package com.t4.LiveServer.dependency;

import com.t4.LiveServer.business.imp.facebook.FacebookLiveBusinessImp;
import com.t4.LiveServer.business.imp.StreamBusinessImp;
import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.StreamBusiness;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Business {

    @Bean
    public FacebookLiveBusiness getFacebookLiveBusiness(){
        return new FacebookLiveBusinessImp();
    }
    @Bean
    public StreamBusiness getStreamBusiness(){
        return new StreamBusinessImp();
    }
}
