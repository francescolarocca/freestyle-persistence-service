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
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file,
                                          @PathVariable String tipo,
                                          @PathVariable String valore,
                                          @PathVariable String nome,
                                          @PathVariable String alias) {

        System.out.println("▶ Inizio upload avatar per " + nome);
        
        if (file.isEmpty()) {
            System.out.println("❌ Errore: Nessun file ricevuto.");
            return ResponseEntity.badRequest().body("File non selezionato");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("📂 Nome file ricevuto: " + originalFileName);

        try {
            // Crea la cartella 'uploads' se non esiste
            Path uploadPath = Paths.get("C:/Users/39347/Desktop/upload");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("📂 Creata cartella upload.");
            }

            // Salva il file
            Path filePath = uploadPath.resolve(originalFileName);
            file.transferTo(filePath.toFile());
            System.out.println("✅ File salvato in: " + filePath.toString());

            // Genera URL accessibile per il frontend
            String avatarUrl = "http://localhost:8080/uploads/" + originalFileName;
            System.out.println("🌍 URL avatar: " + avatarUrl);

            // Recupera il muretto dal database
            MurettiFreestyleEntity murettiFreestyleEntity = murettifreestyleService.findMuretto(tipo, valore, alias);
            if (murettiFreestyleEntity == null) {
                System.out.println("❌ Errore: Muretto non trovato.");
                return ResponseEntity.status(404).body("Muretto non trovato");
            }

            // Cerca il rapper specifico nella lista e aggiorna solo quello
            boolean rapperUpdated = murettiFreestyleEntity.getRapper().stream()
                .filter(rapper -> rapper.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .map(rapper -> {
                    rapper.setAvatarUrl(avatarUrl);
                    return true;
                }).orElse(false);

            if (!rapperUpdated) {
                System.out.println("❌ Errore: Rapper non trovato.");
                return ResponseEntity.status(404).body("Rapper non trovato nel database");
            }

            // Salva le modifiche nel database
            murettiFreeStyleRepository.save(murettiFreestyleEntity);
            System.out.println("💾 Dati aggiornati nel database.");

            return ResponseEntity.ok().body("{\"avatarUrl\": \"" + avatarUrl + "\"}");
        } catch (IOException e) {
            System.out.println("❌ Errore durante il caricamento dell'immagine:");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Errore nel caricamento dell'immagine");
        }
    }

    }
