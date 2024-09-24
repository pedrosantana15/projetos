package br.com.spedro.proposal_app.controller;

import br.com.spedro.proposal_app.dto.ProposalRequestDTO;
import br.com.spedro.proposal_app.dto.ProposalResponseDTO;
import br.com.spedro.proposal_app.services.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/proposta")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PostMapping
    public ResponseEntity<ProposalResponseDTO> create(@RequestBody ProposalRequestDTO requestDTO) {
        ProposalResponseDTO response = proposalService.create(requestDTO);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri())
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProposalResponseDTO>> findAll() {
        return ResponseEntity.ok(proposalService.findAll());
    }

}
