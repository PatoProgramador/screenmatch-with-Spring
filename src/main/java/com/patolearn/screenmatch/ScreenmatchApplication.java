package com.patolearn.screenmatch;

import com.patolearn.screenmatch.main.EjemploStreams;
import com.patolearn.screenmatch.main.Main;
import com.patolearn.screenmatch.model.DatosEpisodio;
import com.patolearn.screenmatch.model.DatosSerie;
import com.patolearn.screenmatch.model.DatosTemporada;
import com.patolearn.screenmatch.repository.SerieRepository;
import com.patolearn.screenmatch.service.ConsumoAPI;
import com.patolearn.screenmatch.service.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}
}