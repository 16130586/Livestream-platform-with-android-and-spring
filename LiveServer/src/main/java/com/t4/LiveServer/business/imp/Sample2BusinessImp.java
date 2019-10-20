package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.Sample2Business;
import com.t4.LiveServer.business.interfaze.SampleBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sample2BusinessImp implements Sample2Business {
    @Autowired
    SampleBusiness sampleBusiness;
    public Sample2BusinessImp(){

    }
    @Override
    public String sample2Echo() {
        return "Sample 2 " +  sampleBusiness.sampleEcho();
    }
}
