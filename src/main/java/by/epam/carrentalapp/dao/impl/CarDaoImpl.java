package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.CarQuery;
import by.epam.carrentalapp.bean.entity.Car;
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

    public List<Car> findAll() {
        LOGGER.info("CarDaoImpl started getting cars");
        List<Car> allCars = new ArrayList<>();
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet carsResultSet = statement.executeQuery(CarQuery.SELECT_ALL_FROM_CARS.getQuery())) {

            while (carsResultSet.next()){
                Long carId = carsResultSet.getLong(CAR_ID_COLUMN_NAME);
                String model = carsResultSet.getString(MODEL_COLUMN_NAME);
                String number = carsResultSet.getString(NUMBER_COLUMN_NAME);
                Double hourlyCost = carsResultSet.getDouble(HOURLY_COST_COLUMN_NAME);

                Car newCar = new Car(carId, model, number, hourlyCost);
                allCars.add(newCar);
                LOGGER.info("CarDaoImpl started getting cars ->" + newCar.toString());
            }
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("CarDao: cannot extract car from ResultSet.");
        }

        return allCars;
    }

    @Override
    public Optional<Car> findById(Long carIdToFind) {
        Optional<Car> carOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(CarQuery.SELECT_ALL_FROM_CARS_WHERE_CAR_ID_EQUALS.getQuery())) {

            preparedStatement.setLong(1, carIdToFind);
            ResultSet carResultSet = preparedStatement.executeQuery();

            if (carResultSet.next()) {
                Long carId = carResultSet.getLong(CAR_ID_COLUMN_NAME);
                String model = carResultSet.getString(MODEL_COLUMN_NAME);
                String number = carResultSet.getString(NUMBER_COLUMN_NAME);
                Double hourlyCost = carResultSet.getDouble(HOURLY_COST_COLUMN_NAME);

                carOptional = Optional.of(new Car(carId, model, number, hourlyCost));
            }

        } catch (SQLException | ConnectionException e) {
            LOGGER.error("CarDaoImpl: cannot extract car from ResultSet.");
        }

        return carOptional;
    }

    @Override
    public Optional<Car> findByModel(String carModelToFind) {
        Optional<Car> carOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(CarQuery.SELECT_ALL_FROM_CARS_WHERE_MODEL_EQUALS.getQuery())) {

            preparedStatement.setString(1, carModelToFind);
            ResultSet carResultSet = preparedStatement.executeQuery();

            if (carResultSet.next()) {
                Long carId = carResultSet.getLong(CAR_ID_COLUMN_NAME);
                String model = carResultSet.getString(MODEL_COLUMN_NAME);
                String number = carResultSet.getString(NUMBER_COLUMN_NAME);
                Double hourlyCost = carResultSet.getDouble(HOURLY_COST_COLUMN_NAME);

                Car carFound = new Car(carId, model, number, hourlyCost);
                carOptional = Optional.of(carFound);
            }

        } catch (SQLException | ConnectionException e) {
            LOGGER.error("CarDaoImpl: cannot extract car from ResultSet.");
        }

        return carOptional;
    }
}