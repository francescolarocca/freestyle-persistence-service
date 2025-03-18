package it.muretti.micro.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Rapper implements Serializable {
    private String nome;
    private double rank;
    private List<Presenza> presenze;
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	public List<Presenza> getPresenze() {
		return presenze;
	}
	public void setPresenze(List<Presenza> presenze) {
		this.presenze = presenze;
	}
    
    
}
