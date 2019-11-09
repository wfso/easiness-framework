package cn.ibestcode.easiness.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public abstract class AbstractJsonConverter<T> implements AttributeConverter<T, String> {

  protected static final ObjectMapper objectMapper = new ObjectMapper();

  abstract Class<T> getTargetClass();

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (attribute == null)
      return null;
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.trim().length() == 0)
      return null;
    try {
      return objectMapper.readValue(dbData, getTargetClass());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
