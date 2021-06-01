package by.epam.carrentalapp.controller.command;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequestParameterName {
    NAME("name"),
    LASTNAME("lastname"),
    EMAIL("email"),
    PASSWORD("password"),
    PASSPORT_NUMBER("passport_number");

    private final String name;
}
