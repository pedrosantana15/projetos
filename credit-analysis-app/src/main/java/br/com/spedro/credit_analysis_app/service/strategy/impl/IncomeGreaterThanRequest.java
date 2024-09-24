package br.com.spedro.credit_analysis_app.service.strategy.impl;

import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.service.strategy.ICalcScore;
import org.springframework.stereotype.Component;

@Component
public class IncomeGreaterThanRequest implements ICalcScore {

    @Override
    public int calculate(Proposal proposal) {
        return isIncomeGreaterThanRequested(proposal) ? 100 : 0;
    }

    public Boolean isIncomeGreaterThanRequested(Proposal proposal) {
        return proposal.getUsr().getRenda() > proposal.getValorSolicitado();
    }

}
