package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private final String SELECT_ALL_FROM_USERS = "SELECT * FROM users;";
    private final String SELECT_ALL_FROM_USERS_WHERE_EMAIL_EQUALS = "SELECT * FROM users WHERE email = ?;";
    private final String SELECT_ALL_FROM_USERS_WHERE_USER_ID_EQUALS = "SELECT * FROM users WHERE user_id = ?;";
    private final String INSERT_INTO_USERS = "INSERT INTO users(email, password, name, lastname) VALUES (?,?,?,?);";

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet usersResultSet = statement.executeQuery(SELECT_ALL_FROM_USERS)) {

            while (usersResultSet.next()){
                Long userId = usersResultSet.getLong(USER_ID_COLUMN_NAME);
                String email = usersResultSet.getString(EMAIL_COLUMN_NAME);
                String password = usersResultSet.getString(PASSWORD_COLUMN_NAME);
                String name = usersResultSet.getString(NAME_COLUMN_NAME);
                String lastname = usersResultSet.getString(LASTNAME_COLUMN_NAME);

                allUsers.add(new User(userId, email, password, name, lastname));
            }
        } catch (SQLException e) {
            LOGGER.error("UserDaoImpl findAll(...): cannot extract user from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("UserDaoImpl findAll(...): connection pool crashed");
            throw new DaoException(e);
        }

        return allUsers;
    }

    @Override
    public Optional<User> findByEmail(String emailToFind) {
        Optional<User> userOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_USERS_WHERE_EMAIL_EQUALS)) {

            preparedStatement.setString(1, emailToFind);
            ResultSet userResultSet = preparedStatement.executeQuery();

            if (userResultSet.next()) {
                userOptional = Optional.of(extractUserFromResultSet(userResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("UserDaoImpl findByEmail(...): cannot extract user from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("UserDaoImpl findByEmail(...): connection pool crashed");
            throw new DaoException(e);
        }

        return userOptional;
    }

    @Override
    public Long save(User userToSave) {
        Long savedUserId = null;

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_USERS,
                    Statement.RETURN_GENERATED_KEYS
            )) {

            preparedStatement.setString(1, userToSave.getEmail());
            preparedStatement.setString(2, userToSave.getPassword());
            preparedStatement.setString(3, userToSave.getName());
            preparedStatement.setString(4, userToSave.getLastname());

            preparedStatement.executeUpdate();
            ResultSet userResultSet = preparedStatement.getGeneratedKeys();

            if (userResultSet != null && userResultSet.next()) {
                savedUserId = userResultSet.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("UserDaoImpl save(...): cannot insert user record");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("UserDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }

        return savedUserId;
    }

    @Override
    public Optional<User> findByUserId(Long userIdToFind) {
        Optional<User> userOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_USERS_WHERE_USER_ID_EQUALS)) {

            preparedStatement.setLong(1, userIdToFind);
            ResultSet userResultSet = preparedStatement.executeQuery();

            if (userResultSet.next()) {
                userOptional = Optional.of(extractUserFromResultSet(userResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("UserDaoImpl findByEmail(...): cannot extract user from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("UserDaoImpl findByEmail(...): connection pool crashed");
            throw new DaoException(e);
        }

        return userOptional;
    }

    private User extractUserFromResultSet(ResultSet userResultSet) throws SQLException {
        Long userId = userResultSet.getLong(USER_ID_COLUMN_NAME);
        String email = userResultSet.getString(EMAIL_COLUMN_NAME);
        String password = userResultSet.getString(PASSWORD_COLUMN_NAME);
        String name = userResultSet.getString(NAME_COLUMN_NAME);
        String lastname = userResultSet.getString(LASTNAME_COLUMN_NAME);

        return new User(userId, name, lastname, email, password);
    }
}
