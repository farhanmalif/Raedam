/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Kemal Crisannaufal
 */
import Model.PetugasParkir;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetugasController {      
    public void tambahPetugas(PetugasParkir petugas) {
        String sql = "INSERT INTO PETUGASPARKIR (namaPetugas, noHpPetugas, emailPetugas, usernamePetugas, passwordPetugas) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            System.out.println(petugas.getIdPetugas());
            statement.setString(1, petugas.getNamaPetugas());
            statement.setString(2, petugas.getNomorHandphone());
            statement.setString(3, petugas.getEmailPetugas());
            statement.setString(4, petugas.getUsername());
            statement.setString(5, petugas.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getPassword(String username) {
        boolean found = false;
        ArrayList<String> listUsername = getAllUsernames();
        for (int i = 0; i < listUsername.size() && !found; i++){
            if (listUsername.get(i).equals(username)){
                found = true;
            }
        }
        
        if (found) {
            String sql = "SELECT * FROM PetugasParkir WHERE usernamePetugas = ?";

        try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("passwordPetugas");
                    return password;
                } 
            }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public ArrayList<String> getAllUsernames() {
        String sql = "SELECT usernamePetugas FROM PETUGASPARKIR";
        ArrayList<String> allUsername = new ArrayList<>();
      
        try (
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                String username = resultSet.getString("usernamePetugas");
                allUsername.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsername;
    }
    
    public ArrayList<PetugasParkir> getAllPetugas(){
        ArrayList<PetugasParkir> listPetugas = new ArrayList<>();
        String sql = "SELECT * FROM PETUGASPARKIR";
        
        try (
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                int idPetugas = resultSet.getInt("idPetugas");
                String username = resultSet.getString("usernamePetugas");
                String namaPetugas = resultSet.getString("namaPetugas");
                String password = resultSet.getString("passwordPetugas");
                String noHp = resultSet.getString("noHpPetugas");
                String emailPetugas = resultSet.getString("emailPetugas");
                
                listPetugas.add(new PetugasParkir(idPetugas, namaPetugas, noHp, noHp, username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPetugas;
    }
    
    
    public void registrasiPetugas(String namaPetugas, String nomorHandphone, String emailPetugas, String username, String password) {
        PetugasParkir petugas = new PetugasParkir(namaPetugas, nomorHandphone, emailPetugas, username, password);            
        tambahPetugas(petugas);
    }
    
    public boolean validasiRegistrasi(String nama, String username, String email, String noHP, String pwd, String pwdConfirm){
        return nama != null && username != null && email != null && noHP != null && pwd != null && pwdConfirm != null && (pwd.equalsIgnoreCase(pwdConfirm));
    }
    
    public boolean isUsernameUnique(String username){
        ArrayList<String> allUsername = getAllUsernames();
        
        boolean unique = true;
        for (int i= 0; i < allUsername.size() && unique; i++) {;
            unique = !username.equals(allUsername.get(i));
        }
        return unique;
    }
    
    public boolean isLengthPasswordEnough(String password){
        return password.length() >= 8;
    }
    
    public boolean validasiLogin(String username, String password) {
        String pwd = getPassword(username);
        return  (pwd != null) ? pwd.equals(password) : false;
    }

    public PetugasParkir searchByUsername(String username) {
        for (PetugasParkir p : getAllPetugas()){
            if (p.getUsername().equals(username)){
                return p;
            }
        }
        return null;
    }
    
    public PetugasParkir searchById(int id) {
        for (PetugasParkir p : getAllPetugas()){
            if (p.getIdPetugas()==id){
                return p;
            }
        }
        return null;
    }
    
    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
    
}
