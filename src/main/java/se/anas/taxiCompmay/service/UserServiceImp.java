package se.anas.taxiCompmay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import se.anas.taxiCompmay.exceptionHandling.UrlRequestException;
import se.anas.taxiCompmay.interfaces.UserService;
import se.anas.taxiCompmay.repositories.TaxiRepo;
import se.anas.taxiCompmay.repositories.UserRepo;
import se.anas.taxiCompmay.resourses.Taxi;
import se.anas.taxiCompmay.resourses.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepo userRepo;    // to can access all methods in userReop
    @Autowired
    private TaxiRepo taxiRepo;      // to can access all methods in taxiReop


    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }


    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }


    // this method handle exception
    public User getUserWithThrowException(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UrlRequestException());
    }

    @Override
    public Optional<User> getUserOptional(Long id) {     // what is the different in response in postman
        return userRepo.findById(id);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // another way to get all users
    @Override
    public ResponseEntity<Object> getAllUsersWithResponse() {
        List<User> dbUsers = userRepo.findAll();
        if (dbUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dbUsers, HttpStatus.OK);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // to get taxiList for a user
    @Override
    public List<Taxi> getUserTaxiList(Long id) {
        return userRepo.findById(id).get().getTaxiList();
    }



    // here we UPDATE a user state when we delete taxi from user's taxiList
    @Override
    public String deleteTaxiFromUserWithoutCheck(Long user_id, Long taxiNo) {
        User savedUser = userRepo.findById(user_id).get();
        Taxi savedTaxi = taxiRepo.findById(taxiNo).get();
        //userRepo.findById(id).get().getTaxiList();    // the same
        List<Taxi> savedUserTaxiList = getUserTaxiList(user_id);
        /*
        userRepo.findById(user_id).get().getTaxiList().remove(taxiRepo.findById(taxiNo));
        return String.format("taxi no %d is deleted from user id %d", taxiNo,user_id);
         */
        savedUserTaxiList.remove(savedTaxi);
        userRepo.save(savedUser);          // OMG must save the new state of user objcet after removing of taxi
        return String.format("taxi no %d is deleted from user id %d", taxiNo, user_id);
    }

    // here we UPDATE a user state when we delete taxi from user's taxiList
    @Override
    public ResponseEntity<Object> deleteTaxiFromUserWithCheck(Long user_id, Long taxiNo) {
        /*
        Optional<User> savedUser = userRepo.findById(user_id);
        Optional<Taxi> savedTaxi = taxiRepo.findById(taxiNo);
        List<Taxi> savedUserTaxiList = getUserTaxiList(user_id);  // external method I made it

         */
         User savedUser = userRepo.findById(user_id).get();
         Taxi savedTaxi = taxiRepo.findById(taxiNo).get();
         List<Taxi> savedUserTaxiList = getUserTaxiList(user_id);  // external method I made it


        if (userRepo.existsById(user_id)) {         // check if this user is already exist in user table
            if (taxiRepo.existsById(taxiNo)) {                           // check if this taxi is also exist in taxi table
                if (savedUserTaxiList.contains(savedTaxi)) {     // check if this user has this taxi in its own taxiList
                    savedUserTaxiList.remove(savedTaxi);          // if true so remove it
                    userRepo.save(savedUser);
                } else {
                    return new ResponseEntity<Object>(String.format("user id %d has not this taxi id %d", user_id, taxiNo), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<Object>(String.format("taxi no %d not found in taxi table", taxiNo), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<Object>(String.format("user id %d not found in user table", user_id), HttpStatus.NOT_FOUND);
    }


    @Override
    public User updateUser(Long id, User user) {
        User savedUser = userRepo.findById(id).get();   // without check
        //  savedUser.setId(user.getId());                   // not need
        savedUser.setFirst_name(user.getFirst_name());
        savedUser.setLast_name(user.getLast_name());
        savedUser.setAge(user.getAge());
        return userRepo.save(savedUser);

    }

    // why when id is not found we got Not null?
    // but we use optional type to get user we get null if id is not found
    @Override
    public Optional<User> updateOptionalUser(Long id, User user) {
        Optional<User> savedUser = userRepo.findById(id);

        savedUser.get().setFirst_name(user.getFirst_name());
        savedUser.get().setLast_name(user.getLast_name());
        savedUser.get().setAge(user.getAge());
        userRepo.save(savedUser.get());
        return savedUser;

    }


    // get 1 repsonse if we put not found id (not error)
    @Override
    public User updateUserWithCheck(Long id, User user) {
        User savedUser;
        try {
            savedUser = userRepo.findById(id).get();

            savedUser.setFirst_name(user.getFirst_name());
            savedUser.setLast_name(user.getLast_name());
            savedUser.setAge(user.getAge());
            return userRepo.save(savedUser);
        } catch (ResourceAccessException e) {
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public User updateUserWithBoolean(Long id, User user) {
        User savedUser;
        if (checkUser(id)) {
            savedUser = userRepo.findById(id).get();   // without check
            savedUser.setFirst_name(user.getFirst_name());
            savedUser.setLast_name(user.getLast_name());
            savedUser.setAge(user.getAge());
            return userRepo.save(savedUser);
        }
        System.out.println("objcet is not found");   // we get this message in console and 1 in postman
        return null;
    }

    // add taxi car to a user without check if user and car are exist or not
    @Override
    public User addTaxiCarToUser(Long user_id, Taxi taxi) {
        User saveUser = userRepo.findById(user_id).get();
        saveUser.getTaxiList().add(taxi);
        // saveUser.setTaxiList(new ArrayList<>(Arrays.asList(taxi)));                // extra :)
        userRepo.save(saveUser);
        return saveUser;
    }


    // this is better to avoid error when id is not exist in user repository
    @Override
    public ResponseEntity<Optional<User>> addTaxiCarToUserByCheck(Long user_id, Taxi taxi) {
        Optional<User> saveUser = null;
        Boolean flag = taxiRepo.findAll().contains(taxi);    // check if taxi is exist in taxi repository or not

        if (userRepo.existsById((user_id))) {     // first condition if id is existed in user repository
            if (!flag)                              // if taxi is not exist in taxi db
                taxiRepo.save(taxi);                 // save this new taxi to taxi db
            saveUser = userRepo.findById(user_id);
            saveUser.get().getTaxiList().add(taxi);       // optional and that's why saveUser.get()
            userRepo.save(saveUser.get());
        } else {
            return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<User>>(HttpStatus.OK);
    }


    public boolean checkUser(Long id) {
        return userRepo.existsById((id));
    }
/*
    public List<User> findAllByFirst_name(String first_name){
        return userRepo.findUsersByFirst_name(first_name);
    }

 */


}
