package by.epam.carrentalapp.entity.customer;

import by.epam.carrentalapp.entity.CustomerUserDetails;
import by.epam.carrentalapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    private CustomerUserDetails customerUserDetails;
}
