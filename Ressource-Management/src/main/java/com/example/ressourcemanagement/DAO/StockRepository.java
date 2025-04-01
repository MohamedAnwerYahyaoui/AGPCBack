package com.example.ressourcemanagement.DAO;

import com.example.ressourcemanagement.Models.Materials;
import com.example.ressourcemanagement.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository  extends JpaRepository<Stock,Integer> {
}
