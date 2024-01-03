package com.ddcsoftware;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//NOTE: This class is used only to test that Continues Delivery is working
@RestController
public class PingPongController {
    record PingPong(String result){};

    //Used so when we send a "ping" (access url with GET) we should receive a Pong
    @GetMapping("/ping")
    public PingPong getPingPong(){
        return new PingPong("Pong");};
}
