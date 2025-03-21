package it.muretti.micro.controller;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import it.muretti.micro.entity.MurettiFreestyleEntity;
import it.muretti.micro.entity.Rapper;
import it.muretti.micro.repository.MurettiFreestyleRepository;
import it.muretti.micro.service.MurettiFreestyleService;

import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/murettifreestyle")
public class AvatarController {

    @Autowired
    private MurettiFreestyleService murettifreestyleService; // Repository MongoDB per gli utenti
    @Autowired
    MurettiFreestyleRepository murettiFreeStyleRepository;

    

    @PostMapping("/upload-avatar/{tipo}/{valore}/{nome}/{alias}")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file,@PathVariable String tipo,@PathVariable String valore, @PathVariable String nome,@PathVariable String alias) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File non selezionato");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Crea la cartella 'uploads' se non esiste
            Path uploadPath = Paths.get("C:/Users/39347/Desktop/upload");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Salva il file nella cartella 'uploads'
            Path filePath = uploadPath.resolve(originalFileName);
            file.transferTo(filePath.toFile());

            // Genera l'URL dell'immagine
            String avatarUrl = "http://localhost:8080/uploads/" + originalFileName; // Assicurati che il server serva correttamente il file

            // Recupera l'utente dal database e aggiorna l'URL dell'immagine
            MurettiFreestyleEntity murettiFreestyleEntity = murettifreestyleService.findMuretto(tipo, valore, alias);

         // Cerca il rapper specifico nella lista e aggiorna solo quello
            murettiFreestyleEntity.getRapper().stream()
             .filter(rapper -> rapper.getNome().equalsIgnoreCase(nome))
             .findFirst()
             .ifPresent(rapper -> rapper.setAvatarUrl(avatarUrl));

         // Salva l'entità aggiornata
            murettiFreeStyleRepository.save(murettiFreestyleEntity);

            return ResponseEntity.ok().body("{\"avatarUrl\": \"" + avatarUrl + "\"}"); // Risponde con l'URL dell'immagine
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Errore nel caricamento dell'immagine");
        }
    
    }
    }
