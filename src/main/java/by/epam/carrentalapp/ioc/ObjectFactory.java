package by.epam.carrentalapp.ioc;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final Logger LOGGER = Logger.getLogger(ObjectFactory.class);

    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    public ObjectFactory() {
        try {
            for (Class<? extends ObjectConfigurator> aClass : ApplicationContext.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
            for (Class<? extends ProxyConfigurator> aClass : ApplicationContext.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
                proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.error("ObjectFactory ObjectFactory(): cannot get class declared fields");
            throw new InversionOfControlException("Cannot get current class declared fields in ObjectFactory constructor");
        }
    }

    public <T> T createObject(Class<T> implClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T t = create(implClass);

        configure(t);

        t = wrapWithProxyIfNeeded(implClass, t);

        return t;
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = (T) proxyConfigurator.replaceWithProxyIfNeeded(t, implClass);
        }
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
