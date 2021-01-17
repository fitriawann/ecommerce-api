package com.gtech.ecommerce.repositories;

import com.gtech.ecommerce.domain.UpdateUser;
import com.gtech.ecommerce.domain.User;
import com.gtech.ecommerce.exceptions.EcAuthException;
import com.gtech.ecommerce.exceptions.EcBadRequestException;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO EC_USERS (USER_ID, FIRST_NAME, LAST_NAME, GENDER, BIRTHDAY, PHONE, EMAIL, PASSWORD) VALUES (NEXTVAL('EC_USERS_SEQ'), ?, ?, ?::gen, ?::date, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM EC_USERS WHERE EMAIL = ?";
    private static final String SQL_COUNT_BY_PHONE = "SELECT COUNT(*) FROM EC_USERS WHERE PHONE = ?";
    private static final String SQL_COUNT_BY_IDENTITY = "SELECT COUNT(*) FROM EC_USERS WHERE PHONE = ? OR EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, GENDER, BIRTHDAY, PHONE, EMAIL, PASSWORD FROM EC_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_IDENTITY = "SELECT USER_ID, FIRST_NAME, LAST_NAME, GENDER, BIRTHDAY, PHONE, EMAIL, PASSWORD FROM EC_USERS WHERE PHONE = ? OR EMAIL = ?";
    private static final String SQL_UPDATE = "UPDATE EC_USERS SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?::gen, BIRTHDAY = ?::date WHERE USER_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String gender, String birthday, String phone, String email, String password) throws EcAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, gender);
                ps.setString(4, birthday);
                ps.setString(5, phone);
                ps.setString(6, email);
                ps.setString(7, hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch(Exception e) {
            throw new EcAuthException("Invalid details. Failed to create account.");
        }
    }

    @Override
    public User findByIdentityAndPassword(String identity, String password) throws EcAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_IDENTITY, new Object[]{identity, identity}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new EcAuthException("invalid identity/password");
            return user;
        } catch(EmptyResultDataAccessException e) {
            throw new EcAuthException("Invalid identity/password");
        }
    }

    @Override
    public Integer getCountByPhone(String phone) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_PHONE, new Object[]{phone}, Integer.class);
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public Integer getCountByIdentity(String identity) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_IDENTITY, new Object[]{identity}, Integer.class);
    }

    @Override
    public User findById(Integer userId) throws EcResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
        } catch (Exception e) {
            throw new EcResourceNotFoundException("User not found");
        }
    }

    @Override
    public void update(Integer userId, UpdateUser updateUser) throws EcBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{updateUser.getFirstName(), updateUser.getLastName(), updateUser.getGender(), updateUser.getBirthday(), userId});
        } catch (Exception e) {
            throw new EcBadRequestException("Invalid request");
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("GENDER"),
                rs.getString("BIRTHDAY"),
                rs.getString("PHONE"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
}
