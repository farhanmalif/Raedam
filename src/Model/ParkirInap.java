
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ParkirInap extends Parkir implements BisaParkir {
    private int idParkirInap;
    private LocalDate tanggalMasuk;
    private LocalDate tanggalKeluar;
    

    //Constructor yang dipanggil untuk membuat objek ketika kendaraan akan PARKIR
    public ParkirInap(String tipeParkir, String statusParkir, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan){
        super(tipeParkir, statusParkir, jenisKendaraan, platNomor, pengaturan);
    }
    
    //Constructor yang dipanggil untuk membuat objek yang statusnya sedang PARKIR
    public ParkirInap(int noParkir, String tipeParkir, String statusParkir, LocalDate tanggalMasuk, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan, int idParkirInap) {
        super(noParkir, tipeParkir, statusParkir, jenisKendaraan, platNomor, pengaturan);
        this.tanggalMasuk = tanggalMasuk;
        this.idParkirInap = idParkirInap;
    }
    
    //Constructor yang dipanggil untuk membuat objek parkir yang statusnya sudah SELESAI 
    public ParkirInap(int noParkir, String tipeParkir, String statusParkir, LocalDate tanggalMasuk, LocalDate tanggalKeluar, String jenisKendaraan, String platNomor, PengaturanParkir pengaturan, int idParkirInap) {
        super(noParkir, tipeParkir, statusParkir, jenisKendaraan, platNomor, pengaturan);
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKeluar = tanggalKeluar;
        this.idParkirInap = idParkirInap;
    }
    
    public void setTanggalMasuk(LocalDate tanggalMasuk){
        this.tanggalMasuk = tanggalMasuk;
    }
    
    public void setTanggalKeluar(LocalDate tanggalKeluar){
        this.tanggalKeluar = tanggalKeluar;
    }
    
    public LocalDate getTanggalMasuk(){
        return tanggalMasuk;
    }
    
    public LocalDate getTanggalKeluar(){
        return tanggalKeluar;
    }

    public int getIdParkirInap() {
        return idParkirInap;
    }

    public void setIdParkirInap(int idParkirInap) {
        this.idParkirInap = idParkirInap;
    }
        
    @Override
    public int hitungDurasiParkir(){
        if (tanggalMasuk.compareTo(tanggalKeluar) == 0){
            return 1;
        } else {
            int durasiParkir = (int)(ChronoUnit.DAYS.between(tanggalMasuk, tanggalKeluar));
            return durasiParkir;
        }  
    }
    
    @Override
    public void masukParkir(String tipeParkir){
        this.tanggalMasuk = LocalDate.now();
        this.tanggalKeluar = null;
        setStatusParkir("PARKIR");
        setTipeParkir(tipeParkir);
    }
    
    @Override
    public void keluarParkir(){
        this.tanggalKeluar = LocalDate.now();
        setStatusParkir("SELESAI");
    }
    
}
