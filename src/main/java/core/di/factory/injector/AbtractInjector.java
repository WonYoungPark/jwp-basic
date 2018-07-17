package core.di.factory.injector;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

/**
 * Created by wyparks2@gmail.com on 2018. 7. 17.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public abstract class AbtractInjector implements Injector {
    private BeanFactory beanFactory;

    public AbtractInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void injector(Class<?> clazz) {
        instantiateClass(clazz);

        Set<?> injectedBeans = getInjectedBeans(clazz);
        for (Object injectedBean : injectedBeans) {
            Class<?> beanClass = getBeanClass(injectedBean);
            inject(injectedBean, instantiateClass(beanClass), beanFactory);
        }
    }

    abstract Set<?> getInjectedBeans(Class<?> clazz);

    abstract Class<?> getBeanClass(Object injectedBean);

    abstract void inject(Object injectedBean, Object bean, BeanFactory beanFactory);

    /**
     * @Inject 클래스 인스턴스 생성
     * @param clazz
     * @return
     */
    private Object instantiateClass(Class<?> clazz) {
        Class<?> concreteClass = findBeanClass(clazz, beanFactory.getPreInstanticateBeans());
        Object bean = beanFactory.getBean(concreteClass);
        if (bean != null)
            return bean;

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);
        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(concreteClass, bean);
            beanFactory.registerBean(concreteClass, bean);
            return bean;
        }

        bean = injectedConstructor(injectedConstructor);
        beanFactory.registerBean(concreteClass, bean);

        return bean;
    }

    /**
     * @Inject 애노테이션이 설정되어 있는 생성자가 존재하면 해당 메서드로 인스턴스 생성
     * @param constructor
     * @return
     */
    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : parameterTypes) {
            Class<?> concreteClass = findBeanClass(clazz, beanFactory.getPreInstanticateBeans());
            Object bean = beanFactory.getBean(concreteClass);
            if (bean == null)
                bean = instantiateClass(concreteClass);

            args.add(bean);

        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    private Class<?> findBeanClass(Class<?> clazz, Set<Class<?>> preInstanticateBeans) {
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);

        if (!preInstanticateBeans.contains(concreteClazz)) {
            throw new IllegalStateException(clazz + "는 Bean이 아니다.");

        }
        return concreteClazz;
    }
}
