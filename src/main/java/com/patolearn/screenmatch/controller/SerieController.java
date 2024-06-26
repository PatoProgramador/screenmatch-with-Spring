package com.patolearn.screenmatch.controller;

import com.patolearn.screenmatch.dto.EpisodioDTO;
import com.patolearn.screenmatch.dto.SerieDTO;
import com.patolearn.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obtenerTodasLasSeries() {
        return serieService.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> otenerTop5() {
        return serieService.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes() {
        return serieService.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerPorId(@PathVariable Long id) {
        return serieService.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id) {
        return serieService.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return serieService.obtenerTemporadasPorNumero(id, numeroTemporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorCategoria(@PathVariable String nombreGenero) {
        return serieService.obtenerSeriesPorCategoria(nombreGenero);
    }
}
