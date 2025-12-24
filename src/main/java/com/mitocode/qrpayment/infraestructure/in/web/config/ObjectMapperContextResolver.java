package com.mitocode.qrpayment.infraestructure.in.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// “Cuando el framework necesite un ObjectMapper para serializar o deserializar JSON, usa este que yo configuré.
public class ObjectMapperContextResolver  {

    private final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Para que no devuelva timestamps
    }

}