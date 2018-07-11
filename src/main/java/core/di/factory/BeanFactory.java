package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstanticateBeans) {
            Object bean = instantiateClass(clazz);

            if (bean != null && !beans.containsKey(clazz))
                beans.put(clazz, bean);
        }
    }

    /**
     * @Inject 애노테이션이 설정되어 있는 생성자가 없으면 해당 메서드로 인스턴스 생성
     * @param clazz
     * @return
     */
    public Object instantiateClass(Class<?> clazz) {
        Constructor<?> constructors = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (constructors != null) {
            return instantiateConstructor(constructors);
        }

        return BeanUtils.instantiate(clazz);
    }

    /**
     * @Inject 애노테이션이 설정되어 있는 생성자가 존재하면 해당 메서드로 인스턴스 생성
     * @param constructor
     * @return
     */
    public Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : parameterTypes) {
            clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);

            Object bean = instantiateClass(clazz);

            if (bean != null)
                args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();

        for (Class<?> clazz : preInstanticateBeans) {
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller != null) {
                controllers.put(clazz, beans.get(clazz));
            }
        }

        return controllers;
    }
}
