package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by wyparks2@gmail.com on 2018. 7. 17.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class SetterInjector extends AbtractInjector {
    private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        Method method = (Method) injectedBean;
        logger.debug("invoke method: {}", method);

        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != 1) {
            throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
        }

        return paramTypes[0];
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Method method = (Method) injectedBean;

        try {
            method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void injector(Class<?> clazz) {
        instantiateClass(clazz);
        Set<Method> injectedMethods = BeanFactoryUtils.getInjectedMethods(clazz);

        for (Method method : injectedMethods) {
            logger.debug("invoke method : {}", method);
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1) {
                throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
            }

            Class<?> concreteClazz = null;
            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }

            try {
                method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * @Inject 애노테이션이 설정되어 있는 생성자가 없으면 해당 메서드로 인스턴스 생성
     * @param clazz
     * @return
     */
    public Object instantiateClass(Class<?> clazz) {
        return null;
    }
}
