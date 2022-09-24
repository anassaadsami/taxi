package se.anas.taxiCompmay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.anas.taxiCompmay.repositories.UserRepo;
import se.anas.taxiCompmay.resourses.Taxi;
import se.anas.taxiCompmay.resourses.User;
import se.anas.taxiCompmay.service.UserServiceImp;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private UserRepo userRepo;       // this one to can access the abstract method in UserResp interface


    // here we get the whole user object in postman in response created
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<User>(userServiceImp.addUser(user), HttpStatus.CREATED);
    }
    // we can add user without using ResponseEntity and it's work but it's not good, in this case
    // we get response just ok
    @PostMapping("/addUser")
    public User addUser2(@RequestBody User user) {
        return userServiceImp.addUser(user);
    }
    // we can add user object by one or more it's attributes but we must have constructor for
    // this type of object , and that's why we need @RequiredArgsConstructor cause it provide us
    // all options of constructor for this object, but we must use @NotNull above every attribute
    // using to create this specific object

    @PostMapping("/addByFirstName/header")
    public ResponseEntity<User> addByFirstName(@RequestHeader String first_name){
        return new ResponseEntity<User>(userServiceImp.addUser(new User(first_name)), HttpStatus.CREATED);
    }

    // without using Optional type we get( error )in response Json if we access not found id
    @GetMapping("/get/path/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userServiceImp.getUser(id), HttpStatus.OK);
    }

    // this to handle exception if user_id not found
    @GetMapping("/getWithException/path/{id}")
    public ResponseEntity<User> getUserWithException(@PathVariable Long id){
        return new ResponseEntity<User>(userServiceImp.getUserWithThrowException(id), HttpStatus.NOT_FOUND);
    }

    // using Optional type provide (null) in response Json if we access not found id
    @GetMapping("/get/Optional/path/{id}")
    public ResponseEntity<Optional<User>> getUserOptional(@PathVariable Long id) {
        return new ResponseEntity<Optional<User>>(userServiceImp.getUserOptional(id) , HttpStatus.OK); // we NOT add get()
    }

    @GetMapping("/getUserTaxiList/path/{id}")
    public List<Taxi> getUserTaxiList(@PathVariable Long id) {
        return userServiceImp.getUserTaxiList(id);
    }


    @GetMapping("/getAllUsers")
    public List <User> getAllUsers() {
        return userServiceImp.getAllUsers();
    }

    @RequestMapping("/getAllUsersWithResponse")
    public ResponseEntity<Object> getAllUserWithResponse() {
        return userServiceImp.getAllUsersWithResponse();
    }

    @PutMapping("/update/header")
    public ResponseEntity<User> updateUserWithCheck(@RequestHeader Long id, @RequestBody User user) {
        return new ResponseEntity<User>(userServiceImp.updateUserWithCheck(id, user), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<User>(userServiceImp.updateUser(id, user), HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateOptional/{id}")
    public ResponseEntity<Optional<User>> updateOptionalUser(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<Optional<User>>(userServiceImp.updateOptionalUser(id, user), HttpStatus.ACCEPTED);
    }


    // we get 1 in as response if we access not found id
    @PutMapping("/updateByBoolean/header")
    public ResponseEntity<User> updateUserBoolean(@RequestHeader Long id, @RequestBody User user) {
        return new ResponseEntity<User>(userServiceImp.updateUserWithBoolean(id, user), HttpStatus.ACCEPTED);
    }

    // if we put not found id we get error??
    @PutMapping("/updateWithTryCatch/{id}")
    public ResponseEntity<User> updateUserByTry(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<User>(userServiceImp.updateUserWithCheck(id, user), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/param")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        userServiceImp.deleteUser(id);
        return new ResponseEntity<String>(String.format("object no %d is deleted", id), HttpStatus.OK);
    }

    @PutMapping("/deleteTaxiFromUserWithoutCheck/path/{user_id}/{taxiNO}")
    public String deleteTaxiFromUserWithoutCheck(@PathVariable("user_id") Long id , @PathVariable Long taxiNO) {
        return userServiceImp.deleteTaxiFromUserWithoutCheck(id,taxiNO);

    }

    /*
     to delete taxi from a user so we must check many things:
     1- if this user is exist
     2- if this taxi is exist
     3- if this user has this taxi in its taxiList
     */
    @PutMapping("/deleteTaxiFromUserWithCheck/pathUser/{id}/pathTaxi/{taxiNO}")
    public ResponseEntity<Object> deleteTaxiFromUserWithCheck(@PathVariable Long id , @PathVariable Long taxiNO) {
       return userServiceImp.deleteTaxiFromUserWithCheck(id,taxiNO);

    }



    @DeleteMapping("/delete/path/{user_id}")
    public String deleteUserPath(@PathVariable("user_id") Long id) {
        userServiceImp.deleteUser(id);
        return String.format("object no %d is deleted", id);              // ?? the difference
    }

    @PutMapping("/addTaxiToUser/{user_id}")
    public ResponseEntity<User> addTaxiToUser(@PathVariable("user_id") Long id, @RequestBody Taxi taxi) {
        return new ResponseEntity<User>(userServiceImp.addTaxiCarToUser(id, taxi), HttpStatus.OK);
    }
    // this is better with optional to avoid error when id is not exist,
    // and if taxi is not exist in db so it will be added to db first and then added to the user 
    @PutMapping("/addTaxiToUserByCheck/{user_id}")
    public ResponseEntity<Optional<User>>addTaxiToUserByCheck(@PathVariable("user_id") Long id, @RequestBody Taxi taxi) {
       // return new ResponseEntity<Optional<User>>(userServiceImp.addTaxiCarToUserByCheck(id, taxi), HttpStatus.OK);
        return userServiceImp.addTaxiCarToUserByCheck(id,taxi);
    }
/*
    // why it's error --->    Error creating bean with name 'userController'
    @GetMapping("/user/query/{first_name}")
    public List<User> getUsersQuery(@PathVariable String first_name) {
       return userService.findAllByFirst_name(first_name) ;
    }

 */

    }
