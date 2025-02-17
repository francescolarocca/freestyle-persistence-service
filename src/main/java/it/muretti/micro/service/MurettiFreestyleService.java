package it.muretti.micro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.repository.MurettiFreestyleRepository;

@Service
public class MurettiFreestyleService {
	
	@Autowired
	private   MurettiFreestyleRepository murettifreestyleRepository;
	
	
	public List<MurettiFreestyleEntity> getAllUsers() {
        return murettifreestyleRepository.findAll();
    }

}
