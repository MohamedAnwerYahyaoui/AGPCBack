package com.example.environnement.repositories;

import com.example.environnement.entities.Zones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonesRepository extends JpaRepository<Zones,Long> {


}
