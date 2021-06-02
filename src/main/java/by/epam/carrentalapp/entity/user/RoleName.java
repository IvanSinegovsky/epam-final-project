package by.epam.carrentalapp.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum RoleName implements Serializable {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN");

    private final String name;
}
