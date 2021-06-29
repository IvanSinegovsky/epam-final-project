package by.epam.carrentalapp.ioc;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class AutowiredAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Logger LOGGER = Logger.getLogger(AutowiredAnnotationObjectConfigurator.class);

    @Override
    public void configure(Object t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object object = ApplicationContext.getObject(field.getType());
                try {
                    field.set(t, object);
                } catch (IllegalAccessException e) {
                    LOGGER.error("AutowiredAnnotationObjectConfigurator configure(...): cannot wire value");
                    throw new InversionOfControlException(
                            "Cannot set interface implementation value to @Autowired marked field"
                    );
                }
            }
        }
    }
}