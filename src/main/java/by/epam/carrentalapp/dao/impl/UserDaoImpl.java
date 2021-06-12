package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.query.UserQuery;
import by.epam.carrentalapp.bean.entity.user.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet usersResultSet = statement.executeQuery(UserQuery.SELECT_ALL_FROM_USERS.getQuery())) {

            while (usersResultSet.next()){
                Long userId = usersResultSet.getLong(USER_ID_COLUMN_NAME);
                String email = usersResultSet.getString(EMAIL_COLUMN_NAME);
                String password = usersResultSet.getString(PASSWORD_COLUMN_NAME);
                String name = usersResultSet.getString(NAME_COLUMN_NAME);
                String lastname = usersResultSet.getString(LASTNAME_COLUMN_NAME);

                allUsers.add(new User(userId, email, password, name, lastname));
            }
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
        }

        return allUsers;
    }

    @Override
    public Optional<User> findByEmail(String emailToFind) {
        Optional<User> userOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UserQuery
                    .SELECT_ALL_FROM_USERS_WHERE_EMAIL_EQUALS.getQuery())) {

            preparedStatement.setString(1, emailToFind);
            ResultSet userResultSet = preparedStatement.executeQuery();

            if (userResultSet.next()) {
                Long userId = userResultSet.getLong(USER_ID_COLUMN_NAME);
                String email = userResultSet.getString(EMAIL_COLUMN_NAME);
                String password = userResultSet.getString(PASSWORD_COLUMN_NAME);
                String name = userResultSet.getString(NAME_COLUMN_NAME);
                String lastname = userResultSet.getString(LASTNAME_COLUMN_NAME);

                userOptional = Optional.of(new User(userId, email, lastname, name, password));
            }

        } catch (SQLException | ConnectionException e) {
            LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
        }

        return userOptional;
    }

    @Override
    public Long save(User userToSave) {
        Long savedUserId = null;

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UserQuery.INSERT_INTO_USERS.getQuery(), Statement.RETURN_GENERATED_KEYS
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
        } catch (SQLException  e) {
            LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
        } catch (ConnectionException e) {
            LOGGER.error("connection FAILED");
        }

        return savedUserId;
    }
}
