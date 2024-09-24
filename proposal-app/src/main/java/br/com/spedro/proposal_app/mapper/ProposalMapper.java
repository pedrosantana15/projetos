package br.com.spedro.proposal_app.mapper;

import br.com.spedro.proposal_app.dto.ProposalRequestDTO;
import br.com.spedro.proposal_app.dto.ProposalResponseDTO;
import br.com.spedro.proposal_app.entitites.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;

@Mapper
public interface ProposalMapper {

    ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);

    @Mapping(target = "usr.nome", source = "nome")
    @Mapping(target = "usr.sobrenome", source = "sobrenome")
    @Mapping(target = "usr.renda", source = "renda")
    @Mapping(target = "usr.cpf", source = "cpf")
    @Mapping(target = "usr.telefone", source = "telefone")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aprovada", ignore = true)
    @Mapping(target = "integrada", constant = "true")
    @Mapping(target = "observacao", ignore = true)
    Proposal convertDtoToProposal(ProposalRequestDTO requestDTO);

    @Mapping(target = "nome", source = "usr.nome")
    @Mapping(target = "sobrenome", source = "usr.sobrenome")
    @Mapping(target = "telefone", source = "usr.telefone")
    @Mapping(target = "cpf", source = "usr.cpf")
    @Mapping(target = "renda", source = "usr.renda")
    @Mapping(target = "valorSolicitadoFmt", expression = "java(setRequestedAmountFmt(proposal))")
    ProposalResponseDTO convertEntityToResponseDto(Proposal proposal);

    List<ProposalResponseDTO> convertEntityListToDtoList(Iterable<Proposal> proposals);

    default String setRequestedAmountFmt(Proposal proposal) {
        return NumberFormat.getCurrencyInstance().format(proposal.getValorSolicitado());
    }

}
