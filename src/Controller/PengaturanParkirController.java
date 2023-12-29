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

import Model.PengaturanParkir;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class PengaturanParkirController {
    private final PetugasController petugasC = new PetugasController();
    private final ArrayList<PengaturanParkir> listPengaturan = getAllPengaturanParkir();
    private final PengaturanParkir pengaturanParkir = setPengaturanParkir(); 
    
    public PengaturanParkir setPengaturanParkir(){
         PengaturanParkir pengaturan = null;
        if (!listPengaturan.isEmpty()){
            pengaturan = listPengaturan.get(listPengaturan.size()-1); 
        }
        return pengaturan;
    }

    public ArrayList<PengaturanParkir> getListPengaturan() {
        return listPengaturan;
    }

    public PengaturanParkir getPengaturanParkir() {
        return pengaturanParkir;
    }
    

    public ArrayList<PengaturanParkir> getAllPengaturanParkir() {
        String sql = "SELECT * FROM PENGATURANPARKIR";
        ArrayList<PengaturanParkir> listPengaturan = new ArrayList<>();
        
        try (
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                int idPengaturan = resultSet.getInt("idPengaturan");
                int tarifParkirMobilPerJam = resultSet.getInt("tarifParkirMobilPerJam");
                int tarifParkirMotorPerJam = resultSet.getInt("tarifParkirMotorPerJam");
                int tarifParkirMobilPerHari = resultSet.getInt("tarifParkirMobilPerHari");
                int tarifParkirMotorPerHari = resultSet.getInt("tarifParkirMotorPerHari");
                int kuotaParkirMotor = resultSet.getInt("kuotaParkirMotor");
                int kuotaParkirMobil = resultSet.getInt("kuotaParkirMobil");
                LocalDateTime waktuAtur = resultSet.getTimestamp("waktuAtur").toLocalDateTime();
                int idPetugas = resultSet.getInt("idPetugas");
                listPengaturan.add(new PengaturanParkir(idPengaturan, kuotaParkirMotor, kuotaParkirMobil, tarifParkirMotorPerJam, tarifParkirMobilPerJam, tarifParkirMotorPerHari, tarifParkirMobilPerHari, waktuAtur, petugasC.searchById(idPetugas)));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPengaturan;
    }
    
    public void insertPengaturan(PengaturanParkir pengaturan) {
        String sql = "INSERT INTO PENGATURANPARKIR (tarifParkirMobilPerJam, tarifParkirMotorPerJam, tarifParkirMotorPerHari, tarifParkirMobilPerHari, kuotaParkirMobil, kuotaParkirMotor, waktuAtur, idPetugas) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, pengaturan.getTarifParkirMobilPerJam());
            statement.setInt(2, pengaturan.getTarifParkirMotorPerJam());
            statement.setInt(3, pengaturan.getTarifParkirMotorPerHari());
            statement.setInt(4, pengaturan.getTarifParkirMobilPerHari());
            statement.setInt(5, pengaturan.getKuotaParkirMobil());
            statement.setInt(6, pengaturan.getKuotaParkirMotor());
            statement.setString(7, pengaturan.getWaktuAtur().toString());            
            statement.setInt(8, pengaturan.getPetugasParkir().getIdPetugas());
            statement.executeUpdate();
            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                System.out.println("Test");
                int id = generatedKeys.getInt(1);
                pengaturan.setIdPengaturan(id);
            }
            listPengaturan.add(pengaturan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public PengaturanParkir searchById(int idPengaturan){
        for (int i = 0; i < listPengaturan.size(); i++){
            if (listPengaturan.get(i).getIdPengaturan() == idPengaturan){
                return listPengaturan.get(i);
            }
        }
        return null;
    }
    
}
