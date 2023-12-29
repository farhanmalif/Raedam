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
import java.time.Duration;

public class ParkirPerJam extends Parkir implements BisaParkir {
    private int idParkirPerJam;
    private LocalDateTime waktuMasuk;
    private LocalDateTime waktuKeluar;
    
    //Constructor yang dipanggil untuk membuat objek ketika kendaraan akan PARKIR
    public ParkirPerJam(String tipeParkir, String statusParkir, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan){
        super(tipeParkir, statusParkir, jenisKendaraan, platNomor, pengaturan);
    }
    
    //Constructor yang dipanggil untuk membuat objek parkir yang statusnya sudah SELESAI 
    public ParkirPerJam(int noParkir, String tipeParkir, String statusParkir, LocalDateTime waktuMasuk, LocalDateTime waktuKeluar, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan, int idParkirPerJam) {
        super(noParkir, tipeParkir, statusParkir, jenisKendaraan, platNomor, pengaturan);
        this.waktuMasuk = waktuMasuk;
        this.waktuKeluar = waktuKeluar;
        this.idParkirPerJam = idParkirPerJam;
    }
    
    //Constructor yang dipanggil untuk membuat objek parkir yang statusnya PARKIR
    public ParkirPerJam(int noParkir, String tipeParkir, String statusParkir, LocalDateTime waktuMasuk, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan, int idParkirPerJam) {
        super(noParkir, tipeParkir, statusParkir, jenisKendaraan, platNomor, pengaturan);
        this.waktuMasuk = waktuMasuk;
        this.idParkirPerJam = idParkirPerJam;
    }
    
    public int getIdParkirPerJam() {
        return idParkirPerJam;
    }

    public void setIdParkirPerJam(int idParkirPerJam) {
        this.idParkirPerJam = idParkirPerJam;
    }
    
    public void setWaktuMasuk(LocalDateTime waktuMasuk){
        this.waktuMasuk = waktuMasuk;
    }
    
    public void setWaktuKeluar(LocalDateTime waktuKeluar){
        this.waktuKeluar = waktuKeluar;
    }
    
    public LocalDateTime getWaktuMasuk(){
        return waktuMasuk;
    }
    
    public LocalDateTime getWaktuKeluar(){
        return waktuKeluar;
    }
    
    @Override
    public void masukParkir(String tipeParkir){
        this.waktuMasuk = LocalDateTime.now();
        this.waktuKeluar = null;
        setStatusParkir("PARKIR");
        setTipeParkir(tipeParkir);
    }
    
    @Override
    public void keluarParkir(){
        this.waktuKeluar = LocalDateTime.now();
        setStatusParkir("SELESAI");
    }
    
    @Override
    public int hitungDurasiParkir(){
        Duration durasi = Duration.between(waktuMasuk, waktuKeluar);
        int detikParkir = (int) durasi.getSeconds();
        int jamParkir = detikParkir / 3600;
        if (detikParkir % 3600 != 0) {
            jamParkir++;
        }
        return jamParkir; 
    }
    
}
