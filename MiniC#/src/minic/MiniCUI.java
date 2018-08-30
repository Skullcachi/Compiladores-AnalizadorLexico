/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author SERGIO-PC
 */
public class MiniCUI extends javax.swing.JFrame {

    /**
     * Creates new form MiniCUI
     */
    public MiniCUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("Archivo sin seleccionar");

        jButton1.setText("Cargar Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String PATH = "C:/Users/cachi/Documents/Landivar/Compiladores/Compiladores-AnalizadorLexico/MiniC#/";
    String resultados = "";
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        BufferedReader reader;
        String currentLine;
        BufferedWriter writer;
        BufferedWriter writerErrores;
        FileFilter filter = new FileNameExtensionFilter("C# Files", "txt");
        resultados = "";
        jFileChooser1.setFileFilter(filter);
        try {
            jFileChooser1.setCurrentDirectory(new File(PATH + "archivo_entrada.txt"));
            int returnVal = jFileChooser1.showOpenDialog(jFileChooser1);
            if (returnVal == jFileChooser1.APPROVE_OPTION)
            {
                File file = jFileChooser1.getSelectedFile();
                //This is where a real application would open the file.
                jTextArea1.append("Selected file: " + file.getAbsolutePath());
                ProbarLexerFile(file);
                if (hayError)
                {

                    writerErrores = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH + "errores_encontrados.txt"), "utf-8"));
                    for (int i = 0; i < listaErrores.size(); i++) {
                        writerErrores.write(listaErrores.get(i));
                    }
                    writerErrores.close();
                    
                    
                }
                else
                {
                    String extensionArchivoSalida = file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length());
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH + file.getName().replaceAll("entrada" + extensionArchivoSalida, "salida.out")), "utf-8"));
                    reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                    /*while ((currentLine = reader.readLine()) != null)
                    {                        
                        writer.write(currentLine);
                    }*/
                    writer.write(resultados);
                    writer.close();
                    reader.close();
                    
                    writerErrores = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH + "errores_encontrados.txt"), "utf-8"));
                    for (int i = 0; i < listaErrores.size(); i++) {
                        writerErrores.write(listaErrores.get(i));
                    }
                    writerErrores.close();
                }
            }
            else
            {
                jTextArea1.append("No file has been selected.\n");
            }

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MiniCUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiniCUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiniCUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiniCUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MiniCUI().setVisible(true);
            }
        });
    }
    
    
    public boolean hayError = false;
    
    public ArrayList<String> listaErrores = new ArrayList<String>();
    
    
    public void ProbarLexerFile(File filetoread) throws IOException{
        Reader reader;
        reader = new BufferedReader(new FileReader(filetoread.getAbsolutePath()));
        Lexic.Yylex lexer = new Lexic.Yylex(reader);
        
        while (true){
            String token = lexer.yylex();
            if (token == null){
                resultados = resultados + "EOF";
                jTextArea1.setText(resultados);
                hayError = false;
                return;
            }
            else
            {
                if (token.contains("Lexical error:"))
                {                    
                    resultados = resultados + "\n*** ERROR: line: " + lexer.line + ". \n*** Unrecognized char: '" + lexer.lexeme + "'.\n\n";
                    hayError = true;
                    listaErrores.add("\n*** ERROR: line: " + lexer.line + ". \n*** Unrecognized char: '" + lexer.lexeme + "'.\n\n");
                }
                else 
                {                    
                    resultados +=  "\n" + token + ".\n";
                }
            }
        }
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
