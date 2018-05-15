package next.dao;

import java.sql.ResultSet;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 9.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public interface RowMapper {
    Object mapRow(ResultSet resultSet);
}
