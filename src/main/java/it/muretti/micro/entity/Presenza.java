package it.muretti.micro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Presenza implements Serializable {
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date data;
    private String evento;
    private double punteggio;
	private String descrizione;
    
    

	@Override
	public String toString() {
		return "Presenza [data=" + data + ", evento=" + evento + ", punteggio=" + punteggio + "]";
	}



	
    
    
}
