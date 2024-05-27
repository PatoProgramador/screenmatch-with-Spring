package com.patolearn.screenmatch.dto;

import com.patolearn.screenmatch.model.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double evaluacion,
        Categoria genero,
        String sinopsis,
        String poster,
        String actores
) {
}
