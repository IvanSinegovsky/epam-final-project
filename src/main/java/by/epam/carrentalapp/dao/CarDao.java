package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.controller.Controller;
import by.epam.carrentalapp.dao.connection.ResultSetProvider;
import by.epam.carrentalapp.entity.Car;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao {
    private final ResultSetProvider resultSetProvider = new ResultSetProvider();
    private final Logger LOGGER = Logger.getLogger(CarDao.class);

    public List<Car> getAll() {
        List<Car> allCars = new ArrayList<>();
        Optional<ResultSet> carsOptional = resultSetProvider.executeQuery("SELECT * FROM cars");

        if (carsOptional.isPresent()) {
            ResultSet cars = carsOptional.get();

            try {
                while (cars.next()){
                    Long carId = cars.getLong("car_id");
                    String model = cars.getString("model");
                    String number = cars.getString("number");
                    Double hourlyCost = cars.getDouble("hourly_cost");

                    allCars.add(new Car(carId, model, number, hourlyCost));
                }
            } catch (SQLException e) {
                LOGGER.error("CarDao: cannot extract car from ResultSet.");
            }
        }

        return allCars;
    }
}
