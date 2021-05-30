package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.RoleDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.query.RoleQuery;
import by.epam.carrentalapp.entity.Role;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoleDaoImpl implements RoleDao {
    private final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);

    @Override
    public Optional<Role> findByRoleId(Long roleIdToFind) {
        Optional<Role> roleOptional = Optional.empty();

        try(PreparedStatement preparedStatement = ConnectionPool.getInstance().getConnection()
                .prepareStatement(RoleQuery.SELECT_ALL_FROM_ROLES_WHERE_ROLE_ID_EQUALS.getQuery())) {
            preparedStatement.setLong(1, roleIdToFind);

            ResultSet roleResultSet = preparedStatement.executeQuery();
            roleOptional = roleResultSetToRole(roleResultSet);
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("RoleDaoImpl findByRoleId(): cannot extract role from ResultSet.");
        }

        return roleOptional;
    }

    @Override
    public Optional<Role> findByTitle(String titleToFind) {
        Optional<Role> roleOptional = Optional.empty();

        try(PreparedStatement preparedStatement = ConnectionPool.getInstance().getConnection()
                .prepareStatement(RoleQuery.SELECT_ALL_FROM_ROLES_WHERE_ROLE_TITLE_EQUALS.getQuery())) {
            preparedStatement.setString(1, titleToFind);

            ResultSet roleResultSet = preparedStatement.executeQuery();
            roleOptional = roleResultSetToRole(roleResultSet);
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("RoleDaoImpl findByTitle(): cannot extract role from ResultSet.");
        }

        return roleOptional;
    }

    private Optional<Role> roleResultSetToRole(ResultSet roleResultSet) throws SQLException {
        Long roleId = roleResultSet.getLong(ROLE_ID_COLUMN_NAME);
        String roleTitle = roleResultSet.getString(ROLE_TITLE_COLUMN_NAME);

        return Optional.of(new Role(roleId, roleTitle));
    }
}
