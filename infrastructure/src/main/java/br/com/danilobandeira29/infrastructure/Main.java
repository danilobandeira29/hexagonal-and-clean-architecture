package br.com.danilobandeira29.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// Não é ideal está na mesma aplicação, pois quando essa aplicação escalar terão N máquinas rodando o mesmo Schedule de 1 em 1 minuto
@EnableScheduling
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
