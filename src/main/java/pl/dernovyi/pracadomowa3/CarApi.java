package pl.dernovyi.pracadomowa3;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarApi {
    private List<Car> cars;

    public CarApi() {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Audi" , "A1", "black"));
        cars.add(new Car(2L, "Audi" , "A3", "white"));
        cars.add(new Car(3L, "Audi" , "A6", "red"));
    }
    @GetMapping
    private ResponseEntity<Resources<Car>> getAllCars(){
       cars.forEach(car -> car.add(linkTo(CarApi.class).slash(car.geId()).withSelfRel()));
       Link link = linkTo(CarApi.class).withSelfRel();
       Resources<Car> carResources = new Resources<>(cars, link);
       return new ResponseEntity<>(carResources, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private ResponseEntity<Resource<Car>> getCarById(@PathVariable int id){
        Link link = linkTo(CarApi.class).slash(id).withSelfRel();
      Optional<Car> findCar = cars.stream()
                .filter(car -> car.geId() == id)
                .findFirst();
      if(findCar.isPresent()){
          Resource<Car> carResource = new Resource<>(findCar.get(), link);
          return new ResponseEntity<>(carResource ,HttpStatus.OK );
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/byColor/{color}")
    private ResponseEntity<Resources<Car>> getCarByColor(@PathVariable String color){
        List<Car> findCar = cars.stream()
                .filter(car -> car.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
        if(!findCar.isEmpty()){
            findCar.forEach(car -> car.add(linkTo(CarApi.class).slash(car.geId()).withSelfRel()));
            Link link = linkTo(CarApi.class).withSelfRel();
            Resources<Car> carResources = new Resources<>(findCar, link);
            return new ResponseEntity<>(carResources ,HttpStatus.OK );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<Car> addCar(@RequestBody Car newCar){
        boolean check = cars.add(newCar);
        if(check){
            return new ResponseEntity<>(newCar,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    private ResponseEntity<Car> changeCar(@RequestBody Car newCar){
       Optional<Car> firstFound = cars.stream()
               .filter(car ->car.getId()==newCar.getId() )
               .findFirst();
        if(firstFound.isPresent()){
            cars.remove(firstFound.get());
            cars.add(newCar);
            return new ResponseEntity<>(newCar,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Car> changeCar(@PathVariable int id, @RequestParam String color){
        Optional<Car> findFirst = cars.stream()
                .filter(car -> car.geId()==id)
                .findFirst();
        if(findFirst.isPresent()){
            cars.remove(findFirst.get());
            findFirst.get().setColor(color);
            cars.add(findFirst.get());
            return new  ResponseEntity<>(findFirst.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Car> removeCar(@PathVariable int id){
        Optional<Car> findFirst = cars.stream()
                .filter(car -> car.geId()==id)
                .findFirst();
        if(findFirst.isPresent()){
            cars.remove(findFirst.get());
            return new ResponseEntity<>(findFirst.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
