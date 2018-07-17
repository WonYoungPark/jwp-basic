package core.di.factory.injector;

import core.di.factory.BeanFactory;

import java.util.Set;

/**
 * Created by wyparks2@gmail.com on 2018. 7. 17.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class ConstructorInjector extends AbtractInjector {

    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return null;
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        return null;
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {

    }
}
