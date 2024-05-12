package com.patolearn.screenmatch;

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
		ConsumoAPI consumoAPI = new ConsumoAPI();
		Conversor conversor = new Conversor();
		//Se obtiene la serie
		String json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=7b4848af");
		System.out.println(json);
		DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);
		// Se obtiene el primer episodio de la primer season
		json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=7b4848af");
		DatosEpisodio episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodio);
		// Se obtiene la primera temporada
		List<DatosTemporada> temporadas = new ArrayList<>();
		// se añade cada temporada a la lista
		for (int i = 1; i <= datos.totalTemporadas(); i++) {
			json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=" + i +"&apikey=7b4848af");
			DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
			temporadas.add(datosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}