package org.example.controller;

import org.example.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/application")
    public String sendNotification(DeviceDTO deviceDTO) {
        // Push notifications to front-end
        template.convertAndSend("/all/messages", deviceDTO);

        return "Notifications successfully sent to Angular !";
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody DeviceDTO deviceDTO) {
        template.convertAndSend("/topic/message", deviceDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload DeviceDTO deviceDTO) {
        // receive message from client
    }


    @SendTo("/topic/message")
    public DeviceDTO broadcastMessage(@Payload DeviceDTO deviceDTO) {
        return deviceDTO;
    }
}
