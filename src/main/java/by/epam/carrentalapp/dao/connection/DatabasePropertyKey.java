package by.epam.carrentalapp.dao.connection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DatabasePropertyKey {
    URL("db.url"),
    USER("db.user"),
    PASSWORD("db.password"),
    POOL_SIZE("db.poolSize"),
    CONNECTION_TIMEOUT("db.connectionTimeout");

    private final String key;
}
