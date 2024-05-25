package com.fintrackusermanagement.usermanagement.repository;

import com.fintrackusermanagement.usermanagement.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    List<User> findByEmail(String email);
    User findByUsername(String username);

}
