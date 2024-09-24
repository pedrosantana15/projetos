package br.com.spedro.proposal_app.services;

import br.com.spedro.proposal_app.dto.ProposalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notify(ProposalResponseDTO proposalResponseDto) {
        messagingTemplate.convertAndSend("/propostas", proposalResponseDto);
    }

}
