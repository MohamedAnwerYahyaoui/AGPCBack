package com.example.ressourcemanagement.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter

@AllArgsConstructor
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private int quantity;
    private double unitPrice;
    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @OneToMany(mappedBy = "materiel", cascade = CascadeType.ALL)
    private List<Stock> stockList;

    public Materials() {
    }

    public Materials(String name, int quantity, double unitPrice, Categorie categorie, List<Stock> stockList) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.categorie = categorie;
        this.stockList = stockList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
