package br.com.spedro.credit_analysis_app.service.notification;

import br.com.spedro.credit_analysis_app.domain.Proposal;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQNotification {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notify(String exchange, Proposal proposal) {
        rabbitTemplate.convertAndSend(exchange, "", proposal);
    }

}
