package by.epam.carrentalapp.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN");

    private final String name;
}
