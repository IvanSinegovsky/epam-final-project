package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.PromoCodeDao;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.PromoCodeService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.validator.Validator;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {
    private final Logger LOGGER = Logger.getLogger(PromoCodeServiceImpl.class);

    @Autowired
    private PromoCodeDao promoCodeDao;

    @Override
    public void addPromoCode(PromoCode promoCode) {
        try {
            if (!Validator.validatePromoCode(promoCode)) {
                throw new ServiceException("Invalid promo code data");
            }

            Optional<Long> savedPromoCodeIdOptional = promoCodeDao.save(promoCode);

            if (savedPromoCodeIdOptional.isEmpty()) {
                throw new ServiceException("PromoCodeServiceImpl addPromoCode(...): DAO cannot return generated key");
            }
        } catch (DaoException e) {
            LOGGER.error("PromoCodeServiceImpl addPromoCode(...): DAO cannot return records");
            throw new ServiceException(e);
        }
    }

    @Override
    public void disablePromoCodes(List<String> promoCodes) {
        try {
            promoCodeDao.setValuesNonActiveByPromoCodes(promoCodes);
        } catch (DaoException e) {
            LOGGER.error("PromoCodeServiceImpl disablePromoCodes(...): DAO cannot return records");
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PromoCode> getAllPromoCodes() {
        List<PromoCode> promoCodes;

        try {
            promoCodes = promoCodeDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("PromoCodeServiceImpl disablePromoCodes(...): DAO cannot return records");
            throw new ServiceException(e);
        }

        return promoCodes;
    }
}
