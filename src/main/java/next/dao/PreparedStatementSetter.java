package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 9.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
@FunctionalInterface
public interface PreparedStatementSetter {
    void setParameters(PreparedStatement preparedStatement) throws SQLException;
}
