package com.patolearn.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String evaluacion)
{
}
