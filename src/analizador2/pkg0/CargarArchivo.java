/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador2.pkg0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author USER
 */
public class CargarArchivo {
    
    public void cargarEntrada(JFrame frame, JTextArea areaTexto) throws FileNotFoundException, IOException {
        String texto = "";
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(frame);
        File rutaDeArchivo = file.getSelectedFile();
        if (rutaDeArchivo != null) {//
            areaTexto.setText("");
            FileReader archivos = new FileReader(file.getSelectedFile());
            BufferedReader lee = new BufferedReader(archivos);
            while ((texto = lee.readLine()) != null) {
                areaTexto.append(texto + "\n");  
            }
            lee.close();
        }
    }
}
