//package com.patolearn.screenmatch;
//
//import com.patolearn.screenmatch.main.Main;
//import com.patolearn.screenmatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationConsola implements CommandLineRunner {
//
//	@Autowired
//	private SerieRepository repository;
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Main main = new Main(repository);
//		main.mostrarMenu();
//
////		EjemploStreams ejemploStreams = new EjemploStreams();
////		ejemploStreams.muestraEjemplo();
//	}
//}