/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Kemal Crisannaufal
 */

public abstract class Parkir 
{
    private int noParkir;
    private String tipeParkir;
    private String jenisKendaraan;
    private String platNomor;
    private String statusParkir;
    private PengaturanParkir pengaturan;
    
    public Parkir(int noParkir, String tipeParkir, String statusParkir, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan) {
        this.tipeParkir = tipeParkir;
        this.statusParkir = statusParkir;
        this.noParkir = noParkir;
        this.jenisKendaraan = jenisKendaraan;
        this.platNomor = platNomor;
        this.pengaturan = pengaturan;
    }
    
    public Parkir(String tipeParkir, String statusParkir, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan) {
        this.tipeParkir = tipeParkir;
        this.statusParkir = statusParkir;
        this.jenisKendaraan = jenisKendaraan;
        this.platNomor = platNomor;
        this.pengaturan = pengaturan;
    }
    
    public void setNoParkir(int noParkir) {
        this.noParkir = noParkir;
    }
    
    public void setTipeParkir(String tipeParkir){
        this.tipeParkir = tipeParkir;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public void setStatusParkir(String statusParkir) {
        this.statusParkir = statusParkir;
    }

    public void setPengaturan(PengaturanParkir pengaturan) {
        this.pengaturan = pengaturan;
    }

    public int getNoParkir() {
        return noParkir;
    }
    
    public String getTipeParkir(){
        return tipeParkir;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public String getPlatNomor() {
        return platNomor;
    }
    
    public String getStatusParkir() {
        return statusParkir;
    }

    public PengaturanParkir getPengaturan() {
        return pengaturan;
    }
    
    //abstract method untuk di-override di ParkirPerJam dan ParkirInap
    
    public abstract int hitungDurasiParkir();
}
