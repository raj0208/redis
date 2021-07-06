package com.raj.redispubsub.config;

import com.raj.redispubsub.subscriber.Subscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisCpnfig {
    @Value("${redis.hostname}")
    String REDIS_HOSTNAME;

    @Value("${redis.port}")
    int REDIS_PORT;

    @Value("{publisher.topic}")
    String PUBLISH_TOPIC;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(REDIS_HOSTNAME);
        configuration.setPort(REDIS_PORT);
        return new JedisConnectionFactory(configuration);
    }

    /**
     * Redis Template
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory());
            template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
            return template;
    }

    /**
     * Redis topic where all messages will be published
     * @return ChannelTpoic pointing to the publisher topicname
     */
    @Bean
    public ChannelTopic redisTopic() {
        return new ChannelTopic(PUBLISH_TOPIC);
    }

    /**
     * Message listener/subscriber
     * @return
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new Subscriber());
    }

    /**
     * Redis Message Listener Container to map topic and listeners
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        // add messagelistener and topic to listen at
        container.addMessageListener(messageListenerAdapter(), redisTopic());
        return container;
    }
}
