package com.esprit.services;

import com.esprit.models.account;
import com.esprit.models.transaction;
import com.esprit.utils.DataSource;
import okhttp3.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class transactionService implements IService<transaction>{
    private final Connection connection;
    private List<Integer> allAccountIds;

    public transactionService(){ connection = DataSource.getInstance().getConnection(); }

    @Override
    public void ajouter(transaction transaction)
    {
        String req = "INSERT INTO transaction(montant, account_debited, account_destination, date_transaction, type_transaction, description, id_account_id) VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(7, transaction.getid_account_id());
            pst.setString(6, transaction.getdescription());
            pst.setString(5, transaction.gettype_transaction());
            pst.setString(4, transaction.getdate_transaction());
            pst.setString(3, transaction.getaccount_destination());
            pst.setString(2, transaction.getaccount_debited());
            pst.setDouble(1, transaction.getmontant());
            pst.executeUpdate();
            System.out.println("transaction effectué avec succée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(transaction transaction) {
        String req = "UPDATE transaction SET montant= ?,account_debited= ?,account_destination= ?,date_transaction= ?,type_transaction= ?,description = ?,id_account_id = ? WHERE id = ?;";
            try {
                PreparedStatement pst = connection.prepareStatement(req);
                pst.setInt(7, transaction.getid_account_id());
                pst.setString(6, transaction.getdescription());
                pst.setString(5, transaction.gettype_transaction());
                pst.setString(4, transaction.getdate_transaction());
                pst.setString(3, transaction.getaccount_destination());
                pst.setString(2, transaction.getaccount_debited());
                pst.setDouble(1, transaction.getmontant());
                pst.executeUpdate();
            System.out.println("transaction modifiée avec succée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(transaction transaction) {
    String req = "DELETE FROM `transaction` WHERE id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, transaction.getId());
            pst.executeUpdate();
            System.out.println("transaction supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<transaction> afficher() {
        List<transaction> transactions = new ArrayList<>();
        String req = "SELECT * FROM transaction";
            try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transactions.add(new transaction(rs.getInt("id"), rs.getDouble("montant"), rs.getString("account_debited"), rs.getString("account_destination"),rs.getString("date_transaction"),rs.getString("type_transaction"),rs.getString("description"),rs.getInt("id_account_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    public transaction rechercheTransaction(int id){
        transaction transaction =null;
        String req ="SELECT * FROM transaction WHERE id= ?";
        try{
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
            {
                transaction = new transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setMontant(rs.getDouble("montant"));
                transaction.setAccount_debited((rs.getString("account_debited")));
                transaction.setAccount_destination(rs.getString(("account_destination")));
                transaction.setDate_transaction((rs.getString("date_transaction")));
                transaction.setType_transaction(rs.getString("type_transaction"));
                transaction.setDescription(rs.getString("description"));
                //cle etrangére
                accountService ac = new accountService();
                account account = ac.rechercheaccount(rs.getInt("id"));
                //transaction.setaccount(account);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return transaction;
    }


    ///Cette partie du code ma permis de supprimer un compte meme si j'ai une jointure avec la table transaction
    public void deleteTransactionsByAccountId(int accountId) throws SQLException {
        String req = "DELETE FROM transaction WHERE id_account_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, accountId);
            pst.executeUpdate();
        }
    }

////////////////////////////
// Méthode pour récupérer tous les IDs de compte disponibles
public List<Integer> getAllAccountIds() {
    List<Integer> accountIds = new ArrayList<>();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

        // Requête SQL pour récupérer tous les IDs de compte
        String sql = "SELECT id FROM account";

        // Préparation de la requête
        stmt = connection.prepareStatement(sql);

        // Exécution de la requête
        rs = stmt.executeQuery();

        // Parcours des résultats et ajout des IDs à la liste
        while (rs.next()) {
            int id = rs.getInt("id");
            accountIds.add(id);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Gérer les erreurs de base de données
    } finally {
        // Fermeture des ressources
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return accountIds;
    }

    public void envoyermail(transaction transaction)
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@demomailtrap.com\",\"name\":\"Transaction effectuée avec succées\"},\"to\":[{\"email\":\"selim03gaaloul@gmail.com\"}],\"subject\":\"Verification de transaction\",\"text\":\"Merci d'avoir subvenu et d'avoir effectuée une transaction \",\"category\":\"Integration Test\"}");
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer e0970a027a348eb6c62fbf88336ac050")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("mailenvoyer");
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
    }

    ///partie statistique
    public List<transaction> getTop3tansactions() {
        List<transaction> transactions = new ArrayList<>();

        // Requête SQL pour sélectionner toutes les transactions
        String query= "SELECT * "+
        "FROM transaction "+
        "GROUP BY account_destination "+
        "ORDER BY transaction_count DESC "+
        "LIMIT 3";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Exécution de la requête
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcours des résultats et création des objets Transaction
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double montant = resultSet.getDouble("montant");
                String accountDebited = resultSet.getString("account_debited");
                String accountDestination = resultSet.getString("account_destination");
                String dateTransaction = resultSet.getString("date_transaction");
                String typeTransaction = resultSet.getString("type_transaction");
                String description = resultSet.getString("description");
                int idAccountId = resultSet.getInt("id_account_id");

                // Création de l'objet Transaction avec les données récupérées
                transaction transaction = new transaction( montant, accountDebited, accountDestination, dateTransaction, typeTransaction, description, idAccountId);

                // Ajout de l'objet Transaction à la liste
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<transaction> recupererTransactionPret() {
        List<transaction> transactions = new ArrayList<>();
        String req = "SELECT * from transaction WHERE type_transaction='pret'";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transactions.add(new transaction(rs.getInt("id"), rs.getDouble("montant"), rs.getString("account_debited"), rs.getString("account_destination"),rs.getString("date_transaction"),rs.getString("type_transaction"),rs.getString("description"),rs.getInt("id_account_id")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    public List<transaction> recupererTransactionCollect() {
        List<transaction> transactions = new ArrayList<>();
        String req = "SELECT * from transaction WHERE type_transaction='collect'";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transactions.add(new transaction(rs.getInt("id"), rs.getDouble("montant"), rs.getString("account_debited"), rs.getString("account_destination"),rs.getString("date_transaction"),rs.getString("type_transaction"),rs.getString("description"),rs.getInt("id_account_id")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }



    public List<transaction> recupererTransactionAutre() {
        List<transaction> transactions = new ArrayList<>();
        String req = "SELECT * from transaction WHERE type_transaction='autre'";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transactions.add(new transaction(rs.getInt("id"), rs.getDouble("montant"), rs.getString("account_debited"), rs.getString("account_destination"),rs.getString("date_transaction"),rs.getString("type_transaction"),rs.getString("description"),rs.getInt("id_account_id")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }


}
