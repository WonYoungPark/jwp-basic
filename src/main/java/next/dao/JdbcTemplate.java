package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 9.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class JdbcTemplate {
    public <T> List<T> query(String query, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
        ) {
            preparedStatementSetter.setParameters(pstmt);

            rs = pstmt.executeQuery();

            List<T> list = new ArrayList<>();
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

    public <T> T queryForObject(String query, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
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

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... values) throws SQLException {

        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            for(int index = 0; index < values.length; index++) {
                pstmt.setObject(index + 1, values[index]);
            }

            if (rs.next()) {
                return rowMapper.mapRow(rs);
            }
            return null;
        }
    }

    void update(String query, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {
            preparedStatementSetter.setParameters(pstmt);
            pstmt.execute();
        }
    }

    void update(String query, Object... parameters) throws SQLException {
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {

            for(int index = 0; index < parameters.length; index++) {
                pstmt.setObject(index + 1, parameters[index]);
            }
            pstmt.execute();
        }
    }
}
