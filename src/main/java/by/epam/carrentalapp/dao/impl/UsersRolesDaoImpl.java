package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.UsersRolesDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.UsersRolesQuery;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRolesDaoImpl implements UsersRolesDao {
    private final Logger LOGGER = Logger.getLogger(UsersRolesDaoImpl.class);

    @Override
    public void save(Long userId, Long roleId) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                UsersRolesQuery.INSERT_INTO_USERS_ROLES.getQuery()
             )) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, roleId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("UsersRolesDaoImpl save(...): cannot insert userId, roleId record");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("UsersRolesDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userIdToFind) {
        List<Long> userRoleIds = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UsersRolesQuery
                        .SELECT_ALL_FROM_USERS_ROLES_WHERE_USER_ID_EQUALS.getQuery())) {

            preparedStatement.setLong(1, userIdToFind);

            ResultSet userResultSet = preparedStatement.executeQuery();
            while (userResultSet.next()) {
                Long roleId = userResultSet.getLong(ROLE_ID_COLUMN_NAME);
                userRoleIds.add(roleId);
            }
        } catch (SQLException e) {
            LOGGER.error("UsersRolesDaoImpl findRoleIdsByUserId(...): cannot extract roleId from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("UsersRolesDaoImpl findRoleIdsByUserId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return userRoleIds;
    }
}
