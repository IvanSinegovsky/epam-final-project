package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.DaoException;
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

public class CarDaoImpl implements CarDao {
    private final Logger LOGGER = Logger.getLogger(CarDaoImpl.class);

    private final String SELECT_ALL_FROM_CARS = "SELECT * FROM cars;";
    private final String SELECT_ALL_FROM_CARS_WHERE_CAR_ID_EQUALS = "SELECT * FROM cars WHERE car_id = ?;";
    private final String SELECT_ALL_FROM_CARS_WHERE_MODEL_EQUALS = "SELECT * FROM cars WHERE model = ?;";
    private final String INSERT_INTO_CARS = "INSERT INTO cars(model, number, hourly_cost, asset_url) VALUES (?,?,?,?);";
    private final String SELECT_ALL_FROM_CARS_LIMIT_EQUALS_OFFSET_EQUALS = "SELECT * FROM cars LIMIT ? OFFSET ?";
    private final String SELECT_COUNT_FROM_CARS = "SELECT COUNT(*) FROM cars";

    @Override
    public Optional<Integer> findCount()  {
        Optional<Integer> carsCountOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet carsResultSet = statement.executeQuery(SELECT_COUNT_FROM_CARS)) {

            if (carsResultSet.next()){
                carsCountOptional = Optional.of(carsResultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error("CarDao findCount(...): cannot find records count");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CarDao findCount(...): connection pool crashed");
            throw new DaoException(e);
        }

        return carsCountOptional;
    }

    public List<Car> findAll() {
        List<Car> allCars = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet carsResultSet = statement.executeQuery(SELECT_ALL_FROM_CARS)) {

            while (carsResultSet.next()){
                allCars.add(extractCarFromResultSet(carsResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("CarDao findAll(...): cannot extract car from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CarDao findAll(...): connection pool crashed");
            throw new DaoException(e);
        }

        return allCars;
    }

    @Override
    public Optional<Car> findById(Long carIdToFind) {
        Optional<Car> carOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_CARS_WHERE_CAR_ID_EQUALS)) {

            preparedStatement.setLong(1, carIdToFind);
            ResultSet carResultSet = preparedStatement.executeQuery();

            if (carResultSet.next()) {
                carOptional = Optional.of(extractCarFromResultSet(carResultSet));
            }

        } catch (SQLException e) {
            LOGGER.error("CarDao findById(...): cannot extract car from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CarDao findById(...): connection pool crashed");
            throw new DaoException(e);
        }

        return carOptional;
    }

    @Override
    public Optional<Car> findByModel(String carModelToFind) {
        Optional<Car> carOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_CARS_WHERE_MODEL_EQUALS)) {

            preparedStatement.setString(1, carModelToFind);
            ResultSet carResultSet = preparedStatement.executeQuery();

            if (carResultSet.next()) {
                carOptional = Optional.of(extractCarFromResultSet(carResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("CarDao findByModel(...): cannot extract car from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CarDao findByModel(...): connection pool crashed");
            throw new DaoException(e);
        }

        return carOptional;
    }

    @Override
    public List<Car> findAllByLimitAndOffset(int limit, int offset) {
        List<Car> carsByRange = new ArrayList<>(limit);

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_CARS_LIMIT_EQUALS_OFFSET_EQUALS)) {

            preparedStatement.setLong(1, limit);
            preparedStatement.setLong(2, offset);
            ResultSet carResultSet = preparedStatement.executeQuery();

            while (carResultSet.next()) {
                carsByRange.add(extractCarFromResultSet(carResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("CarDao findAllByLimitAndOffset(...): cannot extract car from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CarDao findAllByLimitAndOffset(...): connection pool crashed");
            throw new DaoException(e);
        }

        return carsByRange;
    }

    private Car extractCarFromResultSet(ResultSet carResultSet) throws SQLException {
        Long carId = carResultSet.getLong(CAR_ID_COLUMN_NAME);
        String model = carResultSet.getString(MODEL_COLUMN_NAME);
        String number = carResultSet.getString(NUMBER_COLUMN_NAME);
        Double hourlyCost = carResultSet.getDouble(HOURLY_COST_COLUMN_NAME);
        String assetURL = carResultSet.getString(ASSET_URL_COLUMN_NAME);

        return new Car(carId, model, number, hourlyCost, assetURL);
    }

    @Override
    public Optional<Long> save(Car car) {
        Optional<Long> savedCarId = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_CARS,
                    Statement.RETURN_GENERATED_KEYS
            )) {

            ResultSet carResultSet = insertAndGetGeneratedKey(preparedStatement, car);

            if (carResultSet != null && carResultSet.next()) {
                savedCarId = Optional.of(carResultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("CarDaoImpl save(...): cannot insert car record");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CarDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }

        return savedCarId;
    }

    private ResultSet insertAndGetGeneratedKey(PreparedStatement preparedStatement, Car car)
            throws SQLException {

        preparedStatement.setString(1, car.getModel());
        preparedStatement.setString(2, car.getNumber());
        preparedStatement.setDouble(3, car.getHourlyCost());
        preparedStatement.setString(4, car.getAssetURL());

        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }
}