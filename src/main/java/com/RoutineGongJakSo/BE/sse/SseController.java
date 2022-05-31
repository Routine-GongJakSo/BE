package com.RoutineGongJakSo.BE.sse;

import com.RoutineGongJakSo.BE.client.checkIn.model.CheckIn;
import com.RoutineGongJakSo.BE.client.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class SseController {
    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    @GetMapping(value = "/api/subscribe/{id}" , produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable String id) {
        SseEmitter emitter = new SseEmitter();
        CLIENTS.put(id, emitter);

        emitter.onTimeout(() -> CLIENTS.remove(id));
        emitter.onCompletion(() -> CLIENTS.remove(id));
        return emitter;
    }

    public void publishCheckIn(CheckIn checkin) {
        Set<String> deadIds = new HashSet<>();

        CLIENTS.forEach((id, emitter) -> {
            try {
                emitter.send(new SseDto.CheckInResponse(checkin), MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(id);
                log.warn("disconnected id : {}", id);
            }
        });

        deadIds.forEach(CLIENTS::remove);
    }

    public void publishCheckOut(User user) {
        Set<String> deadIds = new HashSet<>();

        CLIENTS.forEach((id, emitter) -> {
            try {
                emitter.send(new SseDto.CheckOutResponse(user), MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(id);
                log.warn("disconnected id : {}", id);
            }
        });

        deadIds.forEach(CLIENTS::remove);
    }
}