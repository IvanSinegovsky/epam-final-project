package by.epam.carrentalapp.entity.user;

import by.epam.carrentalapp.entity.CustomerUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User implements Serializable {
    private CustomerUserDetails customerUserDetails;
}
