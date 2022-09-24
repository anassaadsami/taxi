package se.anas.taxiCompmay.interfaces;

import org.springframework.http.ResponseEntity;
import se.anas.taxiCompmay.resourses.Taxi;
import se.anas.taxiCompmay.resourses.User;

import java.util.List;
import java.util.Optional;

// we make this interface to be easier to hava a look to all methods we have ,
// especially if we have many methods

public interface UserService {

    User addUser(User user);

    User getUser(Long id);
    Optional<User>getUserOptional(Long id);

    List<Taxi> getUserTaxiList(Long id);

    List<User> getAllUsers();
    ResponseEntity<Object> getAllUsersWithResponse();

    void deleteUser(Long id);
    ResponseEntity<Object> deleteTaxiFromUserWithCheck(Long taxiNo , Long user_id);
    String deleteTaxiFromUserWithoutCheck(Long taxiNo , Long user_id);

    User updateUser(Long id, User user);
    Optional<User> updateOptionalUser(Long id, User user);
    User updateUserWithCheck(Long id , User user);   // how make it with Optional
    User updateUserWithBoolean(Long id , User user);

    User addTaxiCarToUser(Long id , Taxi taxi);  // add taxi car to user
     ResponseEntity<Optional<User>> addTaxiCarToUserByCheck(Long user_id, Taxi taxi);


}
