package it.muretti.micro.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestPresenza implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date data;
    private String evento;
    private String moltiplicatore;
    private String posizionamento;
    
    
    
    public String getPosizionamento() {
    	return posizionamento;
    }
    
    public void setPosizionamento(String posizionamento) {
    	this.posizionamento = posizionamento;
    	}
    
    
    public String getMoltiplicatore() {
    	return moltiplicatore;
    }
    
    public void setMoltiplicatore(String moltiplicatore) {
    	this.moltiplicatore = moltiplicatore;
    }
    
	public Date getData() {
		return data;
	}
	public void setData(Date dataPresenza) {
		this.data = dataPresenza;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "RequestPresenza [data=" + data + ", evento=" + evento + ", moltiplicatore=" + moltiplicatore
				+ ", posizionamento=" + posizionamento + "]";
	}
	
	
	
    
    
    
    
    

    
    
}
