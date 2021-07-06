package com.raj.redispubsub.publisher;

import com.raj.redispubsub.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Publisher {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ChannelTopic channelTopic;

    @PostMapping("/publish")
    public String publishMessage(@RequestBody final Notification notification) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), notification.toString());
        return "Event published";
    }
}
