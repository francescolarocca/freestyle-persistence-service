package it.muretti.micro.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Rapper implements Serializable {

	 private String nome;
	    private int rank;
	    private List<Presenza> presenze;
	    
}
