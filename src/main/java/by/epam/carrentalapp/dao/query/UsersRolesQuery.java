package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UsersRolesQuery {
    INSERT_INTO_USERS_ROLES("INSERT INTO users_roles (users_user_id, roles_role_id) VALUES (?, ?);"),
    SELECT_ALL_FROM_USERS_ROLES_WHERE_USER_ID_EQUALS("SELECT * FROM users_roles WHERE users_user_id = ?;");

    private final String query;
}
