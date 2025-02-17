package it.muretti.micro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.service.MurettiFreestyleService;

@Controller
@RequestMapping("/murettifreestyle")
public class ReadController {
	
	@Autowired
	private MurettiFreestyleService murettifreestyleService;
	@GetMapping("/all")
	public ResponseEntity<List<MurettiFreestyleEntity>> getAllUsers() {
        return ResponseEntity.ok(murettifreestyleService.getAllUsers());
	
	}
}
