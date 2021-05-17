package view;

public class JanelaPropriedades extends javax.swing.JDialog {

    private Componente comp;
    String estadoListBox;
    private static int ups5, ups250, sdt, subP, powerS = 0;
    
    public JanelaPropriedades(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    //------------------------------------------------------------------------------
    
    public void iniciaForm(Componente comp) {
        this.comp = comp;
        
        tfTitulo.setText(comp.getNomeComp());
        tfMttr.setText(String.valueOf(comp.getMttr()));
        tfMttf.setText(String.valueOf(comp.getMttf()));
        
        tfPoderMax.setText(String.valueOf(comp.getPoderMax()));
        tfEficiencia.setText(String.valueOf(comp.getEficiencia()));
        tfPreco.setText(String.valueOf(comp.getPreco()));
        tfEnergia.setText(String.valueOf(comp.getEnergia()));
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfTitulo = new javax.swing.JTextField();
        tfMttr = new javax.swing.JTextField();
        tfMttf = new javax.swing.JTextField();
        btConfirmar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tfPoderMax = new javax.swing.JTextField();
        tfEficiencia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfPreco = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfEnergia = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        cbComponentes = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Title:");

        jLabel2.setText("MTTR:");

        jLabel3.setText("MTTF:");

        tfMttr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        tfMttf.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btConfirmar.setText("OK");
        btConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Propriedades");

        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Max. Power (kW):");

        tfPoderMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        tfEficiencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setText("Efficiency (%):");

        tfPreco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setText("Retail Price:");

        jLabel8.setText("Embedded Energy (GJ):");

        tfEnergia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Parametros EFM");

        cbComponentes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UPS_5kVA", "UPS_250kVA", "STS", "ATS", "SDTransformer", "Subpanel", "PowerStrip", "GeneratorGroup", "GeneratorGroup500", "ACSource" }));
        cbComponentes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbComponentesItemStateChanged(evt);
            }
        });
        cbComponentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbComponentesActionPerformed(evt);
            }
        });

        jLabel10.setText("Type:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfMttr, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfMttf, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel10))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfPreco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                        .addComponent(tfEficiencia)
                                        .addComponent(tfPoderMax)
                                        .addComponent(tfEnergia, javax.swing.GroupLayout.Alignment.TRAILING))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btCancelar)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfMttr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfMttf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfPoderMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfEficiencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfEnergia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConfirmar)
                    .addComponent(btCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(307, 429));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmarActionPerformed

        String titulo = tfTitulo.getText();
        double mttr = Double.parseDouble(tfMttr.getText());
        double mttf = Double.parseDouble(tfMttf.getText());
        
        double poderMax = Double.parseDouble(tfPoderMax.getText());
        double eficiencia = Double.parseDouble(tfEficiencia.getText());
        double preco = Double.parseDouble(tfPreco.getText());
        double energia = Double.parseDouble(tfEnergia.getText());

        comp.setNomeComp(titulo);
        comp.setMttr(mttr);
        comp.setMttf(mttf);
        
        comp.setTipoComp(String.valueOf(cbComponentes.getSelectedItem()));
        comp.setPoderMax(poderMax);
        comp.setEficiencia(eficiencia);
        comp.setPreco(preco);
        comp.setEnergia(energia);

