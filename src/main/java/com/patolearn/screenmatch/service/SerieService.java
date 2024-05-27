package com.patolearn.screenmatch.service;

import com.patolearn.screenmatch.dto.SerieDTO;
import com.patolearn.screenmatch.model.Serie;
import com.patolearn.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<SerieDTO> conversorDto(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getTitulo(), s.getTotalTemporadas(),
                        s.getEvaluacion(), s.getGenero(),
                        s.getSinopsis(), s.getPoster(), s.getActores()))
                .collect(Collectors.toList());
    }
}
