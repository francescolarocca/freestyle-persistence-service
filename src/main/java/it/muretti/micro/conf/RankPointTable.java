package it.muretti.micro.conf;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "rankpointable")
@Getter
@Setter
public class RankPointTable {

    private int presenza;
    private Map<String,Integer> battle;
    private Map<String,Double> moltiplicatore;


    public double calcolaRank(String evento, String moltiplicatore, String posizionamento) {
        if(evento.equals("battle")) {
            return this.battle.get(posizionamento) * this.moltiplicatore.get(moltiplicatore);
        }
        else if(evento.equals("presenza")) {
            return presenza * this.moltiplicatore.get(moltiplicatore);
        }
        return 0 ;
    }

}
