package by.epam.carrentalapp.ioc;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class);

    @Setter
    private static ObjectFactory factory;
    private static Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private static Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public static <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T t = null;

        try {
            t = factory.createObject(implClass);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException  | IllegalAccessException e)  {
            LOGGER.error("ApplicationContext getObject(...): cannot create current interface implementation");
            throw new InversionOfControlException("Cannot create current interface implementation");
        }

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }

        return t;
    }
}