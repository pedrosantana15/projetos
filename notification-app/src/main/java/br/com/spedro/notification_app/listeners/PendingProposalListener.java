package br.com.spedro.notification_app.listeners;

import br.com.spedro.notification_app.constant.ConstantMessage;
import br.com.spedro.notification_app.domain.Proposal;
import br.com.spedro.notification_app.services.NotificationSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    @Autowired
    private NotificationSnsService notificationSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void pendingProposal(Proposal proposal) {
        String message = String.format(ConstantMessage.ANALYSIS_PENDING_PROPOSAL, proposal.getUsr().getNome());
        notificationSnsService.notify(proposal.getUsr().getTelefone(), message);
    }

}
