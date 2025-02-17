package it.muretti.micro.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;



import it.muretti.micro.entity.MurettiFreestyleEntity;

public interface MurettiFreestyleRepository extends MongoRepository<MurettiFreestyleEntity,String> {
	
	 List<MurettiFreestyleEntity> findByTipo(String tipo);

}
