package com.example.employee.Services;
import com.example.employee.Entities.Livrable;
import com.example.employee.Entities.LivrableSpecification;
import com.example.employee.Repositories.LivrableRepositoriy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivrableService {

    private  final LivrableRepositoriy livrableRepositoriy;
    public LivrableService(LivrableRepositoriy livrableRepositoriy){
        this.livrableRepositoriy = livrableRepositoriy;
    }



    public Livrable addLivrable(Livrable livrable) {

        return livrableRepositoriy.save(livrable);
    }



    public List<Livrable> findAll() {

        return livrableRepositoriy.findAll();
    }


    public Livrable updateLivrable(long id, Livrable newLivrable) {
        if (livrableRepositoriy.findById(id).isPresent()) {
            Livrable livrable = livrableRepositoriy.findById(id).get();
            livrable.setNom(newLivrable.getNom());
            return livrableRepositoriy.save(livrable);
        } else
            return null;
    }


    public String deleteLivrable(long id) {
        if (livrableRepositoriy.findById(id).isPresent()) {
            livrableRepositoriy.deleteById(id);
            return "Livrable est  supprimé avec succés";
        } else
            return "Livrable non supprimé";
    }
    public Page<Livrable> getLivrables(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Livrable> spec = LivrableSpecification.searchByNom(searchTerm);
        return livrableRepositoriy.findAll(spec, pageable);
    }
}
