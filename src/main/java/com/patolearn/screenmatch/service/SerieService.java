package com.patolearn.screenmatch.service;

import com.patolearn.screenmatch.dto.EpisodioDTO;
import com.patolearn.screenmatch.dto.SerieDTO;
import com.patolearn.screenmatch.model.Serie;
import com.patolearn.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obtenerTodasLasSeries() {
        return conversorDto(serieRepository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return conversorDto(serieRepository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes() {
        return conversorDto(serieRepository.lanzamientosMasRecientes());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                    s.getEvaluacion(), s.getGenero(),
                    s.getSinopsis(), s.getPoster(), s.getActores());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<SerieDTO> conversorDto(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getEvaluacion(), s.getGenero(),
                        s.getSinopsis(), s.getPoster(), s.getActores()))
                .collect(Collectors.toList());
    }
}
