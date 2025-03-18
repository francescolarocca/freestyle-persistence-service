package it.muretti.micro.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "murettifreestyle")

public class MurettiFreestyleEntity implements Serializable{
	
	@Id
    private String id;
    private String tipo;
    private String valore;
    private String muretto;
    private String alias;
    private String ranking;
    private List<Rapper> rapper;
    
    public MurettiFreestyleEntity() {
    }
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getMuretto() {
		return muretto;
	}
	public void setMuretto(String muretto) {
		this.muretto = muretto;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public List<Rapper> getRapper() {
		return rapper;
	}
	public void setRapper(List<Rapper> rapper) {
		this.rapper = rapper;
	}
    
    

}
