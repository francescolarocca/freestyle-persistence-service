package it.muretti.micro.service;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import it.muretti.micro.conf.RankPointTable;
import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Presenza;
import it.muretti.micro.entity.Rapper;
import it.muretti.micro.repository.MurettiFreestyleMongoTemplateRepository;
import it.muretti.micro.repository.MurettiFreestyleRepository;
import it.muretti.micro.request.RequestPresenza;

@Service
public class MurettiFreestyleService {
	
	@Autowired
	private RankPointTable rankPointTable;
	
	@Autowired
	private   MurettiFreestyleRepository murettifreestyleRepository;
	
	@Autowired
	
	private MurettiFreestyleMongoTemplateRepository murettiRepository;
	
	
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
	 
	 public boolean addPresenzaToRapper(String tipo, String valore, String rapperNome, RequestPresenza requestPresenza) {
		 
		 
		 
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
	                
	                Presenza newPresenza = new Presenza();
	                newPresenza.setData(requestPresenza.getData());
	                newPresenza.setEvento(requestPresenza.getEvento());
	                newPresenza.setPunteggio(rankPointTable.calcolaRank(requestPresenza.getEvento(),requestPresenza.getMoltiplicatore(),requestPresenza.getPosizionamento()));
	                // Aggiungi la nuova presenza all'array di presenze
	                rapper.getPresenze().add(newPresenza);
	                murettifreestyleRepository.save(muretto); // Salva l'entità aggiornata nel DB
	                return true;  // Ritorna true se l'operazione è riuscita
	            }
	        }
	        return false;  // Ritorna false se non trova l'entità o il rapper
	    }
	 
	 
	 
	 
	 public boolean updatePresenza(String tipo, String valore,String nome, Date data,Presenza nuovaPresenza, String evento, double punteggio) {
		    // Verifica che la data venga settata correttamente
		    System.out.println("🔍 Nuova data da aggiornare: " + nuovaPresenza.getData());

		    // Esegui l'aggiornamento nel repository
		    

		    return murettiRepository.updatePresenzaInArray(
			        tipo,
			        valore,
			        nome,
			        data,
			        evento,
			        punteggio,
			        nuovaPresenza.getData()  // La nuova data da impostare
			    );
		}
	 
	 
	 public boolean deletePresenza( String valore,String nome, Date data) {
		   

		    // Esegui l'aggiornamento nel repository
		    

		    return murettiRepository.deletePresenzaInArray( 
			        
			        valore,
			        nome,
			        data
			         // La nuova data da impostare
			    );
		}
	 public boolean newRapperToMuretto( String valore,String alias, String nome,  int rank) {
		   

		    // Esegui l'aggiornamento nel repository
		    

		    return murettiRepository.addRapperToMuretto( 
			        
			        valore,
			        nome,
			        alias,
			        rank
			         // La nuova data da impostare
			    );
	 		}
	 public boolean deleteRapper( String valore,String nome, String alias) {
		   

		    // Esegui l'aggiornamento nel repository
		    

		    return murettiRepository.deleteRapperInMuretto( 
			        
			        valore,
			        nome,
			        alias
			         // La nuova data da impostare
			    );
		}
	 
	 public boolean updateRapper(String tipo, String valore,String nome, String newName, int newRank) {
		    // Verifica che la data venga settata correttamente
		    

		    // Esegui l'aggiornamento nel repository
		    

		    return murettiRepository.updateRapperInArray(
			        tipo,
			        valore,
			        nome,    
			        newName,
			        newRank
			    );
		}
	 
	 }
