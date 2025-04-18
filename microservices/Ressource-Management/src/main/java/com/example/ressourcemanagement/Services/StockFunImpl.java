package com.example.ressourcemanagement.Services;

import com.example.ressourcemanagement.DAO.MaterialsRepository;
import com.example.ressourcemanagement.DAO.StockRepository;
import com.example.ressourcemanagement.Models.Materials;
import com.example.ressourcemanagement.Models.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StockFunImpl implements StockFunctionality {

    private static final Logger logger = LoggerFactory.getLogger(StockFunImpl.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MaterialsRepository materialsRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;


    @Override
    public List<Stock> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(stock -> System.out.println("Stock trouvé : " + stock));
        return stocks;
    }


    @Override
    public Stock getStockById(int id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }

    @Override
    public Stock createStock(Stock stock) {

        System.out.println("Données reçues : " + stock);


        if (stock.getMateriel() == null || stock.getMateriel().getId() == 0) {
            System.out.println("Erreur : Aucun matériel sélectionné ou ID invalide");
            throw new RuntimeException("Materials not found with id: 0");
        }


        Materials materiel = materialsRepository.findById(stock.getMateriel().getId())
                .orElseThrow(() -> {
                    System.out.println("Matériel non trouvé avec ID : " + stock.getMateriel().getId()); // Debug
                    return new RuntimeException("Materials not found with id: " + stock.getMateriel().getId());
                });

        System.out.println("Matériel trouvé : " + materiel);


        stock.setMateriel(materiel);


        Stock createdStock = stockRepository.save(stock);


        System.out.println("Stock créé : " + createdStock);


        checkStockAlert(createdStock);

        return createdStock;
    }
    @Override
    public Stock updateStock(int id, Stock stockDetails) {
        // Vérification si le stock existe dans la base de données
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));


        stock.setMateriel(stockDetails.getMateriel());
        stock.setCurrentQuantity(stockDetails.getCurrentQuantity());
        stock.setThreshold(stockDetails.getThreshold());


        Stock updatedStock = stockRepository.save(stock);


        checkStockAlert(updatedStock);


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
         //  try {
          //     sendEmailAlert(stock);
          //  } catch (MessagingException e) {
           //    logger.error("Erreur lors de l'envoi de l'email d'alerte", e);
         //  }
        }
    }

    private void sendAlert(Stock stock) {
        String alertMessage = "⚠️ ALERTE : Le stock de " + stock.getMateriel().getName() +
                " est inférieur au seuil (" + stock.getThreshold() +
                "). Quantité actuelle : " + stock.getCurrentQuantity();
        logger.info(alertMessage);
    }

    private void sendEmailAlert(Stock stock) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(mailFrom);
        helper.setTo(mailFrom);
        helper.setSubject("⚠️ Alerte de stock");

        String htmlContent = "<html>" +
                "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                "<h2 style='color: #d9534f;'>⚠️ ALERTE : Stock Faible</h2>" +
                "<p>Le stock <strong>" + stock.getMateriel().getName() + "</strong> est inférieur au seuil.</p>" +
                "<ul>" +
                "<li><strong>Seuil :</strong> " + stock.getThreshold() + "</li>" +
                "<li><strong>Quantité actuelle :</strong> " + stock.getCurrentQuantity() + "</li>" +
                "</ul>" +
                "<p style='color: #777; font-size: 0.9em;'>Veuillez prendre les mesures nécessaires pour réapprovisionner le stock.</p>" +
                "</body>" +
                "</html>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
        logger.info("📧 Email d'alerte envoyé avec succès pour le stock de " + stock.getMateriel().getName());
    }


}
