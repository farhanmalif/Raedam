/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

/**
 *
 * @author Kemal Crisannaufal
 */
import View.Welcome;
import Model.PetugasParkir;

public class sesi {
    public static String username;
    public static PetugasParkir petugas;
    
    public static void main(String[] args){
        new Welcome().setVisible(true);
    }
}
