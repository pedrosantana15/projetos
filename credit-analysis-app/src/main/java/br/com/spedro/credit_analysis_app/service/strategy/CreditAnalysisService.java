package br.com.spedro.credit_analysis_app.service.strategy;

import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.exceptions.StrategyException;
import br.com.spedro.credit_analysis_app.service.notification.RabbitMQNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditAnalysisService {

    private final List<ICalcScore> scores;

    private final RabbitMQNotification rabbitMQNotification;

    private final String exchangeCompletedProposal;

    @Autowired
    public CreditAnalysisService(List<ICalcScore> scores, RabbitMQNotification rabbitMQNotification,
                                 @Value("${rabbitmq.exchange.completed.proposal}") String exchange) {
        this.scores = scores;
        this.rabbitMQNotification = rabbitMQNotification;
        this.exchangeCompletedProposal = exchange;
    }

    public void analyze(Proposal proposal) {
       try {
           int score = scores.stream()
                   .mapToInt(impl -> impl.calculate(proposal)).sum();
           proposal.setAprovada(score > 350);
       } catch (StrategyException e) {
            proposal.setAprovada(false);
            proposal.setObservacao(e.getMessage());
       }
       rabbitMQNotification.notify(exchangeCompletedProposal, proposal);
    }


}
