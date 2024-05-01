package com.esprit.tests;

import com.esprit.services.accountService;

public class MainProg {

    public static void main(String[] args) {
        //PersonneService ps = new PersonneService();
        //ps.ajouter(new Personne("Ahmed", "Med"));
        //ps.supprimer(new Personne(3, "Ahmed", "Med"));
        //ps.modifier(new Personne(1, "Emma", "Zouaoui"));
        //System.out.println(ps.afficher());



        accountService ac = new accountService();
        //ac.ajouter(new account(990.14,"oui","20-12-2012",0,4));
        //ac.modifier(new account(1,11.00,"transaction","01-11-2001",1,1));
        //ac.supprimer(new account(1,11.00,"transaction","01-11-2001",1,1));
        System.out.println(ac.afficher());
        /*transactionService tr = new transactionService();
        tr.ajouter(new transaction(15,25.45,"HAHFBE-KZHBF-ZEHI-45ZEF","ZEFZ-FZEF-56FE-ZFZEF","31-03-2024","Collect","BESOIN",11));
        //tr.modifier(new transaction(14,99.02,"GHADDABYASS","GAALOULSELIM","12-10-2024","Collect","HHHHHHHH",11));*/
    }
}
