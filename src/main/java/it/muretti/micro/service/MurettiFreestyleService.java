package it.muretti.micro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.muretti.micro.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	private MurettiFreestyleRepository murettifreestyleRepository;
	
	@Autowired
	
	private MurettiFreestyleMongoTemplateRepository murettiFreestyleMongoTemplateRepository;
	
	
	public List<MurettiFreestyleEntity> getAllUsers() {
        return murettifreestyleRepository.findAll();
    }
	
	public List<MurettiFreestyleEntity> filtrati(@RequestParam String tipo) {
        return murettifreestyleRepository.findByTipo(tipo);
    }
	
	public MurettiFreestyleEntity addEntity(MurettiFreestyleEntity entity){
		if (entity.getRapper() == null) {
	        entity.setRapper(new ArrayList<>()); // Inizializza la lista vuota
	    }
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
	// Metodo che restituisce un rapper specifico in base a tipo, valore e nome
	    public Rapper findRapper(String tipo, String valore, String nome) {
	        // Trova il TipoRecord per tipo e valore
	    	MurettiFreestyleEntity rappers  = murettifreestyleRepository.findByTipoAndValore(tipo, valore)
	                .orElseThrow(() -> new IllegalArgumentException("Muretto con tipo " + tipo + " e valore " + valore + " non trovato"));
	        
	        // Cerca il rapper con il nome specificato all'interno del TipoRecord
	        return rappers.getRapper().stream()
	                         .filter(rapper -> rapper.getNome().equalsIgnoreCase(nome))
	                         .findFirst()
	                         .orElse(null); // Restituisce null se il rapper non è trovato
	    }
	
	 
	 public boolean addPresenzaToRapper(String tipo, String valore, String rapperNome, RequestPresenza requestPresenza) {
		    if (!"Muretto".equalsIgnoreCase(tipo)) {
		        throw new IllegalArgumentException("Tipo non valido. Deve essere 'Muretto'");
		    }

		    Optional<MurettiFreestyleEntity> entityOpt = murettifreestyleRepository.findByTipoAndValore(tipo, valore);

		    if (entityOpt.isPresent()) {
		        MurettiFreestyleEntity muretto = entityOpt.get();

		        // Trova il rapper specificato
		        Optional<Rapper> rapperOpt = muretto.getRapper().stream()
		            .filter(r -> r.getNome().equalsIgnoreCase(rapperNome))
		            .findFirst();

		        if (rapperOpt.isPresent()) {
		            Rapper rapper = rapperOpt.get();

		            // Creazione nuova presenza
		            Presenza newPresenza = new Presenza();
		            newPresenza.setData(requestPresenza.getData());
		            newPresenza.setEvento(requestPresenza.getEvento());

		            // Calcola il punteggio per questa presenza
		            double punteggio = rankPointTable.calcolaRank(
		                requestPresenza.getEvento(),
		                requestPresenza.getMoltiplicatore(),
		                requestPresenza.getPosizionamento()
		            );

		            newPresenza.setPunteggio(punteggio);

		            // Aggiungi la nuova presenza alla lista del rapper
		            rapper.getPresenze().add(newPresenza);

		            // 🔥 Aggiorna il rank del rapper sommando il punteggio
		            rapper.setRank(rapper.getRank() + punteggio);

		            // Salva l'entità aggiornata nel DB
		            murettifreestyleRepository.save(muretto);

		            return true;
		        }
		    }

		    return false; // Se il rapper o il muretto non vengono trovati
		}

	 
	 
	 
	 
	 public boolean updatePresenza(String tipo, String valore,String nome, Date data,Presenza nuovaPresenza, String evento, double punteggio) {
		    // Verifica che la data venga settata correttamente
		    System.out.println("🔍 Nuova data da aggiornare: " + nuovaPresenza.getData());

		    // Esegui l'aggiornamento nel repository
		    

		    return murettiFreestyleMongoTemplateRepository.updatePresenzaInArray(
			        tipo,
			        valore,
			        nome,
			        data,
			        evento,
			        punteggio,
			        nuovaPresenza.getData()  // La nuova data da impostare
			    );
		}
	 
	 
	 public void deletePresenza( String valore,String nome, Date data) {

		 	MurettiFreestyleEntity muretto =murettifreestyleRepository.findByTipoAndValore("Muretto", valore).orElseThrow(() -> new InternalServerErrorException("Rapper non trovato"));;
			Rapper rapper  = muretto.getRapper().stream().filter(r -> r.getNome().equalsIgnoreCase(nome)).findFirst().orElseThrow(() -> new InternalServerErrorException("Rapper non trovato"));
			Presenza presenza = rapper.getPresenze().stream().filter(p -> p.getData().equals(data)).findFirst().orElseThrow(() -> new InternalServerErrorException("Presenza non trovata"));
		   	double rank = presenza.getPunteggio();
			rapper.getPresenze().remove(presenza);
			rapper.setRank(rapper.getRank() - rank);
			murettifreestyleRepository.save(muretto);
		}
	 
	 public boolean newRapperToMuretto(String valore, String alias, Rapper rapper) {
		    return murettiFreestyleMongoTemplateRepository.addRapperToMuretto(valore, alias, rapper);
		}
	
	 public boolean deleteRapper( String valore,String nome, String alias) {
		   

		    // Esegui l'aggiornamento nel repository
		 MurettiFreestyleEntity muretto =murettifreestyleRepository.findByTipoAndValore("Muretto", valore).orElseThrow(() -> new InternalServerErrorException("Rapper non trovato"));;
		 Rapper rapper  = muretto.getRapper().stream().filter(r -> r.getNome().equalsIgnoreCase(nome)).findFirst().orElseThrow(() -> new InternalServerErrorException("Rapper non trovato"));

		    return murettiFreestyleMongoTemplateRepository.deleteRapperInMuretto(
			        
			        valore,
			        nome,
			        alias
			         // La nuova data da impostare
			    );
		}
	 
	 public void updateRapper(String tipo, String valore,String nome, String newName, int newRank) {
		    // Verifica che la data venga settata correttamente

		 MurettiFreestyleEntity muretto =murettifreestyleRepository.findByTipoAndValore(tipo, valore).orElseThrow(() -> new InternalServerErrorException("Rapper non trovato"));;
		 Rapper rapper  = muretto.getRapper().stream().filter(r -> r.getNome().equalsIgnoreCase(nome)).findFirst().orElseThrow(() -> new InternalServerErrorException("Rapper non trovato"));
		    // Esegui l'aggiornamento nel repository
		 rapper.setRank(newRank);
		 murettifreestyleRepository.save(muretto);

		}
	 
	 public MurettiFreestyleEntity findMuretto(String tipo, String valore , String alias) {
	        if (!"Muretto".equalsIgnoreCase(tipo)) {
	            throw new IllegalArgumentException("Tipo non valido. Deve essere 'Muretto'");
	        }
	        
	        Optional<MurettiFreestyleEntity> entity = murettifreestyleRepository.findByTipoAndValoreAndAlias(tipo, valore , alias);
	        if (entity.isPresent()) {
	            return entity.get();
	        }
	        throw new IllegalArgumentException("Muretto con tipo: " + tipo + ", valore: " + valore + " e alias: " + alias + " non trovato");
	    }
	 
	 }
