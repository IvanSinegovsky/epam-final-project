package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.connection.ResultSetProvider;
import by.epam.carrentalapp.dao.impl.query.UserQuery;
import by.epam.carrentalapp.entity.Car;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {
    private final Logger LOGGER = Logger.getLogger(CarDaoImpl.class);


    public List<Car> findAll() {
        List<Car> allCars = new ArrayList<>();
        Optional<ResultSet> carsOptional = ResultSetProvider.executeQuery(UserQuery.SELECT_ALL_FROM_USERS.getQuery());

        if (carsOptional.isPresent()) {
            ResultSet cars = carsOptional.get();

            try {
                while (cars.next()){
                    Long carId = cars.getLong(CAR_ID_COLUMN_NAME);
                    String model = cars.getString(MODEL_COLUMN_NAME);
                    String number = cars.getString(NUMBER_COLUMN_NAME);
                    Double hourlyCost = cars.getDouble(HOURLY_COST_COLUMN_NAME);

                    allCars.add(new Car(carId, model, number, hourlyCost));
                }
            } catch (SQLException e) {
                LOGGER.error("CarDao: cannot extract car from ResultSet.");
            }
        }

        return allCars;
    }
}
