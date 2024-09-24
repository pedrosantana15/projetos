package br.com.spedro.credit_analysis_app.service.strategy.impl;

import br.com.spedro.credit_analysis_app.constant.Message;
import br.com.spedro.credit_analysis_app.domain.Proposal;
import br.com.spedro.credit_analysis_app.exceptions.StrategyException;
import br.com.spedro.credit_analysis_app.service.strategy.ICalcScore;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(2)
@Component
public class ScoreImpl implements ICalcScore {

    @Override
    public int calculate(Proposal proposal) {
        int score = score();

        if(score < 200) {
            throw new StrategyException(String.format(Message.SCORE_TOO_LESS, proposal.getUsr().getNome()));
        } else if(score <= 400) {
            return 150;
        } else if(score <= 600) {
            return 180;
        } else if (score <= 800) {
            return 200;
        } else {
            return 250;
        }
    }

    private int score() {
        return new Random().nextInt(0, 1000);
    }

}
