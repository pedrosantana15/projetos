package br.com.spedro.proposal_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.pendingproposal.exchange}")
    private String exchangePendingProposal;

    @Value("${rabbitmq.completedproposal.exchange}")
    private String exchangeCompletedProposal;

    /**
     * Initializing RabbitMQ
     */
    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    /**
     * These methods are creating the Credit Analysis queues.
     */
    @Bean
    public Queue createQueueCreditAnalysis() {
        return QueueBuilder.durable("pending-proposal.ms-credit-analysis")
                .deadLetterExchange("pending-proposal-dlx")
                .build();
    }

    @Bean
    public Queue createQueueCompletedProposal() {
        return QueueBuilder.durable("completed-proposal.ms-proposal").build();
    }

    /**
     * These methods are creating the Proposals queues.
     */
    @Bean
    public Queue createQueueNotification() {
        return QueueBuilder.durable("pending-proposal.ms-notification").build();
    }

    @Bean
    public Queue createQueueCompletedProposalNotification() {
        return QueueBuilder.durable("completed-proposal.ms-notification").build();
    }

    /**
     * These methods are responsible to create the exchanges "pending-proposal.ex"
     * and "completed-proposal.ex"
     */
    @Bean
    public FanoutExchange createFanoutExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange(exchangePendingProposal).build();
    }

    @Bean
    public FanoutExchange createFanoutExchangeCompletedProposal() {
        return ExchangeBuilder.fanoutExchange(exchangeCompletedProposal).build();
    }

    /**
     * These methods are binding the completed proposals queues
     * to the exchange "pending-proposal.ex"
     */
    @Bean
    public Binding createBindingCreditAnalysis() {
        return BindingBuilder.bind(createQueueCreditAnalysis())
                .to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Binding createBindingNotification() {
        return BindingBuilder.bind(createQueueNotification())
                .to(createFanoutExchangePendingProposal());
    }

    /**
     * These methods are binding the completed proposals queues
     * to the exchange "completed-proposal.ex"
     */
    @Bean
    public Binding createBindingProposalCompleted() {
        return BindingBuilder.bind(createQueueCompletedProposal())
                .to(createFanoutExchangeCompletedProposal());
    }

    @Bean
    public Binding createBindingProposalCompletedNotification() {
        return BindingBuilder.bind(createQueueCompletedProposalNotification())
                .to(createFanoutExchangeCompletedProposal());
    }

    /**
     * This method allows to convert a JSON object that comes as a parameter. The
     * default RabbitMQ template can't convert a JSON object.
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Queue createPendingProposalDlqQueue() {
        return QueueBuilder.durable("pending-proposal-dlq.ms-credit-analysis").build();
    }

    @Bean
    public FanoutExchange deadLetterQueueExchangePendingProposal() {
        return ExchangeBuilder.fanoutExchange("pending-proposal-dlx").build();
    }

    @Bean
    public Binding createPendingProposalDlqBinding() {
        return BindingBuilder
                .bind(createPendingProposalDlqQueue())
                .to(deadLetterQueueExchangePendingProposal());
    }

}
