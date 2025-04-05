package com.example.environnement.services;

import com.example.environnement.entities.Chantier;
import com.example.environnement.entities.Zones;
import com.example.environnement.repositories.ChantierRepository;
import com.example.environnement.repositories.Usersrepository;
import com.example.environnement.repositories.ZonesRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceEnvironnement {


        private final ChantierRepository ch;
        private final Usersrepository ur;
        private final ZonesRepository zn;

    public ServiceEnvironnement(ChantierRepository ch, Usersrepository ur, ZonesRepository zn) {
        this.ch = ch;
        this.ur = ur;
        this.zn = zn;
    }


    //Chantier

        public List<Chantier> getALlChantier(){
            return ch.findAll();
        }

        public Chantier addChantier(Chantier budget){
            return ch.save(budget);
        }

        public Chantier ModifChantier(Chantier budget){
            return ch.save(budget);

        }

        public void DeleteChantier(Long id){
            ch.deleteById(id);
        }
        public  Chantier findById(Long id){
            return ch.findById(id).get();
        }



//Expences



        public List<Zones> getAllZones(){
            return zn.findAll();

        }

        public Zones AddZones(Zones expences){
            return zn.save(expences);
        }

        public Zones UpdateZones(Zones expences){
            return zn.save(expences);
        }

        public void DeleteZones(Long id){
            zn.deleteById(id);
        }
        public Zones GEtByIdZones(Long id){
            return zn.findById(id).get();
        }

        //Tache







        //Zones
    }


