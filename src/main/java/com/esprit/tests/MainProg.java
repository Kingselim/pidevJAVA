package com.esprit.tests;

import com.esprit.models.Participant;
import com.esprit.services.ServiceParticipant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.esprit.utils.DataSource;
import com.esprit.models.Seminaire;
import com.esprit.services.ServiceSeminaire;



public class MainProg {

    public static void main(String[] args) {

        // AJOUTER
        //ServiceSeminaire su=new ServiceSeminaire();
        //su.ajouter(new Seminaire(1,"29/03/2024","aa","LOOL"));
        //MODIFIER
        //ServiceSeminaire su=new ServiceSeminaire();
        //su.modifier(new Seminaire(3,1, "04/04/2024","aa","LOOL"));

        //SUPPRIMER
        //ServiceSeminaire su=new ServiceSeminaire();
        //su.supprimer( new Seminaire(2,1,"29/03/2024","aa","LOOL"));
        //su.supprimer(2);

        // AJOUTER PARTICIPANT
        //ServiceParticipant su= new  ServiceParticipant();
        //su.ajouter(new Participant(3,"Chehida","Nour","Etudiante",29705733));
        //MODIFIER PARTICIPANT
        //ServiceParticipant su=new ServiceParticipant();
        //su.modifier(new Participant(2,3,"Chehida","Sam","Etudiant",29705733));

        //SUPPRIMER PARTICIPANT
        //ServiceParticipant su=new ServiceParticipant();
        //su.supprimer( new Participant(2,3,"Chehida","Sam","Etudiant",29705733));
        //su.supprimer(2);

    }
}
