package org.example;

import entities.Demande;
import entities.Pret;
import entities.User;
import services.DemandeService;
import services.PretService;
import services.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        UserService userService = new UserService();
        PretService pretService = new PretService();
        DemandeService demandeService = new DemandeService();

//        User user = new User();
//        user.setId(1L);
//        Pret pret = new Pret();
//        pret.setId(1L);
//
//         Demande demande = new Demande("pending", "personal", "emergency", user, pret);
//         demandeService.addDemande(demande);
//
     String searchTerm = "esprit";

        // Call the searchPrets method and print the results
        List<Pret> results = pretService.searchPrets(searchTerm);
        for (Pret result : results) {
            System.out.println(result);
        }

    }
    }
