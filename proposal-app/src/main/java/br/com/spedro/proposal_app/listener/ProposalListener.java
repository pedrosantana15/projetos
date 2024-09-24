package br.com.spedro.proposal_app.listener;

import br.com.spedro.proposal_app.dto.ProposalResponseDTO;
import br.com.spedro.proposal_app.entitites.Proposal;
import br.com.spedro.proposal_app.mapper.ProposalMapper;
import br.com.spedro.proposal_app.repositories.ProposalRepository;
import br.com.spedro.proposal_app.services.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class ProposalListener {

    private final ProposalRepository proposalRepository;

    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.completed.proposal.queue}")
    public void completedProposalListener(Proposal proposal) {
        proposalRepository.save(proposal);
        ProposalResponseDTO proposalDto = ProposalMapper.INSTANCE.convertEntityToResponseDto(proposal);
        webSocketService.notify(proposalDto);
    }

}
