package br.com.spedro.credit_analysis_app.service.strategy.impl;

import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.service.strategy.ICalcScore;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermIsLessThanTenYears implements ICalcScore {

    @Override
    public int calculate(Proposal proposal) {
        return proposal.getPrazoPagamento() < 120 ? 80 : 0;
    }

}
