package br.com.spedro.credit_analysis_app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String telefone;

    private String email;

    private Double renda;

    @JsonBackReference
    private Proposal proposal;

}
