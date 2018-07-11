package core.di.factory;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by One0 on 2018. 6. 4..
 */
public class BeanScanner {
    private Reflections reflections;

    public BeanScanner(Object... basePakage) {
        this.reflections = new Reflections(basePakage);
    }

    public Set<Class<?>> scan() {
        return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInitiatedBean = new HashSet<>();

        for (Class<? extends Annotation> annotation : annotations) {
            preInitiatedBean.addAll(reflections.getTypesAnnotatedWith(annotation));
        }

        return preInitiatedBean;
    }
}
