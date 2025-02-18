package it.muretti.micro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;



import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Presenza;

public interface MurettiFreestyleRepository extends MongoRepository<MurettiFreestyleEntity,String> {
	
	 List<MurettiFreestyleEntity> findByTipo(String tipo);
	 
	 Optional<MurettiFreestyleEntity> findByTipoAndValore(String tipo, String valore);
	 
	 

	 

	

}
