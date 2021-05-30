package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.query.UserQuery;
import by.epam.carrentalapp.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//todo close all preparedResultSet
public class UserDaoImpl implements UserDao {
    private final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();

        try(Statement statement = ConnectionPool.getInstance().getConnection().createStatement();
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

        try(PreparedStatement preparedStatement = ConnectionPool.getInstance().getConnection()
                .prepareStatement(UserQuery.SELECT_ALL_FROM_USERS_WHERE_EMAIL_EQUALS.getQuery())) {
            preparedStatement.setString(1, emailToFind);

            ResultSet userResultSet = preparedStatement.executeQuery();

            Long userId = userResultSet.getLong(USER_ID_COLUMN_NAME);
            String email = userResultSet.getString(EMAIL_COLUMN_NAME);
            String password = userResultSet.getString(PASSWORD_COLUMN_NAME);
            String name = userResultSet.getString(NAME_COLUMN_NAME);
            String lastname = userResultSet.getString(LASTNAME_COLUMN_NAME);

            userOptional = Optional.of(new User(userId, email, password, name, lastname));
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
        }

        return userOptional;
    }

    @Override
    public Long save(User userToSave) {
        Long savedUserId = null;

        try(PreparedStatement preparedStatement = ConnectionPool.getInstance().getConnection()
                .prepareStatement(UserQuery.INSERT_INTO_USERS.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, userToSave.getEmail());
            preparedStatement.setString(2, userToSave.getPassword());
            preparedStatement.setString(3, userToSave.getName());
            preparedStatement.setString(4, userToSave.getLastname());

            ResultSet userResultSet = preparedStatement.executeQuery();

            if (userResultSet != null && userResultSet.next()) {
                savedUserId = userResultSet.getLong(USER_ID_COLUMN_NAME);
            }
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
        }

        return savedUserId;
    }
}
