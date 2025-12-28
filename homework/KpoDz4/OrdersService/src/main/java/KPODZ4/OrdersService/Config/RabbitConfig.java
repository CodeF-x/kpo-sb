package KPODZ4.OrdersService.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange("orders-exchange");
    }

    @Bean
    public Queue paymentUpdatesQueue() {
        return new Queue("payment.updates", true);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order.created", true);

    }

   @Bean
    public Binding bindingOrderCreated(
            @Qualifier("orderCreatedQueue") Queue queue,
            TopicExchange ordersExchange) {
        return BindingBuilder
                .bind(queue)
                .to(ordersExchange)
                .with("order.created");
    }

}