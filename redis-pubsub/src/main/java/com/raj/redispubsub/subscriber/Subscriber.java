package com.raj.redispubsub.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class Subscriber implements MessageListener {

    Logger logger = LoggerFactory.getLogger(Subscriber.class);

    @Override
    public void onMessage(Message message, byte[] bytes) {
        logger.info("Consumed " + message.toString());
    }
}
