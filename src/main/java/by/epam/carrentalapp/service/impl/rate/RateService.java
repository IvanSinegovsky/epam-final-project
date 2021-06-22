package by.epam.carrentalapp.service.impl.rate;

import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.impl.DaoProvider;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;


public class RateService {
    private static final Logger LOGGER = Logger.getLogger(RateService.class);
    private static final CustomerUserDetailsDao customerUserDetailsDao = DaoProvider.getCustomerUserDetailsDao();
    private static final double maximumRate = 100.0;

    public static void changeRateByEvent(RateEvent rateEvent, Long userDetailsId) {
        try {
            CustomerUserDetails customerUserDetails = customerUserDetailsDao.findById(userDetailsId)
                    .orElseThrow(() -> new ServiceException("Cannot find customerUserDetails by its id"));

            double updatedRate = customerUserDetails.getRate() * rateEvent.getCoefficient();

            if (updatedRate > maximumRate) {
                updatedRate = maximumRate;
            }

            customerUserDetailsDao.setRateById(updatedRate, userDetailsId);
        } catch (DaoException e) {
            LOGGER.error("RateService changeRateByEvent(): DAO cannot update values");
            throw new ServiceException(e);
        }
    }
}
