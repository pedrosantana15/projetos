package br.com.spedro.credit_analysis_app.service.strategy;

import br.com.spedro.credit_analysis_app.domain.Proposal;

public interface ICalcScore {

    int calculate(Proposal proposal);

}
