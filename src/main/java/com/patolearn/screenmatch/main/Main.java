package com.patolearn.screenmatch.main;

import com.patolearn.screenmatch.model.*;
import com.patolearn.screenmatch.repository.SerieRepository;
import com.patolearn.screenmatch.service.ConsumoAPI;
import com.patolearn.screenmatch.service.Conversor;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final Scanner SCANNER = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final Dotenv DOTENV = Dotenv.load();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + DOTENV.get("API_KEY");
    private Conversor conversor = new Conversor();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repository;

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    1 - Buscar series.
                    2 - Buscar episodios.
                    3 - Mostrar historial de series buscadas.
                    0 - Salir.
                    """;
            System.out.println(menu);
            opcion = SCANNER.nextInt();
            SCANNER.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarHistorialSeries();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción invalida");
            }
        }
    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar: ");
        String nombreSerie = SCANNER.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 0; i <= datosSerie.totalTemporadas() ; i++) {
            String json = consumoAPI.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&Season=" + i + API_KEY);
            DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
//        datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repository.save(serie);
        System.out.println(datos);
    }

    private void mostrarHistorialSeries() {
        List<Serie> series = repository.findAll();
//        List<Serie> series = new ArrayList<>();
//
//        series = datosSeries.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}
