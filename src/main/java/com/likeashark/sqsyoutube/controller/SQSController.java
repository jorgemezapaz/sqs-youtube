package com.likeashark.sqsyoutube.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SQSController {

    @Value("${cloud.aws.end-point.uri}")
    private String sqsUrl;

    private final QueueMessagingTemplate queueMessagingTemplate;

    public SQSController(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @GetMapping("/push/{msg}")
    public void pushMessage(@PathVariable("msg") String msg){
       queueMessagingTemplate.send(sqsUrl, MessageBuilder.withPayload(msg).build());
   }

   @SqsListener("queue-youtube")
   public void listenerMessages(String message){
        System.out.println("Queue message: " + message);
   }
}
