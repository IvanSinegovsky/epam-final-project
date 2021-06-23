package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.PromoCodeDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
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

    private final String SELECT_ALL_FROM_PROMO_CODES = "SELECT * FROM promo_codes;";
    private final String INSERT_INTO_PROMO_CODES =
            "INSERT INTO promo_codes(promo_code, discount, is_active) VALUES (?,?,?);";
    private final String UPDATE_PROMO_CODES_SET_IS_ACTIVE_FALSE_WHERE_PROMO_CODE =
            "UPDATE promo_codes SET is_active=0 WHERE promo_code=?;";
    private final String SELECT_ALL_FROM_PROMO_CODES_WHERE_PROMO_CODE_EQUALS =
            "SELECT * FROM promo_codes WHERE promo_code = ?;";

    @Override
    public Optional<PromoCode> findByPromoCode(String promoCodeToFind) {
        Optional<PromoCode> promoCodeOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(SELECT_ALL_FROM_PROMO_CODES_WHERE_PROMO_CODE_EQUALS)) {

            preparedStatement.setString(1, promoCodeToFind);
            ResultSet promoCodeResultSet = preparedStatement.executeQuery();

            if (promoCodeResultSet.next()) {
                promoCodeOptional = Optional.of(extractPromoCodeFromResultSet(promoCodeResultSet));
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

    private PromoCode extractPromoCodeFromResultSet(ResultSet promoCodeResultSet) throws SQLException {
        Long promoCodeId = promoCodeResultSet.getLong(PROMO_CODE_ID_COLUMN_NAME);
        String promoCode = promoCodeResultSet.getString(PROMO_CODE_COLUMN_NAME);
        Integer discount = promoCodeResultSet.getInt(DISCOUNT_COLUMN_NAME);
        Boolean isActive = promoCodeResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);

        return new PromoCode(promoCodeId, promoCode, discount, isActive);
    }

    @Override
    public Optional<Long> save(PromoCode promoCode) {
        Optional<Long> savedPromoCodeId = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PROMO_CODES,
                    Statement.RETURN_GENERATED_KEYS
            )) {

            ResultSet promoCodeResultSet = insertAndGetGeneratedKey(preparedStatement, promoCode);

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

    private ResultSet insertAndGetGeneratedKey(PreparedStatement preparedStatement, PromoCode promoCode)
            throws SQLException {
        preparedStatement.setString(1, promoCode.getPromoCode());
        preparedStatement.setInt(2, promoCode.getDiscount());
        preparedStatement.setBoolean(3, promoCode.getIsActive());

        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    @Override
    public void setValuesNonActiveByPromoCodes(List<String> promoCodes)  {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_PROMO_CODES_SET_IS_ACTIVE_FALSE_WHERE_PROMO_CODE)) {

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
            ResultSet promoCodesResultSet = statement.executeQuery(SELECT_ALL_FROM_PROMO_CODES)) {

            while (promoCodesResultSet.next()){
                allPromoCodes.add(extractPromoCodeFromResultSet(promoCodesResultSet));
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
