package cl.duoc.getmensajes.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.lang.Exception;
import java.util.Enumeration;

public class UtilProperties {

	private static final Logger logger = Logger.getLogger(UtilProperties.class.getName());
	private Properties prop = null;
	private InputStream input = null;

	public void loadFile(String path, boolean logProperties){
		try {

			input = new FileInputStream(path);
			prop = new Properties();

			// Cargar archivo de propiedades
			prop.load(input);
			if(logProperties){
				logProperties(prop);
			}

		} catch (IOException ex) {
			logger.log(Level.SEVERE,"Error al leer archivo de propiedades",ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {
					logger.log(Level.INFO,"Error al cerrar archivo de propiedades",ex);
				}
			}
		}
	}

	public void unloadProperties(){
		prop = null;
	}

	public void logProperties(Properties props){
		@SuppressWarnings("unchecked")
    Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
    while (enums.hasMoreElements()) {
      String key = enums.nextElement();
      String value = props.getProperty(key);
      logger.info(key + " : " + value);
    }
	}

	public String get(String strProp){
		String valueProp = null;
		try{
				valueProp = prop.getProperty(strProp);
		} catch (Exception e){
			logger.log(Level.SEVERE, "Error al obtener propiedad" + e);
		}

		return valueProp;
	}


}
