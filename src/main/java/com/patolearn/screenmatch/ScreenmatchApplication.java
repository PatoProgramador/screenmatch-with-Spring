package com.patolearn.screenmatch;

import com.patolearn.screenmatch.main.EjemploStreams;
import com.patolearn.screenmatch.main.Main;
import com.patolearn.screenmatch.model.DatosEpisodio;
import com.patolearn.screenmatch.model.DatosSerie;
import com.patolearn.screenmatch.model.DatosTemporada;
import com.patolearn.screenmatch.service.ConsumoAPI;
import com.patolearn.screenmatch.service.Conversor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.mostrarMenu();

//		EjemploStreams ejemploStreams = new EjemploStreams();
//
//		ejemploStreams.muestraEjemplo();
	}
}