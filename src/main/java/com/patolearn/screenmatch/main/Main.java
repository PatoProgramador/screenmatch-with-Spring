package com.patolearn.screenmatch.main;

import com.patolearn.screenmatch.model.DatosSerie;
import com.patolearn.screenmatch.model.DatosTemporada;
import com.patolearn.screenmatch.service.ConsumoAPI;
import com.patolearn.screenmatch.service.Conversor;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner SCANNER = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Dotenv dotenv = Dotenv.load();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + dotenv.get("API_KEY");
    private Conversor conversor = new Conversor();

    public void mostrarMenu() {
        System.out.println(API_KEY);
        System.out.println("Por favor, escribe el nombre de la serie que deseas buscar: ");
        // Buscar datos generales de la serie
        String nombreSerie = SCANNER.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);
        // Busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        // se añade cada temporada a la lista
        for (int i = 1; i <= datos.totalTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}