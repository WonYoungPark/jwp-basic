package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 9.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class JdbcTemplate {
    public List query(String query, PreparedStatementSetter preparedStatementSetter, RowMapper rowMapper) throws SQLException {
        ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            preparedStatementSetter.setParameters(pstmt);

            rs = pstmt.executeQuery();

            List list = new ArrayList<>();
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public Object queryForObject(String query, PreparedStatementSetter preparedStatementSetter, RowMapper rowMapper) throws SQLException {
        ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)
            ) {
            preparedStatementSetter.setParameters(pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rowMapper.mapRow(rs);
            }
            return null;
        } finally {
            if (rs != null)
                rs.close();
        }
    }

    void update(String query, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {
            preparedStatementSetter.setParameters(pstmt);
            pstmt.execute();
        }
    }
}
