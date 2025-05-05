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
	 

	 }
	 
	 

	 

	


