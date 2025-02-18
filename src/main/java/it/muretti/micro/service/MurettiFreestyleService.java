package it.muretti.micro.service;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Presenza;
import it.muretti.micro.entity.Rapper;
import it.muretti.micro.repository.MurettiFreestyleRepository;

@Service
public class MurettiFreestyleService {
	
	@Autowired
	private   MurettiFreestyleRepository murettifreestyleRepository;
	
	
	public List<MurettiFreestyleEntity> getAllUsers() {
        return murettifreestyleRepository.findAll();
    }
	
	public List<MurettiFreestyleEntity> filtrati(@RequestParam String tipo) {
        return murettifreestyleRepository.findByTipo(tipo);
    }
	
	public MurettiFreestyleEntity addEntity(MurettiFreestyleEntity entity){
		return murettifreestyleRepository.save(entity);
	}

	 public List<Rapper> findRappersByTipoAndValore(String tipo, String valore) {
	        if (!"Muretto".equalsIgnoreCase(tipo)) {
	            throw new IllegalArgumentException("Tipo non valido. Deve essere 'Muretto'");
	        }
	        
	        Optional<MurettiFreestyleEntity> entity = murettifreestyleRepository.findByTipoAndValore(tipo, valore);
	        if (entity.isPresent()) {
	            return entity.get().getRapper();
	        }
	        return List.of();  // Restituisce una lista vuota se non trova l'entità
	    }
	 
	 public boolean addPresenzaToRapper(String tipo, String valore, String rapperNome, Presenza presenza) {
	        if (!"Muretto".equalsIgnoreCase(tipo)) {
	            throw new IllegalArgumentException("Tipo non valido. Deve essere 'Muretto'");
	        }
	        
	        Optional<MurettiFreestyleEntity> entity = murettifreestyleRepository.findByTipoAndValore(tipo, valore);
	        if (entity.isPresent()) {
	            MurettiFreestyleEntity muretto = entity.get();
	            // Trova il rapper con il nome specificato
	            Optional<Rapper> rapperOpt = muretto.getRapper().stream()
	                .filter(r -> r.getNome().equalsIgnoreCase(rapperNome))
	                .findFirst();

	            if (rapperOpt.isPresent()) {
	                Rapper rapper = rapperOpt.get();
	                // Aggiungi la nuova presenza all'array di presenze
	                rapper.getPresenze().add(presenza);
	                murettifreestyleRepository.save(muretto); // Salva l'entità aggiornata nel DB
	                return true;  // Ritorna true se l'operazione è riuscita
	            }
	        }
	        return false;  // Ritorna false se non trova l'entità o il rapper
	    }
	 
	 public boolean updatePresenza(String tipo, String valore, String nome, ZonedDateTime data, Presenza nuovaPresenza) {
		    if (!"Muretto".equalsIgnoreCase(tipo)) {
		        throw new IllegalArgumentException("Tipo non valido. Deve essere 'Muretto'");
		    }

		    // Cerca l'entità MurettiFreestyle
		    Optional<MurettiFreestyleEntity> entity = murettifreestyleRepository.findByTipoAndValore(tipo, valore);
		    if (entity.isPresent()) {
		        MurettiFreestyleEntity muretto = entity.get();

		        // Trova il rapper per nome
		        Optional<Rapper> rapperOpt = muretto.getRapper().stream()
		            .filter(r -> r.getNome().equalsIgnoreCase(nome))
		            .findFirst();

		        if (rapperOpt.isPresent()) {
		            Rapper rapper = rapperOpt.get();

		            // Converte la data in LocalDate per il confronto (ignora l'orario)
		            LocalDate dataInput = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		            // Trova la presenza da aggiornare
		            Optional<Presenza> presenzaOpt = rapper.getPresenze().stream()
		                .filter(p -> p.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(dataInput))
		                .findFirst();

		            if (presenzaOpt.isPresent()) {
		                // Presenza trovata, aggiorna con i nuovi dati
		                Presenza presenza = presenzaOpt.get();
		                presenza.setEvento(nuovaPresenza.getEvento());
		                presenza.setPunteggio(nuovaPresenza.getPunteggio());
		                presenza.setData(nuovaPresenza.getData()); // Aggiorna anche la data

		                // Salva la modifica nel database
		                murettifreestyleRepository.save(muretto);
		                return true; // Successo
		            }
		        }
		    }
		    return false; // Non trovato o errore nel processo
		}


}
