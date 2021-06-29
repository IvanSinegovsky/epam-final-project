package by.epam.carrentalapp.ioc;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class JavaConfig implements Config {
    private final Logger LOGGER = Logger.getLogger(JavaConfig.class);

    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass = new HashMap<>();

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                LOGGER.info("No interface implementation priority feature. Current interface has 0/ 2 or more implementations");
                throw new InversionOfControlException(ifc + " no interface implementation priority feature." +
                        " Current interface has 0/ 2 or more implementations");
            }

            return classes.iterator().next();
        });

    }
}
