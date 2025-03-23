package dto;

import it.muretti.micro.entity.Rapper;

public class AddRapperRequest {
    private String valore;
    private String alias;
    private Rapper rapper;
    
    
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Rapper getRapper() {
		return rapper;
	}
	public void setRapper(Rapper rapper) {
		this.rapper = rapper;
	}

   
}