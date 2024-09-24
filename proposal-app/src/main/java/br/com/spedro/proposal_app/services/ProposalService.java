package br.com.spedro.proposal_app.services;

import br.com.spedro.proposal_app.dto.ProposalRequestDTO;
import br.com.spedro.proposal_app.dto.ProposalResponseDTO;
import br.com.spedro.proposal_app.entitites.Proposal;
import br.com.spedro.proposal_app.mapper.ProposalMapper;
import br.com.spedro.proposal_app.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {

    ProposalRepository proposalRepository;

    NotificationService notificationService;

    @Value("${rabbitmq.pendingproposal.exchange}")
    private String exchange;

    public ProposalService(ProposalRepository proposalRepository,
                           NotificationService notificationService,
                           @Value("${rabbitmq.pendingproposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    public ProposalResponseDTO create(ProposalRequestDTO requestDTO) {
        Proposal proposal = ProposalMapper.INSTANCE.convertDtoToProposal(requestDTO);
        proposalRepository.save(proposal);
        notifyRabbitMQ(proposal);

        return ProposalMapper.INSTANCE.convertEntityToResponseDto(proposal);
    }

    public void notifyRabbitMQ(Proposal proposal) {
        try {
            notificationService.notify(proposal, exchange);
        } catch (RuntimeException e) {
            proposal.setIntegrada(false);
            proposalRepository.save(proposal);
        }
    }

    public List<ProposalResponseDTO> findAll() {
        Iterable<Proposal> proposals = proposalRepository.findAll();

        return ProposalMapper.INSTANCE
                .convertEntityListToDtoList(proposals);
    }

}
