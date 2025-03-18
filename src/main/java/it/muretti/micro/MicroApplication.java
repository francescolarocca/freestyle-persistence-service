package it.muretti.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import it.muretti.micro.conf.RankPointTable;

@SpringBootApplication
//@ComponentScan(basePackages = "it.muretti.micro.conf") // Se necessario, specifica il pacchetto corretto
@EnableConfigurationProperties(RankPointTable.class)
public class MicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroApplication.class, args);
	}

}
