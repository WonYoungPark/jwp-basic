package core.di.factory;

import com.google.common.collect.Maps;
import core.di.factory.injector.ConstructorInjector;
import core.di.factory.injector.FieldInjector;
import core.di.factory.injector.Injector;
import core.di.factory.injector.SetterInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by One0 on 2018. 6. 27..
 */
public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;
    private Map<Class<?>, Object> beans = Maps.newHashMap();
    private List<Injector> injectors;

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;

        injectors = Arrays.asList(
            new FieldInjector(this),
            new SetterInjector(this),
            new ConstructorInjector(this)
        );
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstanticateBeans) {
            if (beans.get(clazz) == null) {
                logger.debug("instantiate Class {}", clazz);
                inject(clazz);
            }
        }
    }

    private void inject(Class<?> clazz) {
        for (Injector injector : injectors) {
            injector.injector(clazz);
        }
    }

    public void registerBean(Class<?> clazz, Object bean) {
        if (!beans.containsKey(clazz))
            beans.put(clazz, bean);
    }
}
