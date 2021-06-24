package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.dao.connection.ConnectionException;

import java.sql.SQLException;
import java.util.Optional;

public interface CustomerUserDetailsDao {
    String USER_DETAILS_ID_COLUMN_NAME = "user_details_id";
    String PASSPORT_NUMBER_COLUMN_NAME = "passport_number";
    String RATE_COLUMN_NAME = "rate";
    String USER_ID_COLUMN_NAME = "user_id";

    void save(CustomerUserDetails customerUserDetails);
    Optional<CustomerUserDetails> findById(Long userDetailsIdToFind);
    Optional<CustomerUserDetails> findByUserId(Long userIdToFind);
    void setRateById(double rate, Long userDetailsId);
    void registerCustomer(User userToSave, CustomerUserDetails customerUserDetails, String roleTitleToFind)
            throws ConnectionException, SQLException;
}
