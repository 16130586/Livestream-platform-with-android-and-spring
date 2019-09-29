package com.t4.LiveServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/stream"})
public class StreamController {
    public StreamController() {
    }

    @RequestMapping({"/hello"})
    public abc getHello() {
        return new abc();
    }
}
