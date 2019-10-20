package com.t4.LiveServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/ANoun"})
public class ANounController {

    //always clearly declare method
    @RequestMapping(value = {"/hello"} , method = RequestMethod.GET)
    public String verb(){
        return "";
    }
}