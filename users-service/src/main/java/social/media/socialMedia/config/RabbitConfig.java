package social.media.socialMedia.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import social.media.socialMedia.constants.UserRoutingKey;

@Configuration
public class RabbitConfig {
    private static final String EXCHANGE_NAME = "user.events.exchange";

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Exchange eventsExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    @Bean
    public Queue userEventsQueue() {
        return new Queue("user-events-queue", true); // Declares durable queue
    }

    @Bean
    public Binding userCreatedBinding(Queue userEventsQueue, Exchange eventsExchange) {
        return BindingBuilder.bind(userEventsQueue).to(eventsExchange).with(UserRoutingKey.USER_CREATED.getKey()).noargs();
    }

    @Bean
    public Binding userUpdatedBinding(Queue userEventsQueue, Exchange eventsExchange) {
        return BindingBuilder.bind(userEventsQueue).to(eventsExchange).with(UserRoutingKey.USER_UPDATED.getKey()).noargs();
    }

    @Bean
    public Binding userDeletedBinding(Queue userEventsQueue, Exchange eventsExchange) {
        return BindingBuilder.bind(userEventsQueue).to(eventsExchange).with(UserRoutingKey.USER_DELETED.getKey()).noargs();
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(eventsExchange());
        rabbitAdmin.declareQueue(userEventsQueue());
        rabbitAdmin.declareBinding(userCreatedBinding(userEventsQueue(), eventsExchange()));
        rabbitAdmin.declareBinding(userUpdatedBinding(userEventsQueue(), eventsExchange()));
        rabbitAdmin.declareBinding(userDeletedBinding(userEventsQueue(), eventsExchange()));
        return rabbitAdmin;
    }

}