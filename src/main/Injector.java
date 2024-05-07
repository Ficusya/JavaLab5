import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import interfaces.AutoInjectable;

/**
 *  Class responsible for dependency injection based on annotations
 */
public class Injector {

    /**
     * Path to the properties file for interface implementations
     */
    private static final String pathToFile = "src/main/properties";


    /**
     * Injects dependencies into object based on annotations
     * @param objectToInject - object to inject dependencies into
     * @param <T> - implementation type
     * @return implementation object with injected dependencies
     */
    public <T> T inject(T objectToInject) {
        Properties properties = loadProperties(pathToFile);

        for (Field field : objectToInject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> fieldType = field.getType();
                String interfaceName = properties.getProperty(fieldType.getName());

                try {
                    Object implementation = Class.forName(interfaceName).newInstance();
                    field.setAccessible(true);
                    field.set(objectToInject, implementation);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return objectToInject;
    }

    /**
     * Loads properties from file
     * @param path - to the properties file
     * @return - loaded properties
     */
    public Properties loadProperties(String path){
        Properties properties = new Properties();
        try{properties.load(new FileInputStream(path));
        } catch (IOException e){
            e.printStackTrace();
        }
        return properties;
    }
}
