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

public class PetugasParkir {
    private int idPetugas;
    private String namaPetugas;
    private String username;
    private String password;
    private String nomorHandphone;
    private String emailPetugas;

    public PetugasParkir(String namaPetugas, String nomorHandphone, String emailPetugas, String username, String password){
        this.namaPetugas = namaPetugas;
        this.nomorHandphone = nomorHandphone;
        this.emailPetugas = emailPetugas;
        this.username = username;
        this.password = password;
    }
    
    public PetugasParkir(int idPetugas, String namaPetugas, String nomorHandphone, String emailPetugas, String username, String password){
        this.idPetugas = idPetugas;
        this.namaPetugas = namaPetugas;
        this.nomorHandphone = nomorHandphone;
        this.emailPetugas = emailPetugas;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
      
    
    public void setIdPetugas(int idPetugas){
        this.idPetugas = idPetugas;
    }
    
    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public void setNomorHandphone(String nomorHandphone) {
        this.nomorHandphone = nomorHandphone;
    }

    public void setEmailPetugas(String emailPetugas) {
        this.emailPetugas = emailPetugas;
    }
    
     public int getIdPetugas() {
        return idPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public String getNomorHandphone() {
        return nomorHandphone;
    }

    public String getEmailPetugas() {
        return emailPetugas;
    }
    
}
