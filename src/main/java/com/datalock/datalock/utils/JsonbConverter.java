package com.datalock.datalock.utils;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

@Converter(autoApply = false)
public class JsonbConverter implements AttributeConverter<String, PGobject> {

    @Override
    public PGobject convertToDatabaseColumn(String json) {
        try {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(json);
            return pgObject;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting String to jsonb", e);
        }
    }

    @Override
    public String convertToEntityAttribute(PGobject dbData) {
        return dbData != null ? dbData.getValue() : null;
    }
}
