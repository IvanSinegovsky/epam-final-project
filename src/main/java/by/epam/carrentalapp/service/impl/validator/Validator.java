package by.epam.carrentalapp.service.impl.validator;

import by.epam.carrentalapp.entity.CustomerUserDetails;
import by.epam.carrentalapp.entity.OrderRequest;
import by.epam.carrentalapp.entity.user.Customer;
import by.epam.carrentalapp.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String LOCAL_DATE_TIME_PATTERN = "dd-MM-yyyy";
    private static final Pattern EMAIL_PATTERN_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateOrderRequest(OrderRequest orderRequest) {
        LocalDateTime expectedStartTime = orderRequest.getExpectedStartTime();
        LocalDateTime expectedEndTime = orderRequest.getExpectedEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);

        if ((expectedEndTime.format(formatter).equals(expectedStartTime.format(formatter))
                && expectedEndTime.getHour() - expectedStartTime.getHour() < 1)
                || ChronoUnit.HOURS.between(expectedEndTime, expectedStartTime) < 1) {
            return false;
        }

        return true;
    }

    public static boolean validateUser(User user) {
        return  (validateName(user.getName())
                && validateLastname(user.getLastname())
                && validateEmail(user.getEmail())
                && validatePassword(user.getPassword()));
    }

    public static boolean validateCustomerUserDetails(CustomerUserDetails customerUserDetails) {
        return validatePassportNumber(customerUserDetails.getPassportNumber());
    }


    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN_REGEX.matcher(email);

        return matcher.matches();
    }

    public static boolean validateName(String name) {
        return true;
    }

    public static boolean validateLastname(String lastname) {
        return true;
    }

    public static boolean validatePassportNumber(String passportNumber) {
        return true;
    }

    public static boolean validatePassword(String password) {
        return true;
    }

    public static boolean validateCarNumber(String carNumber) {
        return true;
    }
}
