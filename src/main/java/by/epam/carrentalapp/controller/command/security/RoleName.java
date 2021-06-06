package by.epam.carrentalapp.controller.command.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    ALL("isAll"),
    USER("isUser"),
    ADMIN("isAdmin");

    private final String sessionAttributeName;
}
