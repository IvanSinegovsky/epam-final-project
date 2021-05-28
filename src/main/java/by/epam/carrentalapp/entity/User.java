package by.epam.carrentalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String lastname;
}
