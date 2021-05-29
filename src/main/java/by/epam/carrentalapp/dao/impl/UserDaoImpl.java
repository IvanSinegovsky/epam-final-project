package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dao.connection.ResultSetProvider;
import by.epam.carrentalapp.dao.impl.query.QuerySupporter;
import by.epam.carrentalapp.dao.impl.query.UserQuery;
import by.epam.carrentalapp.entity.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        Optional<ResultSet> usersOptional = ResultSetProvider.executeQuery(UserQuery.SELECT_ALL_FROM_USERS.getQuery());

        if (usersOptional.isPresent()) {
            ResultSet users = usersOptional.get();

            try {
                while (users.next()){
                    Long userId = users.getLong(USER_ID_COLUMN_NAME);
                    String email = users.getString(EMAIL_COLUMN_NAME);
                    String password = users.getString(PASSWORD_COLUMN_NAME);
                    String name = users.getString(NAME_COLUMN_NAME);
                    String lastname = users.getString(LASTNAME_COLUMN_NAME);

                    allUsers.add(new User(userId, email, password, name, lastname));
                }
            } catch (SQLException e) {
                LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
            }
        }

        return allUsers;
    }

    @Override
    public Optional<User> findByEmail(String emailToFind) {
        Optional<User> userOptional = Optional.empty();
        Optional<ResultSet> userResultSetOptional = ResultSetProvider.executeQuery(
                UserQuery.SELECT_ALL_FROM_USERS_WHERE_EMAIL_EQUALS.getQuery()
                        + QuerySupporter.wrapBySingleQuotes(emailToFind)
        );

        if (userResultSetOptional.isPresent()) {
            ResultSet user = userResultSetOptional.get();

            try {
                Long userId = user.getLong(USER_ID_COLUMN_NAME);
                String email = user.getString(EMAIL_COLUMN_NAME);
                String password = user.getString(PASSWORD_COLUMN_NAME);
                String name = user.getString(NAME_COLUMN_NAME);
                String lastname = user.getString(LASTNAME_COLUMN_NAME);

                userOptional = Optional.of(new User(userId, email, password, name, lastname));
            } catch (SQLException e) {
                LOGGER.error("UserDaoImpl: cannot extract user from ResultSet.");
            }
        }

        return userOptional;
    }
}
