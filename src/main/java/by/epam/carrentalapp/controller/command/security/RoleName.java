package by.epam.carrentalapp.controller.command.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Separates users for giving users with different roles different permissions
 */
@AllArgsConstructor
@Getter
public enum RoleName {
    ALL("isAll"),
    CUSTOMER("isCustomer"),
    ADMIN("isAdmin");

    /**
     * Value for using like session key to check specific user role(s)
     */
    private final String sessionAttributeName;
}
