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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Optional<Long> save(PromoCode promoCodeToSave) {
        Optional<Long> savedPromoCodeId = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    PromoCodeQuery.INSERT_INTO_PROMO_CODES.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            preparedStatement.setString(1, promoCodeToSave.getPromoCode());
            preparedStatement.setInt(2, promoCodeToSave.getDiscount());
            preparedStatement.setBoolean(3, promoCodeToSave.getIsActive());

            preparedStatement.executeUpdate();
            ResultSet promoCodeResultSet = preparedStatement.getGeneratedKeys();

            if (promoCodeResultSet != null && promoCodeResultSet.next()) {
                savedPromoCodeId = Optional.of(promoCodeResultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("PromoCodeDaoImpl save(...): cannot insert PromoCode to db");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("PromoCodeDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }

        return savedPromoCodeId;
    }

    @Override
    public void setValuesNonActiveByPromoCodes(List<String> promoCodes)  {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    PromoCodeQuery.UPDATE_PROMO_CODES_SET_IS_ACTIVE_FALSE_WHERE_PROMO_CODE.getQuery()
            )) {

            for (String promoCode : promoCodes) {
                preparedStatement.setString(1 , promoCode);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("PromoCodeDaoImpl setValuesNonActiveByPromoCodes(...): cannot execute update");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("PromoCodeDaoImpl setValuesNonActiveByPromoCodes(...): connection pool crashed");
            throw new DaoException(e);
        }
    }

    @Override
    public List<PromoCode> findAll() {
        List<PromoCode> allPromoCodes = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet promoCodesResultSet = statement.executeQuery(PromoCodeQuery.SELECT_ALL_FROM_PROMO_CODES.getQuery())) {

            while (promoCodesResultSet.next()){
                Long promoCodeId = promoCodesResultSet.getLong(PROMO_CODE_ID_COLUMN_NAME);
                String promoCode = promoCodesResultSet.getString(PROMO_CODE_COLUMN_NAME);
                Integer discount = promoCodesResultSet.getInt(DISCOUNT_COLUMN_NAME);
                Boolean isActive = promoCodesResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);

                allPromoCodes.add(new PromoCode(promoCodeId, promoCode, discount, isActive));
            }
        } catch (SQLException e) {
            LOGGER.error("PromoCodeDaoImpl findAll(...): cannot extract PromoCode from resultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("PromoCodeDaoImpl findAll(...): connection pool crashed");
            throw new DaoException(e);
        }

        return allPromoCodes;
    }
}
