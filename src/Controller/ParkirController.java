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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Parkir;
import Model.ParkirInap;
import Model.ParkirPerJam;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.Date;


public class ParkirController {
    
    private final PengaturanParkirController pengaturanC = new PengaturanParkirController();
    
    
    private ArrayList<Parkir> listParkir = getAllParkirData();
    private final ArrayList<Parkir> listParkirParkir = (ArrayList <Parkir>)listParkir.stream().filter(p -> p.getStatusParkir().equalsIgnoreCase("PARKIR")).collect(Collectors.toList());    
    private final int nMotor = listParkirParkir.stream().filter(p -> p.getJenisKendaraan().equalsIgnoreCase("MOTOR")).collect(Collectors.toList()).size();
    private final int nMobil = listParkirParkir.stream().filter(p -> p.getJenisKendaraan().equalsIgnoreCase("MOBIL")).collect(Collectors.toList()).size();
   

    public ArrayList<Parkir> getListParkir() {
        return getAllParkirData();
    }

    public ArrayList<Parkir> getListParkirParkir() {
        return listParkirParkir;
    }
    
    public int getnMotor() {
        return nMotor;
    }

    public int getnMobil() {
        return nMobil;
    }
    
    public Parkir searchByPlateNum(String nomor) { 
        for (Parkir p : listParkir) {
            if (p.getPlatNomor().replace(" ", "").equalsIgnoreCase(nomor.replace(" ", "")) && p.getStatusParkir().equalsIgnoreCase("PARKIR")) {
                return p;
            }
        }
        return null;
    }
    
    
    public Parkir searchById(int id) {
        for (Parkir p : listParkir){
            if (p.getNoParkir() == id){
                return p;
            }
        }
        return null;
    }
    
