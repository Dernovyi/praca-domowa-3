package pl.dernovyi.pracadomowa3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.dernovyi.pracadomowa3.model.Car;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CarServiceImpl implements CarService {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public CarServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveCar(Car newCar) {
        String sql = "INSERT INTO cars VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, newCar.getId(), newCar.getMark(), newCar.getModel() ,newCar.getColor() ,newCar.getYear());
    }

    @Override
    public List<Integer> getYear() {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT year FROM cars ORDER BY year";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        maps.stream().forEach(element -> years.add(
                (Integer) element.get("year")));
        return years;

    }

    @Override
    public List<Car> findAllCars() {
        String sql = "SELECT * FROM cars";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        List<Car> listCars =new ArrayList<>();
        maps.stream().forEach(element -> listCars.add(new Car(
                Long.parseLong(String.valueOf(element.get("car_id"))),
                (String) element.get("mark"),
                (String) element.get("model"),
                (String) element.get("color"),
                Integer.parseInt(String.valueOf(element.get("year")))
        )));
        return listCars;
    }

    @Override
    public void changeCarById(Car newCar) {
        String sql = "UPDATE  cars SET cars.mark=?, cars.model=?, cars.color =?, cars.year = ? WHERE cars.car_id =?";
        jdbcTemplate.update(sql, newCar.getMark(), newCar.getModel(), newCar.getColor(), newCar.getYear(), newCar.getId());
    }

    @Override
    public Car getCarById(int id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Car(
                rs.getLong("car_id"),
                rs.getString("mark"),
                rs.getString("model"),
                rs.getString("color"),
                rs.getInt("year")),
                id);
    }

    @Override
    public void removeCarById(int id) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        jdbcTemplate.update(sql, id);
    }


}
