package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.Role;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.RoleDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoleDaoImpl implements RoleDao {
    private final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);

    private final String SELECT_ALL_FROM_ROLES_WHERE_ROLE_ID_EQUALS = "SELECT * FROM roles WHERE role_id = ?;";
    private final String SELECT_ALL_FROM_ROLES_WHERE_ROLE_TITLE_EQUALS = "SELECT * FROM roles WHERE role_title = ?;";

    @Override
    public Optional<Role> findByRoleId(Long roleIdToFind) {
        Optional<Role> roleOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(SELECT_ALL_FROM_ROLES_WHERE_ROLE_ID_EQUALS)) {
            preparedStatement.setLong(1, roleIdToFind);

            ResultSet roleResultSet = preparedStatement.executeQuery();
            while (roleResultSet.next()) {
                roleOptional = roleResultSetToRole(roleResultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("RoleDaoImpl findByRoleId(...): cannot extract role from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("RoleDaoImpl findByRoleId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return roleOptional;
    }

    @Override
    public Optional<Role> findByTitle(String titleToFind) {
        Optional<Role> roleOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_ROLES_WHERE_ROLE_TITLE_EQUALS)) {
            preparedStatement.setString(1, titleToFind);

            ResultSet roleResultSet = preparedStatement.executeQuery();
            while (roleResultSet.next()) {
                roleOptional = roleResultSetToRole(roleResultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("RoleDaoImpl findByRoleId(...): cannot extract role from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("RoleDaoImpl findByRoleId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return roleOptional;
    }

    private Optional<Role> roleResultSetToRole(ResultSet roleResultSet) throws SQLException {
        Long roleId = roleResultSet.getLong(ROLE_ID_COLUMN_NAME);
        String roleTitle = roleResultSet.getString(ROLE_TITLE_COLUMN_NAME);

        return Optional.of(new Role(roleId, roleTitle));
    }
}
