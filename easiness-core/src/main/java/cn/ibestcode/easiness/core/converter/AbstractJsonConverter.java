package cn.ibestcode.easiness.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Slf4j
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
      log.warn(e.getMessage(), e);
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
      log.warn(e.getMessage(), e);
    }
    return null;
  }
}
