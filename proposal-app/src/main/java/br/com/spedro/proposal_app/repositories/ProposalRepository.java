package br.com.spedro.proposal_app.repositories;

import br.com.spedro.proposal_app.entitites.Proposal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> {

    List<Proposal> findAllByIntegradaIsFalse();

}
