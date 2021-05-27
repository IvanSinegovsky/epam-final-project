package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.dao.connection.DaoProvider;
import by.epam.carrentalapp.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CarDao {
    private final DaoProvider daoProvider = new DaoProvider();

    public List<Car> getAll() throws SQLException, ClassNotFoundException {
        ResultSet cars = daoProvider.executeQuery("SELECT * FROM cars");

        while (cars.next()){
            String name = cars.getString("name");
            //todo fill fields
        }

        //todo change
        return null;
    }
}
