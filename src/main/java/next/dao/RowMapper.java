package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 9.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public interface RowMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
