package next.dao;

import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws DataAccessException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = ps -> {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
        };
        try {
            jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pss);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(User user) throws DataAccessException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = ps -> {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUserId());
        };

        try {
            jdbcTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?", pss);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<User> findAll() throws DataAccessException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = ps -> {
        };

        RowMapper<User> rowMapper = resultSet -> {
            return new User(
            resultSet.getString("userId"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("email"));
        };

        try {
            return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", pss, rowMapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public User findByUserId(String userId) throws DataAccessException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = ps -> {
            ps.setString(1, userId);
        };

        RowMapper<User> rowMapper = resultSet -> {
            return new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email"));
        };
        try {
            return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", pss, rowMapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}