    public ArrayList<Parkir> getAllParkirData() {
        ArrayList<Parkir> listParkir = new ArrayList<>();
                
        String sql = "SELECT " +
        "PARKIR.idParkir, " +
        "PARKIR.idPengaturan, " +
        "PARKIR.nomorKendaraan, " +
        "PARKIR.jenisKendaraan, " +
        "PARKIR.tipeParkir, " +
        "PARKIR.statusParkir, " +
        "PARKIR.idParkirPerJam, " +
        "PARKIR.idParkirInap, " +
        "PARKIRPERJAM.waktuMasuk, " +
        "PARKIRPERJAM.waktuKeluar, " +
        "PARKIRINAP.tanggalMasuk, " +
        "PARKIRINAP.tanggalKeluar " +
        "FROM PARKIR " +
        "LEFT JOIN PARKIRPERJAM ON PARKIR.idParkirPerJam = PARKIRPERJAM.idParkirPerJam " +
        "LEFT JOIN PARKIRINAP ON PARKIR.idParkirInap = PARKIRINAP.idParkirInap";
        
        try (
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                int idParkir = resultSet.getInt("idParkir");
                int idPengaturan = resultSet.getInt("idPengaturan");
                String nomorKendaraan = resultSet.getString("nomorKendaraan");
                String jenisKendaraan = resultSet.getString("jenisKendaraan");
                String tipeParkir = resultSet.getString("tipeParkir");
                String statusParkir = resultSet.getString("statusParkir");
                int idParkirPerJam = resultSet.getInt("idParkirPerJam");
                int idParkirInap = resultSet.getInt("idParkirInap");
                
                
                if (tipeParkir.equalsIgnoreCase("INAP")) {
                    LocalDate tanggalMasuk = resultSet.getDate("tanggalMasuk").toLocalDate();
                    if (!statusParkir.equalsIgnoreCase("PARKIR")) {
                        LocalDate tanggalKeluar = resultSet.getDate("tanggalKeluar").toLocalDate();
                        listParkir.add(new ParkirInap(idParkir, tipeParkir, statusParkir, tanggalMasuk, tanggalKeluar, jenisKendaraan, nomorKendaraan, pengaturanC.searchById(idPengaturan), idParkirInap));
                    } else {
                        listParkir.add(new ParkirInap(idParkir, tipeParkir, statusParkir, tanggalMasuk, jenisKendaraan, nomorKendaraan, pengaturanC.searchById(idPengaturan), idParkirInap));
                    }
                } else {
                    LocalDateTime waktuMasuk = resultSet.getTimestamp("waktuMasuk").toLocalDateTime();
                    if (!statusParkir.equalsIgnoreCase("PARKIR")) {
                        LocalDateTime waktuKeluar = resultSet.getTimestamp("waktuKeluar").toLocalDateTime();
                        listParkir.add(new ParkirPerJam(idParkir, tipeParkir, statusParkir, waktuMasuk, waktuKeluar, jenisKendaraan, nomorKendaraan, pengaturanC.searchById(idPengaturan), idParkirPerJam));
                    } else {
                        listParkir.add(new ParkirPerJam(idParkir, tipeParkir, statusParkir, waktuMasuk, jenisKendaraan, nomorKendaraan, pengaturanC.searchById(idPengaturan), idParkirPerJam));
                    }
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listParkir;
    }
    
    public void insertParkir(Parkir parkir){
        if (parkir instanceof ParkirPerJam) {
            String sql1 = "INSERT INTO PARKIRPERJAM (waktuMasuk) VALUES (?)";
            String sql2 = "INSERT INTO parkir (nomorKendaraan, jenisKendaraan, tipeParkir, statusParkir, idPengaturan, idParkirPerJam) VALUES  (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS))
            { 
                statement1.setString(1, ((ParkirPerJam) parkir).getWaktuMasuk().toString());
                statement1.executeUpdate();
                
                ResultSet generatedKeys = statement1.getGeneratedKeys();
                
                 if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    ((ParkirPerJam) parkir).setIdParkirPerJam(id);
                    statement2.setString(1, parkir.getPlatNomor());
                    statement2.setString(2, parkir.getJenisKendaraan());
                    statement2.setString(3, parkir.getTipeParkir());
                    statement2.setString(4, parkir.getStatusParkir());
                    statement2.setInt(5, pengaturanC.getPengaturanParkir().getIdPengaturan());
                    statement2.setInt(6, id);
                    statement2.executeUpdate();
                    generatedKeys.close();
                    
                    ResultSet generatedKeys2 = statement2.getGeneratedKeys();
                    if (generatedKeys2.next()){
                        int id2 = generatedKeys2.getInt(1);
                        
                        parkir.setNoParkir(id2);
                    }
                    
                 } 
            } 
            catch (SQLException e) {
            e.printStackTrace();
        }
        } else {
            String sql1 = "INSERT INTO PARKIRINAP (tanggalMasuk) VALUES (?)";
            String sql2 = "INSERT INTO PARKIR (nomorKendaraan, jenisKendaraan, tipeParkir, statusParkir, idPengaturan, idParkirInap)  VALUES  (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS))
            { 
                statement1.setString(1, ((ParkirInap) parkir).getTanggalMasuk().toString());
                statement1.executeUpdate();
                
                ResultSet generatedKeys = statement1.getGeneratedKeys();
                
                 if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    ((ParkirInap) parkir).setIdParkirInap(id);
                    statement2.setString(1, parkir.getPlatNomor());
                    statement2.setString(2, parkir.getJenisKendaraan());
                    statement2.setString(3, parkir.getTipeParkir());
                    statement2.setString(4, parkir.getStatusParkir());
                    statement2.setInt(5, pengaturanC.getPengaturanParkir().getIdPengaturan());
                    statement2.setInt(6, id);
                    statement2.executeUpdate();
                    generatedKeys.close();
                    ResultSet generatedKeys2 = statement2.getGeneratedKeys();
                    if (generatedKeys2.next()){
                        int id2 = generatedKeys2.getInt(1);
                        parkir.setNoParkir(id2);
                    }
                 } 
            } 
            catch (SQLException e) {
            e.printStackTrace();
        }
     }
        
    }
    
