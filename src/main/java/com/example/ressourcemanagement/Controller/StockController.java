package com.example.ressourcemanagement.Controller;

import com.example.ressourcemanagement.Models.Materials;
import com.example.ressourcemanagement.Models.Stock;
import com.example.ressourcemanagement.Services.MaterialsFunImpl;
import com.example.ressourcemanagement.Services.StockFunImpl;
import com.example.ressourcemanagement.Services.StockFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockFunctionality stockFunctionality;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockFunctionality.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable int id) {
        Stock stock = stockFunctionality.getStockById(id);
        if (stock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(stock);
    }


    @PostMapping("/add")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockFunctionality.createStock(stock);
        return ResponseEntity.ok(createdStock);
    }

    // Mettre à jour un stock existant
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable int id, @RequestBody Stock stockDetails) {
        Stock existingStock = stockFunctionality.getStockById(id);
        if (existingStock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Retourne NOT FOUND si le stock n'existe pas
        }

        Stock updatedStock = stockFunctionality.updateStock(id, stockDetails); // Vous mettez à jour ici
        return ResponseEntity.ok(updatedStock);  // Retourne l'objet mis à jour
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable int id) {
        stockFunctionality.deleteStock(id);
        return ResponseEntity.ok("{\"message\": \"Stock supprimé avec succès !\"}");
    }

}
