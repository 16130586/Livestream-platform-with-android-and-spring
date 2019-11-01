package com.t4.LiveServer.entryParam.base.Stream;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StreamingForward {
    public String platform;
    public String token;

    public enum ForwardPlatform {
        FACEBOOK, YOUTUBE, TWITCH, DISCORD
    }
}