    public void updateParkir(Parkir parkir){
        String sql = "UPDATE PARKIR SET statusParkir = ? WHERE idParkir = ?";
        String sql1 = "UPDATE PARKIRPERJAM SET waktuKeluar = ? WHERE idParkirPerJam = ?";
        String sql2 = "UPDATE PARKIRINAP SET tanggalKeluar = ? WHERE idParkirInap = ?";
        
        if (parkir.getTipeParkir().equalsIgnoreCase("PERJAM")) {
            try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement1 = connection.prepareStatement(sql);
             PreparedStatement statement2 = connection.prepareStatement(sql1))
            { 
                statement1.setString(1, "SELESAI");
                statement1.setInt(2, parkir.getNoParkir());
                
                statement2.setString(1, ((ParkirPerJam) parkir).getWaktuKeluar().toString());
                statement2.setInt(2, ((ParkirPerJam) parkir).getIdParkirPerJam());
                statement1.executeUpdate();
                statement2.executeUpdate();
            } 
            catch (SQLException e) {
            e.printStackTrace();
        } 
        } else {
            try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement1 = connection.prepareStatement(sql);
             PreparedStatement statement2 = connection.prepareStatement(sql2))
            { 
                statement1.setString(1, "SELESAI");
                statement1.setInt(2, parkir.getNoParkir());
                
                statement2.setString(1, ((ParkirInap) parkir).getTanggalKeluar().toString());
                statement2.setInt(2, ((ParkirInap) parkir).getIdParkirInap());
                statement1.executeUpdate();
                statement2.executeUpdate();
            } 
            catch (SQLException e) {
            e.printStackTrace();
            }
        }
    }
    
    public Parkir createObjekMasukParkir(String nomorKendaraan, String jenisKendaraan, String tipeParkir) {
        if (jenisKendaraan.equalsIgnoreCase("MOTOR") && getnMotor() < pengaturanC.getPengaturanParkir().getKuotaParkirMotor() || jenisKendaraan.equalsIgnoreCase("MOBIL") && getnMotor() < pengaturanC.getPengaturanParkir().getKuotaParkirMobil()){
                if (tipeParkir.equalsIgnoreCase("PERJAM")) {
                    return new ParkirPerJam(tipeParkir, "PARKIR", jenisKendaraan, nomorKendaraan, pengaturanC.getPengaturanParkir());
                } else {
                    return new ParkirInap(tipeParkir, "PARKIR", jenisKendaraan, nomorKendaraan, pengaturanC.getPengaturanParkir());
                }                
        } else {
            return null;
        }
    }
    
    
    public ArrayList<Parkir> searchParkirByTanggal(Date tanggal){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        ArrayList<Parkir> parkirTerpilih = new ArrayList<Parkir>();
        if (tanggal != null){
            String strTanggal = formater.format(tanggal);
            for (Parkir p : getListParkir()){
                if (p.getTipeParkir().equalsIgnoreCase("INAP")){
                    LocalDate tanggalMasuk = ((ParkirInap) p).getTanggalMasuk();
                    if (tanggalMasuk.getDayOfMonth() == Integer.parseInt(strTanggal.substring(0, 2)) 
                        && tanggalMasuk.getMonthValue() == Integer.parseInt(strTanggal.substring(3, 5))
                        && tanggalMasuk.getYear() == Integer.parseInt(strTanggal.substring(6, 10))){
                        parkirTerpilih.add(p);
                    }
                } else {
                    LocalDateTime waktuMasuk = ((ParkirPerJam) p).getWaktuMasuk();
                    if (waktuMasuk.getDayOfMonth() == Integer.parseInt(strTanggal.substring(0, 2)) 
                        && waktuMasuk.getMonthValue() == Integer.parseInt(strTanggal.substring(3, 5))
                        && waktuMasuk.getYear() == Integer.parseInt(strTanggal.substring(6, 10))){
                        parkirTerpilih.add(p);
                        }
                    }
            }
        }
        return parkirTerpilih;
    }
    
    public boolean isParkirKosong(String jenisKendaraan){
        return  (jenisKendaraan.equalsIgnoreCase("motor") && pengaturanC.getPengaturanParkir().getKuotaParkirMotor() > getnMotor()) || 
                    (jenisKendaraan.equalsIgnoreCase("mobil") && pengaturanC.getPengaturanParkir().getKuotaParkirMobil()> getnMobil());
    }
    
    public int deleteData(Parkir parkir){
        String sql1 = "DELETE FROM PARKIRPERJAM WHERE idParkirPerJam = ?";
        String sql2 = "DELETE FROM PARKIRINAP WHERE idParkirInap = ?";
        String sql3 = "DELETE FROM PARKIR WHERE idParkir = ?";
   
        
        int status = 400;
        if (parkir.getTipeParkir().equalsIgnoreCase("PERJAM")){
            try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement1 = connection.prepareStatement(sql1);
             PreparedStatement statement3= connection.prepareStatement(sql3))
            {
               statement1.setInt(1, ((ParkirPerJam) parkir).getIdParkirPerJam() );
                statement3.setInt(1, parkir.getNoParkir() );  
                statement3.executeUpdate();
                status = statement1.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        
        } else{
            try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement2 = connection.prepareStatement(sql1);
             PreparedStatement statement3= connection.prepareStatement(sql3))     
            {
                statement2.setInt(1, ((ParkirInap) parkir).getIdParkirInap());
                statement3.setInt(1, parkir.getNoParkir() ); 
                statement3.executeUpdate();
                status = statement2.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return status;

    }
    
    public void deleteParkirPerJam(int idParkirPerJam) {
        String sql = "DELETE FROM PARKIRPERJAM WHERE idParkirPerJam = ?";

        try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idParkirPerJam);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteParkirInap(int idParkirInap) {
        String sql = "DELETE FROM PARKIRINAP WHERE idParkirInap = ?";

        try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idParkirInap);
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateDataParkir(Parkir parkir, String platNomor, String tipeParkir, String jenisKendaraan, String waktuMasuk){
        if (parkir.getTipeParkir().equals("PERJAM")) {
            if (tipeParkir.equals("PERJAM")) {
                String sql = "UPDATE PARKIR SET nomorKendaraan = ?, tipeParkir = ?, jenisKendaraan = ? WHERE idParkir = ?";
                try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql))
                { 
                    statement.setString(1, platNomor);
                    statement.setString(2, tipeParkir);
                    statement.setString(3, jenisKendaraan);
                    statement.setInt(4, parkir.getNoParkir());
                    statement.executeUpdate();
                    listParkir = getAllParkirData();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                } 
            } else {
                String sql1 = "UPDATE PARKIR SET nomorKendaraan = ?, tipeParkir = ?, jenisKendaraan = ?, idParkirPerJam = null, idParkirInap = ? WHERE idParkir = ?";
                String sql2 = "INSERT INTO PARKIRINAP (tanggalMasuk) VALUES (?)";
                try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
                    PreparedStatement statement1 = connection.prepareStatement(sql1);
                    PreparedStatement statement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS))
                { 
                    LocalDateTime waktu = ((ParkirPerJam) parkir).getWaktuMasuk();
                    String tanggal = String.valueOf(waktu.getYear()) + "-" + String.valueOf(waktu.getMonthValue()) + "-" + String.valueOf(waktu.getDayOfMonth());
                    statement2.setString(1, tanggal);
                    statement2.executeUpdate();
                    ResultSet generatedKeys = statement2.getGeneratedKeys();
                    if (generatedKeys.next()){
                        int id = generatedKeys.getInt(1);
                        statement1.setString(1, platNomor);
                        statement1.setString(2, tipeParkir);
                        statement1.setString(3, jenisKendaraan);
                        statement1.setInt(4, id);
                        statement1.setInt(5, parkir.getNoParkir());
                        statement1.executeUpdate();
                        deleteParkirPerJam(((ParkirPerJam) parkir).getIdParkirPerJam());
                        listParkir = getAllParkirData();
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
            }
        } else {
            if (tipeParkir.equals("INAP")) {
                String sql = "UPDATE PARKIR SET nomorKendaraan = ?, tipeParkir = ?, jenisKendaraan = ? WHERE idParkir = ?";
                try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql))
                { 
                    statement.setString(1, platNomor);
                    statement.setString(2, tipeParkir);
                    statement.setString(3, jenisKendaraan);
                    statement.setInt(4, parkir.getNoParkir());
                    statement.executeUpdate();
                    listParkir = getAllParkirData();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                } 
            } else {
                String sql1 = "UPDATE PARKIR SET nomorKendaraan = ?, tipeParkir = ?, jenisKendaraan = ?, idParkirPerJam = ?, idParkirInap = null WHERE idParkir = ?";
                String sql2 = "INSERT INTO PARKIRPERJAM (waktuMasuk) VALUES (?)";
                try (Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
                    PreparedStatement statement1 = connection.prepareStatement(sql1);
                    PreparedStatement statement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS))
                { 
                    statement2.setString(1, waktuMasuk);
                    statement2.executeUpdate();
                    ResultSet generatedKeys = statement2.getGeneratedKeys();
                    if (generatedKeys.next()){
                        int id = generatedKeys.getInt(1);
                        statement1.setString(1, platNomor);
                        statement1.setString(2, tipeParkir);
                        statement1.setString(3, jenisKendaraan);
                        statement1.setInt(4, id);
                        statement1.setInt(5, parkir.getNoParkir());
                        statement1.executeUpdate();
                        deleteParkirInap(((ParkirInap) parkir).getIdParkirInap());
                        listParkir = getAllParkirData();
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
            }
        }     
    }
}
