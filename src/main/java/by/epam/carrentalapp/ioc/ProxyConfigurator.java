package by.epam.carrentalapp.ioc;

public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object t, Class implClass);
}
