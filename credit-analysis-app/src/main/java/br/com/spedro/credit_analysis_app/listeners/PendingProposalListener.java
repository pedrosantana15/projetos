package br.com.spedro.credit_analysis_app.listeners;

import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.service.strategy.CreditAnalysisService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PendingProposalListener {

    private CreditAnalysisService creditAnalysisService;

    @Autowired
    public PendingProposalListener(CreditAnalysisService creditAnalysisService) {
        this.creditAnalysisService = creditAnalysisService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.pending.proposal}")
    public void pendingProposal(Proposal proposal) {
        creditAnalysisService.analyze(proposal);
    }

}
