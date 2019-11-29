package cn.ibestcode.easiness.utils;

import cn.ibestcode.easiness.utils.exception.UtilsException;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

@Slf4j
public class SerializationUtil {

  // 把 Object 对象转化为 byte数组
  public static String serialization(Object obj) {
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    byte[] bytes;
    try {
      ObjectOutputStream oo = new ObjectOutputStream(bo);
      oo.writeObject(obj);
      bytes = bo.toByteArray();
    } catch (IOException e) {
      log.warn(e.getMessage(), e);
      throw new UtilsException("IOException", e);
    }
    return DatatypeConverter.printBase64Binary(bytes);
  }

  // 把 byte数组 还原为 Object 对象
  public static Object deserialization(String string) {
    if (string == null) {
      return null;
    }
    ByteArrayInputStream bi = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(string));
    ObjectInputStream in;
    Object obj = null;
    try {
      in = new ObjectInputStream(bi);
      obj = in.readObject();
    } catch (ClassNotFoundException e) {
      log.warn(e.getMessage(), e);
      throw new UtilsException("ClassNotFoundException", e);
    } catch (IOException e) {
      log.warn(e.getMessage(), e);
      throw new UtilsException("IOException", e);
    }
    return obj;
  }
}
