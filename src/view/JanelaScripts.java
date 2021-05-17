/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import GeradoresScript.GerarArqScript;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JanelaScripts extends javax.swing.JDialog {

    public JanelaScripts(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        ImageIcon icone = new ImageIcon(getClass().getResource("/ico/blueico2.png")); 
        setIconImage(icone.getImage());
        initComponents();
    }
    
    private void miNovoOnClick() {
        taScripts.setText("");
    }
    
    private void miAbrirOnClick() {
        try {
            //criando filtro de arquivos, (nomeNaLista, extensão) pode ser um vetor com vários filtros
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos do tipo (*.txt, *.xml, *.cpn)", "txt", "xml", "cpn");
            
            //Definindo pasta onde devem ser salvos os arquivos do usuário.
            String diretorioBase = System.getProperty("user.home") + "/desktop";
            File dir = new File(diretorioBase);
            
            JFileChooser arquivo = new JFileChooser();
            arquivo.setCurrentDirectory(dir); // setando o caminho para salvar arquivos do usuário.
            arquivo.setAcceptAllFileFilterUsed(false); //aceitar mostrar todos os tipos de arquivos na lista.
            arquivo.addChoosableFileFilter(filter);
            arquivo.setDialogTitle("Procurar Arquivo..."); // titulo da janela
            arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY); //tipo de arquivos mostrados
//            arquivo.setFileFilter(filter); //setando o filtro.
            
            int arq = arquivo.showOpenDialog(this); // atenção abrir-showOpenDialog
            
            if (arq == JFileChooser.APPROVE_OPTION) {
                String nomeArquivo = arquivo.getSelectedFile().getAbsolutePath();
                String retorno = ManipulaArquivoTXT.lerArquivoTXT(nomeArquivo);

                taScripts.setText(retorno);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
    
    private void miSalvarOnClick() {
        try {
            //criando filtro de arquivos, (nomeNaLista, extensão)
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos do tipo (*.txt, *.xml, *.cpn)", "txt", "xml", "cpn"); 
            
            //Definindo pasta onde devem ser salvos os arquivos do usuário.
            String diretorioBase = System.getProperty("user.home") + "/desktop";
            File dir = new File(diretorioBase);
            
            JFileChooser arquivo = new JFileChooser();
            arquivo.setCurrentDirectory(dir); // setando o caminho para salvar arquivos do usuário.
            arquivo.setAcceptAllFileFilterUsed(false); //aceitar mostrar todos os tipos de arquivos na lista.
            arquivo.addChoosableFileFilter(filter);
            arquivo.setDialogTitle("Salvar Arquivo..."); // titulo da janela
            arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY); //tipo de arquivos mostrados
            arquivo.setFileFilter(filter); //setando o filtro.
            
            int arq = arquivo.showSaveDialog(this); // atenção gravar-showSaveDialog
            
            if (arq == JFileChooser.APPROVE_OPTION) {
                String nomeArquivo = arquivo.getSelectedFile().getAbsolutePath();
                String conteudo = taScripts.getText();
                
                if (!nomeArquivo.contains(".")) {
                    nomeArquivo += ".txt";
                }
                
                boolean retorno = ManipulaArquivoTXT.gravarArquivoTXT(nomeArquivo, conteudo);

                if (retorno == true) {
                    JOptionPane.showMessageDialog(this, "Arquivo Salvo com sucesso.");
                }


            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btSave = new javax.swing.JButton();
        btOpen = new javax.swing.JButton();
        btNew = new javax.swing.JButton();
        jBtnExecute = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taScripts = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("..: Scripts :..");
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(25, 22));

        btSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/save.png"))); // NOI18N
        btSave.setContentAreaFilled(false);
        btSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSave.setFocusable(false);
        btSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        btOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/open.png"))); // NOI18N
        btOpen.setContentAreaFilled(false);
        btOpen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btOpen.setFocusable(false);
        btOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOpenActionPerformed(evt);
            }
        });

        btNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/new.png"))); // NOI18N
        btNew.setContentAreaFilled(false);
        btNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNew.setFocusable(false);
        btNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewActionPerformed(evt);
            }
        });

        jBtnExecute.setText("Execute");
        jBtnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btNew, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnExecute)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnExecute)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btNew, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        taScripts.setColumns(20);
        taScripts.setRows(5);
        jScrollPane1.setViewportView(taScripts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(616, 478));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        miSalvarOnClick();
    }//GEN-LAST:event_btSaveActionPerformed

    private void btOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOpenActionPerformed
        miAbrirOnClick();
    }//GEN-LAST:event_btOpenActionPerformed

    private void btNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewActionPerformed
        miNovoOnClick();
    }//GEN-LAST:event_btNewActionPerformed

    private void jBtnExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExecuteActionPerformed
            String script = taScripts.getText();
            JanelaResultados jr = new JanelaResultados(null, true);
            GerarArqScript arqS = new GerarArqScript();
            jr.taResult.setText(arqS.calcRBD(script));
            jr.setVisible(true);

    }//GEN-LAST:event_jBtnExecuteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btNew;
    private javax.swing.JButton btOpen;
    private javax.swing.JButton btSave;
    private javax.swing.JButton jBtnExecute;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea taScripts;
    // End of variables declaration//GEN-END:variables

}
