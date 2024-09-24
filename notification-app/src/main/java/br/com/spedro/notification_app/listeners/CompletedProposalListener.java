package br.com.spedro.notification_app.listeners;

import br.com.spedro.notification_app.constant.ConstantMessage;
import br.com.spedro.notification_app.domain.Proposal;
import br.com.spedro.notification_app.services.NotificationSnsService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompletedProposalListener {

    private NotificationSnsService notificationSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.completed.proposal}")
    public void listener(Proposal proposal) {
        notificationSnsService.notify(proposal.getUsr().getTelefone(), isApproved(proposal));
    }

    public String isApproved(Proposal proposal) {
        if(proposal.getAprovada()) {
            return String.format(ConstantMessage.COMPLETED_PROPOSAL_APPROVED,
                    proposal.getUsr().getNome(), proposal.getValorSolicitado());
        } else {
            return String.format(ConstantMessage.COMPLETED_PROPOSAL_REPROVED,
                    proposal.getUsr().getNome(), proposal.getValorSolicitado(), proposal.getObservacao());
        }
    }

}
