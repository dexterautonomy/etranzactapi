package com.etranzact.etranzactapp.repository;

import com.etranzact.etranzactapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findBySessionID(String sessionID);
}
