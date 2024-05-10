package com.patolearn.screenmatch;

import com.patolearn.screenmatch.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		String json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=7b4848af");
		System.out.println(json);
	}
}