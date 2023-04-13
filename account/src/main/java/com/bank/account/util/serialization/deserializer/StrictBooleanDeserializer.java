package com.bank.account.util.serialization.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;

/**
 * Этот десериалайзер не позволяет передавать ничего, кроме
 * {@code  'true'} or {@code 'false'} в JSON в полях Boolean
 * <p>
 *      Используйте {@link JsonDeserialize} над {@code Boolean} полем по необходимости
 * </p>
 * @author Илья Пономарев
 */
public class StrictBooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext) throws IOException {
        final String value = jsonParser.getValueAsString();
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw new JsonParseException(jsonParser, "Invalid boolean value");
    }
}
