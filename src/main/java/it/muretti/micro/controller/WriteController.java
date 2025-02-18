package it.muretti.micro.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Presenza;
import it.muretti.micro.service.MurettiFreestyleService;

@Controller
@RequestMapping("/murettifreestyle")
public class WriteController {
	
	
	@Autowired
	private MurettiFreestyleService murettifreestyleService;
	
	@PostMapping("/add")
    public ResponseEntity<MurettiFreestyleEntity> addUtente(@RequestBody MurettiFreestyleEntity entity) {
		MurettiFreestyleEntity nuovoUtente = murettifreestyleService.addEntity(entity);
        return ResponseEntity.ok(nuovoUtente);
    }
	
	 // Endpoint per aggiungere la presenza a un rapper specifico
    @PostMapping("/addPresenza/{tipo}/{valore}/{rapperNome}")
    public ResponseEntity<?> addPresenza(
        @PathVariable String tipo,
        @PathVariable String valore,
        @PathVariable String rapperNome,
        @RequestBody Presenza presenza) {
        
        boolean added = murettifreestyleService.addPresenzaToRapper(tipo, valore, rapperNome, presenza);
        if (added) {
            return ResponseEntity.ok("Presenza aggiunta correttamente");
        } else {
            return ResponseEntity.status(404).body("Rapper non trovato o errore nel processo");
        }
    }
    
    @PutMapping("/updatePresenza")
    public ResponseEntity<?> updatePresenza(
        @RequestParam String tipo,
        @RequestParam String valore,
        @RequestParam String nome,
        @RequestParam String data,  // La data è un parametro stringa nell'URL
        @RequestBody Presenza nuovaPresenza) {

        // Usa SimpleDateFormat per fare il parsing della stringa della data
    	DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        
        ZonedDateTime dataPresenza = null;
        data = data.replace(" ", "+");
        try {
            // Parsing della data in un oggetto ZonedDateTime
            dataPresenza = ZonedDateTime.parse(data, formatter);
        } catch (Exception e) {
            e.printStackTrace(); // Gestione degli errori
        }

        // Ora chiami il servizio con la data correttamente parsata
        boolean updated = murettifreestyleService.updatePresenza(tipo, valore, nome, dataPresenza, nuovaPresenza);
        if (updated) {
            return ResponseEntity.ok("Presenza aggiornata correttamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Presenza non trovata o errore nel processo");
        }
    }



}
