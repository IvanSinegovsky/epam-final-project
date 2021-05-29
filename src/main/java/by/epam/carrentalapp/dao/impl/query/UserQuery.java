package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserQuery {
    SELECT_ALL_FROM_USERS("SELECT * FROM users"),
    SELECT_ALL_FROM_USERS_WHERE_EMAIL_EQUALS("SELECT * FROM users WHERE email=");

    private final String query;
}
