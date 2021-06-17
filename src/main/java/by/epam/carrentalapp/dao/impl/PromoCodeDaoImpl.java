package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.PromoCodeDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.PromoCodeQuery;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PromoCodeDaoImpl implements PromoCodeDao {
    private final Logger LOGGER = Logger.getLogger(PromoCodeDaoImpl.class);

    @Override
    public Optional<PromoCode> findByPromoCode(String promoCodeToFind) {
        Optional<PromoCode> promoCodeOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(PromoCodeQuery.SELECT_ALL_FROM_PROMO_CODES_WHERE_PROMO_CODE_EQUALS.getQuery())) {

            preparedStatement.setString(1, promoCodeToFind);
            ResultSet promoCodeResultSet = preparedStatement.executeQuery();

            if (promoCodeResultSet.next()) {
                Long promoCodeId = promoCodeResultSet.getLong(PROMO_CODE_ID_COLUMN_NAME);
                String promoCode = promoCodeResultSet.getString(PROMO_CODE_COLUMN_NAME);
                Integer discount = promoCodeResultSet.getInt(DISCOUNT_COLUMN_NAME);
                Boolean isActive = promoCodeResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);

                promoCodeOptional = Optional.of(new PromoCode(promoCodeId, promoCode, discount, isActive));
            }
        } catch (SQLException e) {
            LOGGER.error("PromoCodeDaoImpl findByPromoCode(...): cannot extract promoCode from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("PromoCodeDaoImpl findByPromoCode(...): connection pool crashed");
            throw new DaoException(e);
        }

        return promoCodeOptional;
    }
}
