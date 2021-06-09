package by.epam.carrentalapp.controller.command.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    ALL("isAll"),
    CUSTOMER("isCustomer"),
    ADMIN("isAdmin");

    private final String sessionAttributeName;
}
