package it.muretti.micro.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Presenza implements Serializable {
	 private Date data;
	    private String evento;
	    private int punteggio;
}
