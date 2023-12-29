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

import java.time.LocalDateTime;

public class PengaturanParkir {
    private int idPengaturan;
    private int kuotaParkirMotor;
    private int kuotaParkirMobil;
    private int tarifParkirMotorPerJam;
    private int tarifParkirMobilPerJam;
    private int tarifParkirMotorPerHari;
    private int tarifParkirMobilPerHari;
    private LocalDateTime waktuAtur;
    private PetugasParkir petugasParkir;

    public PengaturanParkir(int idPengaturan, int kuotaParkirMotor, int kuotaParkirMobil, int tarifParkirMotorPerJam, int tarifParkirMobilPerJam, int tarifParkirMotorPerHari, int tarifParkirMobilPerHari, LocalDateTime waktuAtur, PetugasParkir petugasParkir) {
        this.idPengaturan = idPengaturan;
        this.kuotaParkirMotor = kuotaParkirMotor;
        this.kuotaParkirMobil = kuotaParkirMobil;
        this.tarifParkirMotorPerJam = tarifParkirMotorPerJam;
        this.tarifParkirMobilPerJam = tarifParkirMobilPerJam;
        this.tarifParkirMotorPerHari = tarifParkirMotorPerHari;
        this.tarifParkirMobilPerHari = tarifParkirMobilPerHari;
        this.waktuAtur = waktuAtur;
        this.petugasParkir = petugasParkir;
    }
    
    public PengaturanParkir(int kuotaParkirMotor, int kuotaParkirMobil, int tarifParkirMotorPerJam, int tarifParkirMobilPerJam, int tarifParkirMotorPerHari, int tarifParkirMobilPerHari, LocalDateTime waktuAtur, PetugasParkir petugasParkir) {
        this.kuotaParkirMotor = kuotaParkirMotor;
        this.kuotaParkirMobil = kuotaParkirMobil;
        this.tarifParkirMotorPerJam = tarifParkirMotorPerJam;
        this.tarifParkirMobilPerJam = tarifParkirMobilPerJam;
        this.tarifParkirMotorPerHari = tarifParkirMotorPerHari;
        this.tarifParkirMobilPerHari = tarifParkirMobilPerHari;
        this.waktuAtur = waktuAtur;
        this.petugasParkir = petugasParkir;
    }

    public void setIdPengaturan(int idPengaturan) {
        this.idPengaturan = idPengaturan;
    }

    public void setKuotaParkirMotor(int kuotaParkirMotor) {
        this.kuotaParkirMotor = kuotaParkirMotor;
    }

    public void setKuotaParkirMobil(int kuotaParkirMobil) {
        this.kuotaParkirMobil = kuotaParkirMobil;
    }

    public void setTarifParkirMotorPerJam(int tarifParkirMotorPerJam) {
        this.tarifParkirMotorPerJam = tarifParkirMotorPerJam;
    }

    public void setTarifParkirMobilPerJam(int tarifParkirMobilPerJam) {
        this.tarifParkirMobilPerJam = tarifParkirMobilPerJam;
    }

    public void setTarifParkirMotorPerHari(int tarifParkirMotorPerHari) {
        this.tarifParkirMotorPerHari = tarifParkirMotorPerHari;
    }

    public void setTarifParkirMobilPerHari(int tarifParkirMobilPerHari) {
        this.tarifParkirMobilPerHari = tarifParkirMobilPerHari;
    }
    
    public void setWaktuAtur(LocalDateTime waktuAtur) {
        this.waktuAtur = waktuAtur;
    }

    public void setPetugasParkir(PetugasParkir petugasParkir) {
        this.petugasParkir = petugasParkir;
    }

    public PetugasParkir getPetugasParkir() {
        return petugasParkir;
    }
    
    public LocalDateTime getWaktuAtur() {
        return waktuAtur;
    }

    public int getIdPengaturan() {
        return idPengaturan;
    }

    public int getKuotaParkirMotor() {
        return kuotaParkirMotor;
    }

    public int getKuotaParkirMobil() {
        return kuotaParkirMobil;
    }

    public int getTarifParkirMotorPerJam() {
        return tarifParkirMotorPerJam;
    }

    public int getTarifParkirMobilPerJam() {
        return tarifParkirMobilPerJam;
    }

    public int getTarifParkirMotorPerHari() {
        return tarifParkirMotorPerHari;
    }

    public int getTarifParkirMobilPerHari() {
        return tarifParkirMobilPerHari;
    }
   
}
