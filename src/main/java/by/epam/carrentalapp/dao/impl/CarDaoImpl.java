package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.query.CarQuery;
import by.epam.carrentalapp.entity.Car;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private final Logger LOGGER = Logger.getLogger(CarDaoImpl.class);

    public List<Car> findAll() {
        List<Car> allCars = new ArrayList<>();
        try(Statement statement = ConnectionPool.getConnection().createStatement();
            ResultSet carsResultSet = statement.executeQuery(CarQuery.SELECT_ALL_FROM_CARS.getQuery())) {

            while (carsResultSet.next()){
                Long carId = carsResultSet.getLong(CAR_ID_COLUMN_NAME);
                String model = carsResultSet.getString(MODEL_COLUMN_NAME);
                String number = carsResultSet.getString(NUMBER_COLUMN_NAME);
                Double hourlyCost = carsResultSet.getDouble(HOURLY_COST_COLUMN_NAME);

                allCars.add(new Car(carId, model, number, hourlyCost));
            }
        } catch (SQLException e) {
            LOGGER.error("CarDao: cannot extract car from ResultSet.");
        }

        return allCars;
    }
}
