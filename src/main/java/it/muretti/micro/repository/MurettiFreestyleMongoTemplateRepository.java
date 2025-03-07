package it.muretti.micro.repository;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

@Component
public class MurettiFreestyleMongoTemplateRepository {

	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public boolean updatePresenzaInArray(
		     String tipo, 
		     String valore, 
		     String nome,
		     Date data, 
		     String evento, 
		     double punteggio, 
		     Date nuovaData
		 ){
		
		Bson filter = Filters.and(
		        Filters.eq("tipo", tipo),
		        Filters.eq("valore", valore)
		       
		    );
		
		Bson update = Updates.combine(
				Updates.set("rapper.$[elem1].presenze.$[elem].evento", evento),
				Updates.set("rapper.$[elem1].presenze.$[elem].punteggio", punteggio),
				Updates.set("rapper.$[elem1].presenze.$[elem].data", nuovaData)); 
		
		UpdateOptions options = new UpdateOptions()
		        .arrayFilters(List.of(
		            Filters.eq("elem1.nome", nome),
		            Filters.eq("elem.data", data)
		        ));
		
		UpdateResult result = mongoTemplate.getCollection("murettifreestyle").updateMany(filter, update, options);
		
		
						
		
		
		
		return result.getModifiedCount() == 1;
	}
	
	public boolean deletePresenzaInArray(
		      
		     String valore, 
		     String nome,
		     Date data
		 ){
		
		Bson filter = Filters.and(
		        Filters.eq("tipo", "Muretto"),
		        Filters.eq("valore", valore),
		        Filters.elemMatch("rapper", Filters.and( // Trova il rapper con il nome
                        Filters.eq("nome", nome),
                        Filters.elemMatch("presenze", Filters.eq("data", data))
                ))
		       
		    );
		
		Bson update = Updates.pull("rapper.$.presenze",new Document("data",data));
		
		
		
		UpdateResult result = mongoTemplate.getCollection("murettifreestyle").updateOne(filter, update);
		
		
						
		
		
		
		return result.getModifiedCount() == 1;
	}
	
	
	public boolean addRapperToMuretto(String valore, String alias, String nome,  int rank) {
        Bson filter = Filters.and(
            Filters.eq("tipo", "Muretto"),
            Filters.eq("valore", valore),
            Filters.eq("alias", alias)// Trova il documento con questo valore
        );

        Document newRapper = new Document()
            .append("nome", nome)
            .append("rank", rank)
            .append("presenze", List.of()); // Inizialmente senza presenze

        Bson update = Updates.push("rapper", newRapper);

        UpdateResult result = mongoTemplate.getCollection("murettifreestyle").updateOne(filter, update);

        return result.getModifiedCount() == 1;
    }
	
	public boolean deleteRapperInMuretto(
		      
		     String valore, 
		     String nome,
		     String alias
		    
		 ){
		
		Bson filter = Filters.and(
		        Filters.eq("tipo", "Muretto"),
		        Filters.eq("valore", valore),
		        Filters.eq("alias", alias)
		       
		       
		    );
		
		Bson update = Updates.pull("rapper",new Document("nome",nome));
		
		
		
		UpdateResult result = mongoTemplate.getCollection("murettifreestyle").updateOne(filter, update);
		
		
						
		
		
		
		return result.getModifiedCount() == 1;
	}
	
	public boolean updateRapperInArray(
		     String tipo, 
		     String valore, 
		     String nome,
		     String newName,
		     int newRank
		     
		 ){
		
		Bson filter = Filters.and(
		        Filters.eq("tipo", tipo),
		        Filters.eq("valore", valore)
		       
		    );
		
		Bson update = Updates.combine(
				Updates.set("rapper.$[elem].nome",newName),
				Updates.set("rapper.$[elem].rank",newRank));
			
		
		UpdateOptions options = new UpdateOptions()
		        .arrayFilters(List.of(
		        Filters.and(
		            Filters.eq("elem.nome", nome)
		            
		        ))
		        		);
		
		UpdateResult result = mongoTemplate.getCollection("murettifreestyle").updateMany(filter, update, options);
		
		
						
		
		
		
		return result.getModifiedCount() == 1;
	}
	
	
	
}
