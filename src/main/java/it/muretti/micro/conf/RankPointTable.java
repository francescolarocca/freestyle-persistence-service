package it.muretti.micro.conf;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "rankpointable")
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
    public int getPresenza() {
        return presenza;
    }

    public void setPresenza(int presenza) {
        this.presenza = presenza;
    }

    public Map<String, Integer> getBattle() {
        return battle;
    }

    public void setBattle(Map<String, Integer> battle) {
        this.battle = battle;
    }

    public Map<String, Double> getMoltiplicatore() {
        return moltiplicatore;
    }

    public void setMoltiplicatore(Map<String, Double> moltiplicatore) {
        this.moltiplicatore = moltiplicatore;
    }
}
