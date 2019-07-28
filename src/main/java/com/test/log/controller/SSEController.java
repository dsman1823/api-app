package com.test.log.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SSEController {
    public static final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());

    @RequestMapping(path = "/stream", method = RequestMethod.GET)
    public SseEmitter stream() {

        SseEmitter emitter = new SseEmitter();

        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));

        return emitter;
    }
}