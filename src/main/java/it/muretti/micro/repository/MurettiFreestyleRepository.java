package it.muretti.micro.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import it.muretti.micro.entity.MurettiFreestyleEntity;



public interface MurettiFreestyleRepository extends MongoRepository<MurettiFreestyleEntity,String> {
	
	 List<MurettiFreestyleEntity> findByTipo(String tipo);
	 
	 Optional<MurettiFreestyleEntity> findByTipoAndValore(String tipo, String valore);
	 
	 Optional<MurettiFreestyleEntity> findByTipoAndValoreAndAlias(String tipo, String valore, String alias);
	 
	 @Query("{ 'tipo': ?0, 'valore': ?1, 'rapper.nome': ?2 , 'rapper.presenze.data': ?3 }")
	 @Update("{ '$set': { 'rapper.$[].presenze.$[elem].evento': ?4, 'rapper.$[].presenze.$[elem].punteggio': ?5, 'rapper.$[].presenze.$[elem].data': ?6 } }")
	 void updatePresenzaInArray(
	     String tipo, 
	     String valore, 
	     String nome,
	     Date data, 
	     String evento, 
	     int punteggio, 
	     Date nuovaData
	 );

	 }
	 
	 

	 

	


