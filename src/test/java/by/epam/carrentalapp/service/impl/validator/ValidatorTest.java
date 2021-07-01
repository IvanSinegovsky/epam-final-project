package by.epam.carrentalapp.service.impl.validator;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.bean.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validateOrderRequest() {
        //CASE 1: less than 1 hour between ride start and end time
        boolean isValid = Validator.validateOrderRequest(new OrderRequest(
                LocalDateTime.now(),
                LocalDateTime.now(),
                0l,
                0l,
                true,
                true,
                0l
                ));

        Assertions.assertFalse(isValid);

        //CASE 2: more than 1 hour between ride start and end time
        isValid = Validator.validateOrderRequest(new OrderRequest(
                LocalDateTime.of(2021, 1, 2, 10, 1),
                LocalDateTime.of(2021, 1, 3, 10, 1),
                0l,
                0l,
                true,
                true,
                0l
        ));
        Assertions.assertTrue(isValid);
    }

    @Test
    void validateUser() {
        //CASE 1: invalid credentials
        boolean isValid = Validator.validateUser(new User(
                "",
                null,
                "invalid@email,com",
                "less8ch"
        ));

        Assertions.assertFalse(isValid);

        //CASE 2: valid credentials
        isValid = Validator.validateUser(new User(
                "Name",
                "Lastname",
                "valid@email.com",
                "morethan8char"
        ));

        Assertions.assertTrue(isValid);
    }

    @Test
    void validateCar() {
        //CASE 1: invalid car data
        boolean isValid = Validator.validateCar(new Car("", "12345MK-7", 10.0, "urlAlwaysValid"));

        Assertions.assertFalse(isValid);

        //CASE 2: valid car data
        isValid = Validator.validateCar(new Car("Valid Car", "1235MK-7", 10.0, "urlAlwaysValid"));

        Assertions.assertTrue(isValid);
    }

    @Test
    void validatePromoCode() {
        //CASE 1: incorrect discount range
        boolean isValid = Validator.validatePromoCode(new PromoCode("PROMO", 101, true));

        Assertions.assertFalse(isValid);

        //CASE 2: correct discount range
        isValid = Validator.validatePromoCode(new PromoCode("PROMO", 10, true));

        Assertions.assertTrue(isValid);
    }

    @Test
    void validateCustomerUserDetails() {
        //CASE 1: invalid credentials
        boolean isValid = Validator.validateCustomerUserDetails(new CustomerUserDetails("123123123", 50));

        Assertions.assertFalse(isValid);

        //CASE 2: valid credentials
        isValid = Validator.validateCustomerUserDetails(new CustomerUserDetails("1234567A123PB1", 50));

        Assertions.assertTrue(isValid);
    }
}