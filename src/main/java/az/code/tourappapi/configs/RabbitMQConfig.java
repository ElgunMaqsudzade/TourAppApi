package az.code.tourappapi.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class RabbitMQConfig {
    private static final String exchange = "exchange";
    public static final String offer = "offer";
    public static final String subscription = "subscription";
    public static final String offerReply = "offer_reply";


    @Bean
    public Queue queueOffer() {
        return new Queue(offer);
    }

    @Bean
    public Queue queueOfferReply() {
        return new Queue(offerReply);
    }

    @Bean
    public Queue queueSub() {
        return new Queue(subscription);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingOffer() {
        return BindingBuilder.bind(queueOffer()).to(exchange()).with(offer);
    }

    @Bean
    public Binding bindingOfferReply() {
        return BindingBuilder.bind(queueOfferReply()).to(exchange()).with(offerReply);
    }

    @Bean
    public Binding bindingSubscription() {
        return BindingBuilder.bind(queueSub()).to(exchange()).with(subscription);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate temp = new RabbitTemplate(connectionFactory);
        temp.setExchange(exchange);
        temp.setRoutingKey(offer);
        temp.setMessageConverter(converter());
        return temp;
    }
}
