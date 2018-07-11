package core.di.factory;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by One0 on 2018. 6. 4..
 */
public class BeanScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(BeanScannerTest.class);

    private BeanScanner scanner;

    @Before
    public void setup() {
        scanner = new BeanScanner("core.di.factory.example");
    }

    @Test
    public void getControllers() throws Exception {
        Set<Class<?>> beans = scanner.scan();
        for(Class clazz : beans){
            logger.debug("bean {}", clazz.getName());
        }
    }
}
