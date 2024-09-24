package br.com.spedro.notification_app.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proposal {

    private Long id;

    private Double valorSolicitado;

    private Integer prazoPagamento;

    private Boolean aprovada;

    private boolean integrada;

    private String observacao;

    @JsonManagedReference
    private User usr;

}