/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Parkir;
import Model.ParkirInap;
import Model.ParkirPerJam;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kemal Crisannaufal
 */
public class Komponen {
    
    static DefaultTableModel CreateTableModelParkir(ArrayList<Parkir> listParkir) {
        DefaultTableModel newModel = new DefaultTableModel();
            newModel.addColumn("Tipe Parkir");
            newModel.addColumn("Plat Nomor");
            newModel.addColumn("Jenis Kendaraan");
            newModel.addColumn("Waktu Masuk");
            newModel.addColumn("Waktu Keluar");
            newModel.addColumn("Status Parkir");

            Object[][] objek = new Object[listParkir.size()][6];
                for (int i = 0; i < listParkir.size(); i++){
                        for (int j = 0; j <= 5; j++){
                            objek[i][0] = listParkir.get(i).getTipeParkir();
                            objek[i][1] = listParkir.get(i).getPlatNomor();
                            objek[i][2] = listParkir.get(i).getJenisKendaraan();
                            objek[i][5] = listParkir.get(i).getStatusParkir();
                            if (j == 3) {
                                if (listParkir.get(i) instanceof ParkirPerJam) {
                                    objek[i][j] = ((ParkirPerJam) listParkir.get(i)).getWaktuMasuk().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss"));
                                } else {
                                    objek[i][j] = ((ParkirInap) listParkir.get(i)).getTanggalMasuk();
                                }
                            } else if (j == 4) {
                               if (listParkir.get(i).getStatusParkir().equalsIgnoreCase("SELESAI")) {
                                    if (listParkir.get(i) instanceof ParkirPerJam) {
                                        objek[i][j] = ((ParkirPerJam) listParkir.get(i)).getWaktuKeluar().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss"));
                                    } else 
                                        objek[i][j] = ((ParkirInap) listParkir.get(i)).getTanggalKeluar();
                                } else {
                                    objek[i][j] = "--";
                                }
                            }

                        }
                    }
                    
            int i = 0;
            for (Object[] rowData : objek) {
                newModel.addRow(rowData);
                i++;
            }
                    
            while (i < 18){
                newModel.addRow(new Object[]{null,null,null,null,null,null});
                    i++;
            }
            return newModel;
    }
    
}
