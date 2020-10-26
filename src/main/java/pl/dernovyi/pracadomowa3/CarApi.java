package pl.dernovyi.pracadomowa3;



import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class CarApi {
    private List<Car> cars;

    public CarApi() {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Audi" , "A1", "black"));
        cars.add(new Car(2L, "Audi" , "A3", "white"));
        cars.add(new Car(3L, "Audi" , "A6", "red"));
        cars.add(new Car(4L, "Audi" , "RS3", "blue"));
        cars.add(new Car(5L, "Audi" , "Q5", "green"));
        cars.add(new Car(6L, "Mazda" , "CX-5", "white"));

    }
    @GetMapping
    private ResponseEntity<List<Car>> getAllCars(){
        for (Car car : cars) {
            Long carId = car.getId();
            Link selfLink = linkTo(CarApi.class).slash(carId).withSelfRel();
            if(!car.hasLinks()){
                car.add(selfLink);
                Link link = linkTo(CarApi.class).withSelfRel();
                car.add(link);
            }
        }
       return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private ResponseEntity<Car> getCarById(@PathVariable int id){
        Link link = linkTo(CarApi.class).slash(id).withSelfRel();
      Optional<Car> findCar = cars.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
      if(findCar.isPresent()){
          if(!findCar.get().hasLinks()){
              findCar.get().add(link);
              Link linkNew = linkTo(CarApi.class).withSelfRel();
              findCar.get().add(linkNew);
          }
          return new ResponseEntity<>(findCar.get() ,HttpStatus.OK );
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/byColor/{color}")
    private ResponseEntity<List<Car>> getCarByColor(@PathVariable String color){
        List<Car> findCar = cars.stream()
                .filter(car -> car.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
        if(!findCar.isEmpty()){
            return new ResponseEntity<>(findCar , HttpStatus.OK );
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

    @PutMapping("/{id}")
    private ResponseEntity<Car> changeCar(@PathVariable Long id, @RequestBody Car newCar){
       Optional<Car> firstFound = cars.stream()
               .filter(car ->car.getId() == id)
               .findFirst();
        if(firstFound.isPresent()){
            cars.remove(firstFound.get());
            cars.add(newCar);
            return new ResponseEntity<>(newCar,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("changeColor/{id}")
    private ResponseEntity<Car> changeCar(@PathVariable Long id, @RequestParam String color){
        Optional<Car> findFirst = cars.stream()
                .filter(car -> car.getId()==id)
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
                .filter(car -> car.getId()==id)
                .findFirst();
        if(findFirst.isPresent()){
            cars.remove(findFirst.get());
            return new ResponseEntity<>(findFirst.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
