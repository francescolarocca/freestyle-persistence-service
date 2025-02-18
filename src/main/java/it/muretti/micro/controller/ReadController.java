package it.muretti.micro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Rapper;
import it.muretti.micro.service.MurettiFreestyleService;

@Controller
@RequestMapping("/murettifreestyle")
public class ReadController {
	
	@Autowired
	private MurettiFreestyleService murettifreestyleService;
	@GetMapping
	public ResponseEntity<List<MurettiFreestyleEntity>> getAllUsers() {
        return ResponseEntity.ok(murettifreestyleService.getAllUsers());
	
	}
	
	@GetMapping("/filtrati")
	public ResponseEntity<List<MurettiFreestyleEntity>> getMurettiFiltrati(@RequestParam String tipo) {
	    
	    if (!tipo.equalsIgnoreCase("muretto") && !tipo.equalsIgnoreCase("rapper")) {
	       
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(null);  // 
	    }

	    
	    List<MurettiFreestyleEntity> result = murettifreestyleService.filtrati(tipo);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/rapperByTipoValore")
    public ResponseEntity<?> getRappersByTipoAndValore(@RequestParam String tipo, @RequestParam String valore) {
        try {
            List<Rapper> rappers = murettifreestyleService.findRappersByTipoAndValore(tipo, valore);
            if (rappers.isEmpty()) {
                return ResponseEntity.status(404).body("Nessun rapper trovato per il muretto con valore: " + valore);
            }
            return ResponseEntity.ok(rappers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
		
}
