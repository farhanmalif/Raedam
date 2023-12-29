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

public class Pembayaran {
    private int idPembayaran;
    private int totalBayar;
    private LocalDateTime waktuBayar;
    private Parkir parkir;
    private PetugasParkir petugasparkir;
    
    public Pembayaran(int idPembayaran, int totalBayar, LocalDateTime waktuBayar, Parkir parkir, PetugasParkir petugasParkir) {
        this.idPembayaran = idPembayaran;
        this.totalBayar = totalBayar;
        this.waktuBayar = waktuBayar;
        this.parkir = parkir;
        this.petugasparkir = petugasParkir;
    }
    
    public Pembayaran(int totalBayar, LocalDateTime waktuBayar, Parkir parkir, PetugasParkir petugasParkir) {
        this.totalBayar = totalBayar;
        this.waktuBayar = waktuBayar;
        this.parkir = parkir;
        this.petugasparkir = petugasParkir;
    }
    
    
    public int getIdPembayaran() {
        return this.idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public int getTotalBayar() {
        return this.totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }

    public LocalDateTime getWaktuBayar() {
        return this.waktuBayar;
    }

    public void setWaktuBayar(LocalDateTime waktuBayar) {
        this.waktuBayar = waktuBayar;
    }

    public Parkir getParkir() {
        return parkir;
    }

    public void setParkir(Parkir Parkir) {
        this.parkir = Parkir;
    }

    public PetugasParkir getPetugasparkir() {
        return petugasparkir;
    }

    public void setPetugasparkir(PetugasParkir petugasparkir) {
        this.petugasparkir = petugasparkir;
    }
   

}
