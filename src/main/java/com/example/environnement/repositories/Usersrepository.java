package com.example.environnement.repositories;

import com.example.environnement.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface Usersrepository extends JpaRepository<Users,Long> {
}
