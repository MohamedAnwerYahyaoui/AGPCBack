package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.DAO.StockRepository;
import com.example.ressourcemanagement.Models.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockFunImpl implements StockFunctionality {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockById(int id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }

    @Override
    public Stock createStock(Stock stock) {
        Stock createdStock = stockRepository.save(stock);
        checkStockAlert(createdStock); // Vérifier l'alerte après la création
        return createdStock;
    }
    @Override
    public Stock updateStock(int id, Stock stockDetails) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));

        stock.setMateriel(stockDetails.getMateriel());
        stock.setCurrentQuantity(stockDetails.getCurrentQuantity());
        stock.setThreshold(stockDetails.getThreshold());

        Stock updatedStock = stockRepository.save(stock);
        checkStockAlert(updatedStock); // Vérifier l'alerte après la mise à jour
        return updatedStock;
    }

    @Override
    public void deleteStock(int id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
        stockRepository.delete(stock);
    }
    private void checkStockAlert(Stock stock) {
        if (stock.getCurrentQuantity() < stock.getThreshold()) {
            sendAlert(stock);
        }
    }

    private void sendAlert(Stock stock) {
        String alertMessage = "ALERTE : Le stock de " + stock.getMateriel().getName() +
                " est inférieur au seuil (" + stock.getThreshold() +
                "). Quantité actuelle : " + stock.getCurrentQuantity();
        System.out.println(alertMessage); // Affiche le message dans la console
    }
}