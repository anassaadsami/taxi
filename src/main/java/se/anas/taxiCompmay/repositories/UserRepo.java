package se.anas.taxiCompmay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.anas.taxiCompmay.resourses.User;

import java.util.List;

public interface UserRepo extends JpaRepository< User, Long> {

    // make query to get all user by first name and order them by age
    // and that's why UserController class take UserRepo also as instance attribute to can get this method

   // List<User> findByFirst_name(String first_name);    //it doesn't work

    //@Query("FROM User u WHERE u.first_name = ?1")
    //List<User> findUsersByFirst_name(String first_name);
/*
    @Test
    public void whenDeletingCategories_thenBooksShouldAlsoBeDeleted() {
        categoryRepository.deleteAll();
        assertThat(bookRepository.count()).isEqualTo(0);
        assertThat(categoryRepository.count()).isEqualTo(0);
    }
    @Test
public void whenDeletingBooks_thenCategoriesShouldAlsoBeDeleted() {
    bookRepository.deleteAll();
    assertThat(bookRepository.count()).isEqualTo(0);
    assertThat(categoryRepository.count()).isEqualTo(2);
}

 */

}
