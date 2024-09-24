package br.com.spedro.proposal_app.scheduler;

import br.com.spedro.proposal_app.entitites.Proposal;
import br.com.spedro.proposal_app.repositories.ProposalRepository;
import br.com.spedro.proposal_app.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NotIntegratedProposals {

    ProposalRepository proposalRepository;

    NotificationService notificationService;

    @Value("${rabbitmq.pendingproposal.exchange}")
    private String exchange;

    public NotIntegratedProposals(ProposalRepository proposalRepository,
                           NotificationService notificationService,
                           @Value("${rabbitmq.pendingproposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void findNotIntegratedProposals() {
        proposalRepository.findAllByIntegradaIsFalse().forEach(proposal -> {
            try {
                notificationService.notify(proposal, exchange);
                updateProposal(proposal);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
        });
    }

    public final void updateProposal(Proposal proposal) {
        proposal.setIntegrada(true);
        proposalRepository.save(proposal);
    }

}
