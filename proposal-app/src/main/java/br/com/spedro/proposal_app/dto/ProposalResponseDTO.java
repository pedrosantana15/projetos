package br.com.spedro.proposal_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String telefone;

    private String cpf;

    private Double renda;

    private String valorSolicitadoFmt;

    private Integer prazoPagamento;

    private Boolean aprovada;

    private String observacao;

}