//        if (!titulo.equalsIgnoreCase("")){
//            comp.setNomeComp(titulo);
//        }

        setVisible(false);
    }//GEN-LAST:event_btConfirmarActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btCancelarActionPerformed

    private void cbComponentesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbComponentesItemStateChanged
        // TODO add your handling code here:
        estadoListBox = String.valueOf(cbComponentes.getSelectedItem());
        preencherCampos(estadoListBox);
    }//GEN-LAST:event_cbComponentesItemStateChanged

    private void cbComponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbComponentesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbComponentesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btConfirmar;
    private javax.swing.JComboBox<String> cbComponentes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tfEficiencia;
    private javax.swing.JTextField tfEnergia;
    private javax.swing.JTextField tfMttf;
    private javax.swing.JTextField tfMttr;
    private javax.swing.JTextField tfPoderMax;
    private javax.swing.JTextField tfPreco;
    private javax.swing.JTextField tfTitulo;
    // End of variables declaration//GEN-END:variables

    
    public void preencherCampos(String nomeComponente) {
        
        if (nomeComponente.equals("UPS_5kVA")) {
            tfTitulo.setText("UPS_5kVA");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(95.3));
            tfPreco.setText(String.valueOf(3600.0));
            tfEnergia.setText(String.valueOf(3.1392));
            tfMttf.setText(String.valueOf(250000.0 ));
            tfMttr.setText(String.valueOf(8.0));            
        }else if (nomeComponente.equals("UPS_250kVA")) {
            tfTitulo.setText("UPS_250kVA");
            tfPoderMax.setText(String.valueOf(250.0));
            tfEficiencia.setText(String.valueOf(95.3));
            tfPreco.setText(String.valueOf(60000.0));
            tfEnergia.setText(String.valueOf(61.03986048));
            tfMttf.setText(String.valueOf(250000.0 ));
            tfMttr.setText(String.valueOf(8.0));   
        }else if (nomeComponente.equals("STS")) {
            tfTitulo.setText("STS");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(800.0));
            tfEnergia.setText(String.valueOf(0.35928));
            tfMttf.setText(String.valueOf(240384.62 ));
            tfMttr.setText(String.valueOf(6.0));
        }else if (nomeComponente.equals("ATS")) {
            tfTitulo.setText("ATS");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(800.0));
            tfEnergia.setText(String.valueOf(0.35928));
            tfMttf.setText(String.valueOf(500000.00 ));
            tfMttr.setText(String.valueOf(0.33));
        }else if (nomeComponente.equals("SDTransformer")) {
            tfTitulo.setText("SDTransformer");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(98.5));
            tfPreco.setText(String.valueOf(550.0));
            tfEnergia.setText(String.valueOf(0.35928));
            tfMttf.setText(String.valueOf(1412908.33));
            tfMttr.setText(String.valueOf(156.01));
        }else if (nomeComponente.equals("Subpanel")) {
            tfTitulo.setText("Subpanel");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.9));
            tfPreco.setText(String.valueOf(200.0));
            tfEnergia.setText(String.valueOf(0.4284));
            tfMttf.setText(String.valueOf(1520000.00));
            tfMttr.setText(String.valueOf(2.4));       
        }else if (nomeComponente.equals("PowerStrip")) {
            tfTitulo.setText("PowerStrip");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(200.0));
            tfEnergia.setText(String.valueOf(0.35568));
            tfMttf.setText(String.valueOf(11511175.63));
            tfMttr.setText(String.valueOf(3.8));   
        }else if (nomeComponente.equals("ACSource")) {
            tfTitulo.setText("ACSource");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(200.0));
            tfEnergia.setText(String.valueOf(0.35568));
            tfMttf.setText(String.valueOf(11511175.63));
            tfMttr.setText(String.valueOf(3.8));   
        }else if (nomeComponente.equals("GeneratorGroup")) {
            tfTitulo.setText("GeneratorGroup");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(200.0));
            tfEnergia.setText(String.valueOf(0.35568));
            tfMttf.setText(String.valueOf(11511175.63));
            tfMttr.setText(String.valueOf(3.8));   
        }else if (nomeComponente.equals("GeneratorGroup500")) {
            tfTitulo.setText("GeneratorGroup500");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(200.0));
            tfEnergia.setText(String.valueOf(0.35568));
            tfMttf.setText(String.valueOf(11511175.63));
            tfMttr.setText(String.valueOf(3.8));   
        }else if (nomeComponente.equals("JunctionBox")) {
            tfTitulo.setText("JunctionBox");
            tfPoderMax.setText(String.valueOf(5.0));
            tfEficiencia.setText(String.valueOf(99.5));
            tfPreco.setText(String.valueOf(200.0));
            tfEnergia.setText(String.valueOf(0.35568));
            tfMttf.setText(String.valueOf(11511175.63));
            tfMttr.setText(String.valueOf(3.8));   
        }
    }
}
