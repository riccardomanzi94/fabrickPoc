package it.poc.fabrick.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.poc.fabrick.config.MyEnumDeserializer;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = MyEnumDeserializer.class)
public enum CustomType implements Serializable {

    ENUMERATION("enumeration"),
    VALUE("value");

    private final String value;

    CustomType(String value) {
        this.value = value;

    }

    public String getValue() {
        return value;
    }

}


