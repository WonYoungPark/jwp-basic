package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getFields()).forEach(field ->
            logger.debug(field.toString())
        );
        Arrays.stream(clazz.getConstructors()).forEach(constructor ->
            logger.debug(constructor.toString())
        );
        Arrays.stream(clazz.getMethods()).forEach(method ->
            logger.debug(method.toString())
        );
        Arrays.stream(clazz.getDeclaredFields()).forEach(field ->
            logger.debug(field.toString())
        );
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(constructor ->
            logger.debug(constructor.toString())
        );
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method ->
            logger.debug(method.toString())
        );
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Arrays
                .stream(clazz.getDeclaredConstructors())
                .forEach(constructor -> {
                    try {
                        User user = (User) constructor.newInstance("1", "2", "3", "4");
                        logger.info(user.toString());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }
    
    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();

        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "주한");

        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.setInt(student, 14);

        logger.info("{} {}", student.getName(), student.getAge());
    }
}
