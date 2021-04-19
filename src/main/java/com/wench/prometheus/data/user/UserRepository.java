package com.wench.prometheus.data.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Native MySQL code with an embedded ?1 which it used to emplace the first parameter of this method.
    @Query(
            value = "SELECT * FROM users WHERE email LIKE ?1",
            nativeQuery = true
    )
    List<User> getUserWithEmailLike(String email);

    // JPQL Query non-native. Does not utilize noremal MySQL syntax
    @Query("SELECT user FROM User user WHERE user.firstName LIKE ?1")
    List<User> getUserWithFirstNameLike(String firstName);

    @Query("SELECT user FROM User user WHERE user.lastName LIKE ?1")
    List<User> getUserWithLastNameLike(String lastName);
}
