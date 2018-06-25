package core.nmvc;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by One0 on 2018. 6. 4..
 */
public class ControllerScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);

    private ControllerScanner scanner;

    @Before
    public void setup() {
        scanner = new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() throws Exception {
        Map<Class<?>, Object> controllers = scanner.getControllers();

        for (Class<?> controller : controllers.keySet()) {
            logger.info("controller : {}", controller);
        }
    }
}
