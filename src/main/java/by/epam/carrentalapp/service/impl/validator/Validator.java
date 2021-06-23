package by.epam.carrentalapp.service.impl.validator;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.bean.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String LOCAL_DATE_TIME_PATTERN = "dd-MM-yyyy";

    private static final Pattern EMAIL_PATTERN_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern CAR_NUMBER_PATTERN_REGEX
            = Pattern.compile("^([0-9]{4}[ABEKHOPCTYXIM]{2}-[0-7])?$");
    private static final Pattern PASSPORT_NUMBER_PATTERN_REGEX
            = Pattern.compile("^(\\d{7}[ABCKEMH]\\d{3}[PBAI]{2}\\d)$");
    private static final Pattern PASSWORD_PATTERN_REGEX
            = Pattern.compile("\\S{8,30}");

    public static boolean validateOrderRequest(OrderRequest orderRequest) {
        LocalDateTime expectedStartTime = orderRequest.getExpectedStartTime();
        LocalDateTime expectedEndTime = orderRequest.getExpectedEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);

        boolean a1 = expectedEndTime.format(formatter).equals(expectedStartTime.format(formatter));

        int hours1 = expectedEndTime.getHour() - expectedStartTime.getHour();

        boolean a2 = hours1 < 1;

        if (a1 && a2) {
            return false;
        }

        return true;
    }

    public static boolean validateUser(User user) {
        return  (validateName(user.getName())
                && validateName(user.getLastname())
                && validateEmail(user.getEmail())
                && validatePassword(user.getPassword()));
    }

    public static boolean validateCar(Car car) {
        return (validateName(car.getModel())
                && validateCarNumber(car.getNumber()));
    }

    public static boolean validatePromoCode(PromoCode promoCode) {
        return (validateName(promoCode.getPromoCode())
                && (promoCode.getDiscount() >= 0 && promoCode.getDiscount() < 100));
    }

    public static boolean validateCustomerUserDetails(CustomerUserDetails customerUserDetails) {
        return validatePassportNumber(customerUserDetails.getPassportNumber());
    }


    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN_REGEX.matcher(email);

        return matcher.matches();
    }

    public static boolean validateName(String name) {
        return !(name == null || "".equals(name));
    }

    public static boolean validatePassportNumber(String passportNumber) {
        Matcher matcher = PASSPORT_NUMBER_PATTERN_REGEX.matcher(passportNumber);

        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Matcher matcher = PASSWORD_PATTERN_REGEX.matcher(password);

        return matcher.matches();
    }

    public static boolean validateCarNumber(String carNumber) {
        Matcher matcher = CAR_NUMBER_PATTERN_REGEX.matcher(carNumber);

        return matcher.matches();
    }
}
