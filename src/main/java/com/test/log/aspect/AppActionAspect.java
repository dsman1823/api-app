package com.test.log.aspect;

import com.test.log.controller.SSEController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class AppActionAspect {
    @Before("@annotation(com.test.log.aspect.AppAction) && execution(public * *(..)) && args(..)")
    public void emit(JoinPoint joinPoint) {

        List<SseEmitter> sseEmitterListToRemove = new ArrayList<>();
        SSEController.emitters.forEach(emitter -> {
            try {
                emitter.send(joinPoint.getSignature().toString(), MediaType.TEXT_PLAIN);
            } catch (IOException ex) {
                emitter.complete();
                sseEmitterListToRemove.add(emitter);
                ex.printStackTrace();
            }
        });
        SSEController.emitters.removeAll(sseEmitterListToRemove);

    }
}
