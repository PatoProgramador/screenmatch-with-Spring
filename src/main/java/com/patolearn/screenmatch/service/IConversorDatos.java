package com.patolearn.screenmatch.service;

public interface IConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
