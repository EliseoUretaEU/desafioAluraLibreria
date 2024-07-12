package com.desafio_libreria.libreriaAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConvierteDatos {
    <T> T obtenerDatosLibros(String json, Class<T>clase) throws JsonProcessingException;
}
