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
    private List<Serie> series;

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
                    4 - Buscar series por título.
                    5 - Top 5 mejores series.
                    6 - Buscar series por categoria.
                    7 - Filtrar series por temporadas y evaluación.
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
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    buscarSeriePorNumeroTemporadaEvaluacion();
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
        mostrarHistorialSeries();
        System.out.println("Escribe el nombre de la serie la cual quieres ver los episodios: ");
        String nombreSerie = SCANNER.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            Serie serieEncontrada = serie.get();
            List<DatosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas() ; i++) {
                String json = consumoAPI.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + API_KEY);
                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                                    .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        } else {
            System.out.println("La serie no fue encontrada :(");
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
//        datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repository.save(serie);
        System.out.println(datos);
    }

    private void mostrarHistorialSeries() {
        series = repository.findAll();
//        List<Serie> series = new ArrayList<>();
//
//        series = datosSeries.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar: ");
        String nombreSerie = SCANNER.nextLine();
        Optional<Serie> serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: " + serieBuscada.get());
        } else {
            System.out.println("Serie no encontrada :(");
        }
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repository.findTop5ByOrderByEvaluacionDesc();

        topSeries.forEach(s -> System.out.println("Serie: " + s.getTitulo() + ", Evaluacion: " + s.getEvaluacion()));
    }

    private void buscarSeriePorCategoria() {
        System.out.println("Escribe el genero/categoria de la serie que desea buscar: ");
        String genero = SCANNER.nextLine();
        Categoria categoria = Categoria.fromEspanol(genero);

        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);
        System.out.println("Las series de la categoria " + genero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarSeriePorNumeroTemporadaEvaluacion() {
        System.out.println("Ingresa el número de temporadas bajo el cual quieres filtrar las series: ");
        int numTemporadas = SCANNER.nextInt();
        System.out.println("Ingresa el número de evaluación a partir del cual quieres filtrar las series: ");
        Double userEvaluacion = SCANNER.nextDouble();

        List<Serie> seriesFiltradas = repository.seriesPorTemporadaYEvaluacion(numTemporadas, userEvaluacion);

        seriesFiltradas.forEach(s -> System.out.println(s.getTitulo() + ", Evaluación:" + s.getEvaluacion() + ", Total de temporadas: " + s.getTotalTemporadas()));
    }
}
