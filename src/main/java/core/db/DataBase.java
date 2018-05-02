package core.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import next.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        User user = new User("wyparks2", "Dnjsdud0$", "박원영", "wyparks2@gmail.com");
        DataBase.addUser(user);

        User user2 = new User("admin", "1234", "관리자", "admin@gmail.com");
        DataBase.addUser(user2);
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
