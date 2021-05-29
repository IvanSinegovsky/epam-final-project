package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleQuery {
    SELECT_ALL_FROM_ROLES_WHERE_ROLE_ID_EQUALS("SELECT * FROM roles WHERE role_id = ?;"),
    SELECT_ALL_FROM_ROLES_WHERE_ROLE_TITLE_EQUALS("SELECT * FROM roles WHERE role_title = ?;");

    private final String query;
}
