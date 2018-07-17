package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by wyparks2@gmail.com on 2018. 7. 17.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class FieldInjector extends AbtractInjector {
    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        Field field = (Field) injectedBean;
        return field.getType();
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Field field = (Field) injectedBean;

        try {
            field.setAccessible(true);
            field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            logger.error(e.getMessage());
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
