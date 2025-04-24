package it.muretti.micro.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import it.muretti.micro.request.RequestAppello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.AddRapperRequest;
import it.muretti.micro.conf.RankPointTable;
import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Presenza;
import it.muretti.micro.entity.Rapper;
import it.muretti.micro.request.RequestPresenza;
import it.muretti.micro.service.MurettiFreestyleService;

@CrossOrigin(origins = "http://localhost:3000") // Permette a tutto il controller di accettare richieste da React
@Controller
@RequestMapping("/murettifreestyle")
public class WriteController {
	
	
	
	@Autowired
	private MurettiFreestyleService murettifreestyleService;
	
	@PostMapping("/add")
    public ResponseEntity<MurettiFreestyleEntity> addUtente(@RequestBody MurettiFreestyleEntity entity) {
		try {
	        MurettiFreestyleEntity nuovoMuretto = murettifreestyleService.addEntity(entity);
	        // Restituisci la risposta con codice 201 per successo
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoMuretto);
	    } catch (Exception e) {
	        // Gestisci errori se ci sono
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
    }
	
	 // Endpoint per aggiungere la presenza a un rapper specifico
    @PostMapping("/addPresenza/{tipo}/{valore}/{rapperNome}")
    public ResponseEntity<?> addPresenza(
        @PathVariable String tipo,
        @PathVariable String valore,
        @PathVariable String rapperNome,
        @RequestBody RequestPresenza presenza) {
    	
    	System.out.println("Dati ricevuti: " + presenza.toString());
    
        
        boolean added = murettifreestyleService.addPresenzaToRapper(tipo, valore, rapperNome, presenza);
        if (added) {
            return ResponseEntity.ok("Presenza aggiunta correttamente");
        } else {
            return ResponseEntity.status(404).body("Rapper non trovato o errore nel processo");
        }
    }
    
    @PutMapping("/updatePresenza")
    public ResponseEntity<?> updatePresenza(
        @RequestParam String tipo, // muretto
        @RequestParam String valore, //messina
        @RequestParam String nome,  //resla
        @RequestParam String data,  // data
        @RequestBody Presenza nuovaPresenza) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        data = data.replace(" ", "+");
        
        Date dataConvertita=null;
        try {
            ZonedDateTime dataPresenza = ZonedDateTime.parse(data, formatter);
            dataConvertita = Date.from(dataPresenza.toInstant());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato data non valido: " + data);
        }

        // Passaggio dei dati aggiornati
        boolean updated = murettifreestyleService.updatePresenza(tipo, valore,nome, dataConvertita, nuovaPresenza ,  nuovaPresenza.getEvento(), nuovaPresenza.getPunteggio());
        if (updated) {
        	System.out.println(nuovaPresenza.toString());
            return ResponseEntity.ok("Presenza aggiornata correttamente");
           
            
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Presenza non trovata o errore nel processo");
        }
        
        
    }
    
    @DeleteMapping("/{valore}/{nome}")
    public ResponseEntity<?> deletePresenza(
        
        @PathVariable String valore,
        @PathVariable String nome,
        @RequestParam String data
        ) {
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        data = data.replace(" ", "+");
        
        Date dataConvertita=null;
        try {
            ZonedDateTime dataPresenza = ZonedDateTime.parse(data, formatter);
            dataConvertita = Date.from(dataPresenza.toInstant());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato data non valido: " + data);
        }

    	   murettifreestyleService.deletePresenza(valore, nome, dataConvertita);
        return ResponseEntity.ok("Data eliminata con successo!");
    }
    
    @PostMapping("/addRapper")
    public ResponseEntity<String> addRapper(@RequestBody AddRapperRequest request) {
        boolean success = murettifreestyleService.newRapperToMuretto(
                request.getValore(),
                request.getAlias(),
                request.getRapper()
        );

        if (success) {
            return ResponseEntity.ok("Rapper aggiunto con successo!");
        } else {
            return ResponseEntity.badRequest().body("Errore: Nessun documento trovato o aggiornato.");
        }
    }
    @DeleteMapping("/deleteRapper/{valore}/{alias}")
    public ResponseEntity<?> deleteRapper(
        
        @PathVariable String valore,
        @PathVariable String alias,
        @RequestParam String nome
        
        ) {
    	
    	  boolean deleted = murettifreestyleService.deleteRapper(valore, nome, alias);
          if (deleted) {
              return ResponseEntity.ok("Rapper eliminato con successo!");
          } else {
              return ResponseEntity.notFound().build();
          }    
    }
    
    @PutMapping("/updateRapper/{tipo}/{valore}/{oldName}")
    public ResponseEntity<?> updateRapper(
    	@PathVariable String tipo,
    	@PathVariable String valore,
        @PathVariable String oldName,
        @RequestParam(required = false) String nome,
        @RequestParam int rank
          
        ) {

       
        // Passaggio dei dati aggiornati
        murettifreestyleService.updateRapper(tipo, valore, oldName,  nome , rank);
        return ResponseEntity.ok("Rapper aggiornato correttamente");
        
    }

    @PostMapping("/appello")
    public ResponseEntity<?> doAppello(
            @RequestBody RequestAppello requestAppello
    ) {
        murettifreestyleService.doAppello(requestAppello);
        return ResponseEntity.ok("Rapper aggiornato correttamente");
    }


}
