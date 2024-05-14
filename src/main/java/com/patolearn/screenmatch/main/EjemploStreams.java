package com.patolearn.screenmatch.main;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo() {
        List<String> nombres = Arrays.asList("Brenda", "Luis", "Pato", "Eric", "Genesys");

        nombres.stream()
                .sorted()
                .filter(n -> n.startsWith("P"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
