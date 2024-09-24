package br.com.spedro.credit_analysis_app.service.strategy.impl;

import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.service.strategy.ICalcScore;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtherLoanInProgress implements ICalcScore {

    @Override
    public int calculate(Proposal proposal) {
        return hasOtherLoans() ? 0 : 50;
    }

    private boolean hasOtherLoans() {
        return new Random().nextBoolean();
    }

}
