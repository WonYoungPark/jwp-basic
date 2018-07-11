package core.di.factory;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by One0 on 2018. 6. 27..
 */
public class BeanFactoryTest {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactoryTest.class);

    private BeanFactory beanFactory;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        BeanScanner scanner = new BeanScanner("core.di.factory.example");
        beanFactory = new BeanFactory(scanner.scan());
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    public void getControllers() throws Exception {
        Map<Class<?>, Object> controllers = beanFactory.getControllers();

        for (Class<?> controller : controllers.keySet()) {
            logger.info("controller : {}", controller);
        }
    }
}
