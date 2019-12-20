package com.t4.LiveServer;

import com.t4.LiveServer.model.StreamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LiveServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(LiveServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LiveServerApplication.class, args);
	}

}
