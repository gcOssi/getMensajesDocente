package cl.duoc.getmensajes.util;

import java.util.Base64;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UtilEnDecoder {
  private static final Logger logger = Logger.getLogger(UtilEnDecoder.class.getName());

  public static String decode(String encodedString){
    byte[] decodedBytes = null;
    String decodedString = null;
    try{
      decodedBytes = Base64.getDecoder().decode(encodedString);
      decodedString = new String(decodedBytes);
    } catch (Exception ex){
      logger.log(Level.SEVERE,"Error al decodificar",ex);
    }

    return decodedString;
  }


}
