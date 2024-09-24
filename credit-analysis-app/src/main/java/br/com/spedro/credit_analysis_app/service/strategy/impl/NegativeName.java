package br.com.spedro.credit_analysis_app.service.strategy.impl;

import br.com.spedro.credit_analysis_app.constant.Message;
import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.exceptions.StrategyException;
import br.com.spedro.credit_analysis_app.service.strategy.ICalcScore;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1)
@Component
public class NegativeName implements ICalcScore {

    @Override
    public int calculate(Proposal proposal) {
        if (negativeName()) {
            throw new StrategyException(String.format(Message.NEGATIVE_NAME_USER, proposal.getUsr().getNome()));
        }

        return 100;
    }

    private boolean negativeName() {
        return new Random().nextBoolean();
    }

}
