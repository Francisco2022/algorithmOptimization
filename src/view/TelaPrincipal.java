package view;

//setContentAreaField - usado para desabilitar o contorno do botão.

import Genes.Cromossomo;
import Genes.GeradorAleatorio;
import GeradoresScript.GerarArqScript;
import GeradoresScript.GerarEFM;
import GeradoresScript.GerarManutCorret;
import GeradoresScript.GerarManutPrev;
import GeradoresScript.GerarRBD;
import GeradoresScript.GerarSPN;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.mxgraph.util.mxUtils;
import java.util.ArrayList;
import newCpn.GerarCPN;


public class TelaPrincipal extends JFrame { //implements ActionListener, MouseListener, MouseMotionListener {

    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private final JPanel mxPane;
    private static mxCell cell;
    
    private GerarSPN gSPN = new GerarSPN();
    private GerarRBD gRBD = new GerarRBD();
    private GerarEFM gEFM = new GerarEFM();
    private GerarArqScript arqS = new GerarArqScript();
    
    private GeradorAleatorio gen = new GeradorAleatorio();
    private Cromossomo[] croms;
    
    private static Point mousePos;
    private final AcoesGrafo acGrafo;
    protected static int lim = 0;
    protected JColorChooser escolherCor;
    public Color corEscolhida = Color.BLACK;
    private mxEvent mxEvent;
    public String resultado;
    
//------------------------------------------------------------------------------    
    public JColorChooser getEscolherCor() {
        return escolherCor;
    }

    public void setEscolherCor(JColorChooser escolherCor) {
        this.escolherCor = escolherCor;
    }

    public Color getCorEscolhida() {
        return corEscolhida;
    }

    public void setCorEscolhida(Color corEscolhida) {
        this.corEscolhida = corEscolhida;
    }

//------------------------------------------------------------------------------    
    public TelaPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        setSize(new Dimension(800, 600));
//        graph = new mxGraph();
//        parent = graph.getDefaultParent();
//        graphComponent = new mxGraphComponent(graph);
        acGrafo = new AcoesGrafo();
        graphComponent = acGrafo.getGraphComponent();
        graph = acGrafo.getGraph();
        mxEvent = new mxEvent();
        // o sistema entende que o icone selecionado será o icone que 
        // aparecerá ao lado do titulo do sistema e também na barra de tarefas.
        ImageIcon icone = new ImageIcon(getClass().getResource("/ico/ico_System.png")); 
        setIconImage(icone.getImage());
        btZoomOut.setEnabled(false);
        resultado = "";
        
        
        //normalmente usados em editores.
        graphComponent.setPageVisible(false); // habilita uma página
        graphComponent.setPageShadowColor(Color.black);
//        graphComponent.setPageScale(0.7);
        graphComponent.setPageBreaksVisible(true);
//        graphComponent.setPageBreakColor(Color.darkGray);
//        graphComponent.setPageFormat(0); //0 - LANDSCAPE, 1 - PORTRAIT, 2 - REVERSE_LANDSCAPE
        graphComponent.setGridVisible(true); // habilita grades na página
        graphComponent.setToolTips(true); // verificando...
//        graphComponent.setToolTipText("Título do Quadro: "); // verificando...
//        graphComponent.getConnectionHandler().setCreateTarget(true); // verificando...
        
        // Carrega a folha de estilo padrão de um arquivo externo 
//        mxCodec codec = new mxCodec();
//        Document doc = mxUtils.loadDocument(TelaPrincipal.class.getResource("./default-style.xml").toString());
//        codec.decode(doc.getDocumentElement(), graph.getStylesheet());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mxPane = new JPanel();
        mxPane.setBorder(new EmptyBorder(1, 1, 1, 1));
        mxPane.setLayout(new BorderLayout(0, 0));
        // Sets the background to white
        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(Color.WHITE);
        addPopup(graphComponent.getGraphControl(), popup);
        
        mxPane.add(graphComponent, BorderLayout.CENTER);
        mxPane.add(pnMenuBar, BorderLayout.NORTH);
        mxPane.add(pnStatus, BorderLayout.SOUTH);
        setContentPane(mxPane);
        
//        graph.setMultigraph(false); // (false) não aceita duas ligações no mesmo sentido.
//        graph.setAllowDanglingEdges(false); // (false) só permite ligar grafo a grafo, não deixa linha solta.
        graphComponent.setConnectable(true); // (false) não permite ligação apartir dos grafos.
        graphComponent.setToolTips(true); // mostra informações do objeto// mostra informações do objeto
        
        new mxRubberband(graphComponent); // permite selecionar vários objetos com um slayer semi transparente. 
        new mxKeyboardHandler(graphComponent); //apagar na tecla Delete do teclado.
            
        
        graphComponent.getGraphControl().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                mousePos = graphComponent.getMousePosition();
                cell = (mxCell) graphComponent.getCellAt(mousePos.x, mousePos.y);
//                cell = (mxCell) graphComponent.getCellAt(me.getX(), me.getY());
                lblX.setText(mousePos.x + "");
                lblY.setText(mousePos.y + "");
            }
        });

        graphComponent.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent ev) {
                if (ev.getWheelRotation() < 0) {
                    graphComponent.zoomIn();
                } else {
                    graphComponent.zoomOut();
                }
            }
        });
        
    }
    
//------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popup = new javax.swing.JPopupMenu();
        popInsert = new javax.swing.JMenuItem();
        popDelete = new javax.swing.JMenuItem();
        popInfo = new javax.swing.JMenuItem();
        popProp = new javax.swing.JMenuItem();
        pnStatus = new javax.swing.JPanel();
        JLabel5 = new javax.swing.JLabel();
        lblOpAtiva = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblX = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblY = new javax.swing.JLabel();
        pnMenuBar = new javax.swing.JPanel();
        btOpen = new javax.swing.JButton();
        btNew = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btBack = new javax.swing.JButton();
        btForward = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btCpn = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btView = new javax.swing.JButton();
        btGrid = new javax.swing.JButton();
        btZoomOut = new javax.swing.JButton();
        btZoomIn = new javax.swing.JButton();
        btBackground = new javax.swing.JButton();
        btRbd = new javax.swing.JButton();
        btSpn = new javax.swing.JButton();
        btEfm = new javax.swing.JButton();
        btCorret = new javax.swing.JButton();
        btPrevent = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuSalvar = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuSair = new javax.swing.JMenuItem();
        menuCalcular = new javax.swing.JMenu();
        menuCalcRBD = new javax.swing.JMenuItem();
        menuCalcSPN = new javax.swing.JMenuItem();
        menuCalcEFM = new javax.swing.JMenuItem();
        menuCalcCPN = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menuAlgGen = new javax.swing.JMenuItem();
        menuAlgDif = new javax.swing.JMenuItem();
        menuNSGA2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuCalcManutCorret = new javax.swing.JMenuItem();
        menuCalcManutPrev = new javax.swing.JMenuItem();

        popInsert.setText("Inserir Componente");
        popInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popInsertActionPerformed(evt);
            }
        });
        popup.add(popInsert);

        popDelete.setText("Apagar Componente");
        popDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popDeleteActionPerformed(evt);
            }
        });
        popup.add(popDelete);

        popInfo.setText("Informações");
        popInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popInfoActionPerformed(evt);
            }
        });
        popup.add(popInfo);

        popProp.setText("Propriedades");
        popProp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popPropActionPerformed(evt);
            }
        });
        popup.add(popProp);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Iniciação Científica");
        setFocusable(false);
        setName("Form"); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        pnStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        JLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabel5.setText("Op ativa:");

        lblOpAtiva.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOpAtiva.setText("<nenhuma>");

        jLabel1.setText("X:");

        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("0");

        jLabel2.setText("Y:");

        lblY.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblY.setText("0");

        javax.swing.GroupLayout pnStatusLayout = new javax.swing.GroupLayout(pnStatus);
        pnStatus.setLayout(pnStatusLayout);
        pnStatusLayout.setHorizontalGroup(
            pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(lblX, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(lblY, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOpAtiva)
                .addContainerGap())
        );

        pnStatusLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblX, lblY});

        pnStatusLayout.setVerticalGroup(
            pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(JLabel5)
                .addComponent(lblOpAtiva))
            .addGroup(pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(lblX)
                .addComponent(jLabel2)
                .addComponent(lblY))
        );

        pnMenuBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnMenuBar.setPreferredSize(new java.awt.Dimension(174, 30));

        btOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/open.png"))); // NOI18N
        btOpen.setBorder(null);
        btOpen.setBorderPainted(false);
        btOpen.setContentAreaFilled(false);
        btOpen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btOpen.setDisabledIcon(null);
        btOpen.setFocusable(false);
        btOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btOpen.setMinimumSize(new java.awt.Dimension(24, 24));
        btOpen.setPreferredSize(new java.awt.Dimension(24, 24));
        btOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOpenActionPerformed(evt);
            }
        });

        btNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/new.png"))); // NOI18N
        btNew.setBorder(null);
        btNew.setBorderPainted(false);
        btNew.setContentAreaFilled(false);
        btNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNew.setDisabledIcon(null);
        btNew.setFocusable(false);
        btNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btNew.setMinimumSize(new java.awt.Dimension(24, 24));
        btNew.setPreferredSize(new java.awt.Dimension(24, 25));
        btNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewActionPerformed(evt);
            }
        });

        btSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/save.png"))); // NOI18N
        btSave.setBorder(null);
        btSave.setBorderPainted(false);
        btSave.setContentAreaFilled(false);
        btSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSave.setDisabledIcon(null);
        btSave.setFocusable(false);
        btSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSave.setMinimumSize(new java.awt.Dimension(24, 24));
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/redo.png"))); // NOI18N
        btBack.setBorder(null);
        btBack.setBorderPainted(false);
        btBack.setContentAreaFilled(false);
        btBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btBack.setFocusable(false);
        btBack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btBack.setMinimumSize(new java.awt.Dimension(24, 24));
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });

        btForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/undo.png"))); // NOI18N
        btForward.setBorder(null);
        btForward.setBorderPainted(false);
        btForward.setContentAreaFilled(false);
        btForward.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btForward.setFocusable(false);
        btForward.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btForward.setMinimumSize(new java.awt.Dimension(24, 24));
        btForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btForwardActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btCpn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/cpn5.png"))); // NOI18N
        btCpn.setBorder(null);
        btCpn.setBorderPainted(false);
        btCpn.setContentAreaFilled(false);
        btCpn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCpn.setFocusable(false);
        btCpn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCpn.setMinimumSize(new java.awt.Dimension(24, 24));
        btCpn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCpnActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/eyesoff.png"))); // NOI18N
        btView.setBorder(null);
        btView.setBorderPainted(false);
        btView.setContentAreaFilled(false);
        btView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btView.setFocusable(false);
        btView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btView.setMinimumSize(new java.awt.Dimension(24, 24));
        btView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewActionPerformed(evt);
            }
        });

        btGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/grid3.png"))); // NOI18N
        btGrid.setBorder(null);
        btGrid.setBorderPainted(false);
        btGrid.setContentAreaFilled(false);
        btGrid.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btGrid.setFocusable(false);
        btGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGrid.setMinimumSize(new java.awt.Dimension(24, 24));
        btGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGridActionPerformed(evt);
            }
        });

        btZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/zoomout.png"))); // NOI18N
        btZoomOut.setBorder(null);
        btZoomOut.setBorderPainted(false);
        btZoomOut.setContentAreaFilled(false);
        btZoomOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btZoomOut.setFocusable(false);
        btZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btZoomOut.setMinimumSize(new java.awt.Dimension(24, 24));
        btZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btZoomOutActionPerformed(evt);
            }
        });

        btZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/zoomin.png"))); // NOI18N
        btZoomIn.setBorder(null);
        btZoomIn.setBorderPainted(false);
        btZoomIn.setContentAreaFilled(false);
        btZoomIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btZoomIn.setFocusable(false);
        btZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btZoomIn.setMinimumSize(new java.awt.Dimension(24, 24));
        btZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btZoomInActionPerformed(evt);
            }
        });

        btBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/fill.png"))); // NOI18N
        btBackground.setBorder(null);
        btBackground.setBorderPainted(false);
        btBackground.setContentAreaFilled(false);
        btBackground.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btBackground.setDisabledIcon(null);
        btBackground.setDisabledSelectedIcon(null);
        btBackground.setFocusable(false);
        btBackground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackgroundActionPerformed(evt);
            }
        });

        btRbd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/rbd5.png"))); // NOI18N
        btRbd.setBorder(null);
        btRbd.setBorderPainted(false);
        btRbd.setContentAreaFilled(false);
        btRbd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btRbd.setFocusable(false);
        btRbd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRbd.setMinimumSize(new java.awt.Dimension(24, 24));
        btRbd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRbdActionPerformed(evt);
            }
        });

        btSpn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/spn5.png"))); // NOI18N
        btSpn.setBorder(null);
        btSpn.setBorderPainted(false);
        btSpn.setContentAreaFilled(false);
        btSpn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSpn.setFocusable(false);
        btSpn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSpn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSpnActionPerformed(evt);
            }
        });

        btEfm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/efm5.png"))); // NOI18N
        btEfm.setBorder(null);
        btEfm.setBorderPainted(false);
        btEfm.setContentAreaFilled(false);
        btEfm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btEfm.setFocusable(false);
        btEfm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btEfm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEfmActionPerformed(evt);
            }
        });

        btCorret.setText("Corret");
        btCorret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCorretActionPerformed(evt);
            }
        });

        btPrevent.setText("Prevent");
        btPrevent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPreventActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnMenuBarLayout = new javax.swing.GroupLayout(pnMenuBar);
        pnMenuBar.setLayout(pnMenuBarLayout);
        pnMenuBarLayout.setHorizontalGroup(
            pnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMenuBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btBack, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btForward, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCpn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btEfm, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRbd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSpn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCorret)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPrevent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btView, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(btGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btZoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        pnMenuBarLayout.setVerticalGroup(
            pnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMenuBarLayout.createSequentialGroup()
                .addGroup(pnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btCorret)
                        .addComponent(btPrevent))
                    .addGroup(pnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btNew, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btOpen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btBack, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btForward, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
                        .addComponent(jSeparator3)
                        .addComponent(jSeparator4)
                        .addComponent(btCpn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2)
                        .addComponent(btView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btZoomOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btZoomIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btRbd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSpn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btEfm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setBorder(null);

        menuArquivo.setText("Arquivo");

        jMenuItem1.setText("Novo");
        menuArquivo.add(jMenuItem1);

        menuSalvar.setText("Salvar");
        menuSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalvarActionPerformed(evt);
            }
        });
        menuArquivo.add(menuSalvar);

        menuAbrir.setText("Abrir");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        menuArquivo.add(menuAbrir);
        menuArquivo.add(jSeparator6);

        menuSair.setText("Sair");
        menuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSairActionPerformed(evt);
            }
        });
        menuArquivo.add(menuSair);

        jMenuBar1.add(menuArquivo);

        menuCalcular.setText("Calcular");

        menuCalcRBD.setText("RBD");
        menuCalcRBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCalcRBDActionPerformed(evt);
            }
        });
        menuCalcular.add(menuCalcRBD);

        menuCalcSPN.setText("SPN");
        menuCalcSPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCalcSPNActionPerformed(evt);
            }
        });
        menuCalcular.add(menuCalcSPN);

        menuCalcEFM.setText("EFM");
        menuCalcEFM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCalcEFMActionPerformed(evt);
            }
        });
        menuCalcular.add(menuCalcEFM);

        menuCalcCPN.setText("CPN");
        menuCalcular.add(menuCalcCPN);

        jMenuBar1.add(menuCalcular);

        jMenu1.setText("Mod. Otimização");

        menuAlgGen.setText("Algoritimo Genetico");
        menuAlgGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAlgGenActionPerformed(evt);
            }
        });
        jMenu1.add(menuAlgGen);

        menuAlgDif.setText("Alg. Diferencial ");
        menuAlgDif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAlgDifActionPerformed(evt);
            }
        });
        jMenu1.add(menuAlgDif);

        menuNSGA2.setText("NSGA II");
        menuNSGA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNSGA2ActionPerformed(evt);
            }
        });
        jMenu1.add(menuNSGA2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Mod. Manutenção");

        menuCalcManutCorret.setText("Manut. Corret.");
        menuCalcManutCorret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCalcManutCorretActionPerformed(evt);
            }
        });
        jMenu2.add(menuCalcManutCorret);

        menuCalcManutPrev.setText("Manut. Prev.");
        menuCalcManutPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCalcManutPrevActionPerformed(evt);
            }
        });
        jMenu2.add(menuCalcManutPrev);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnMenuBar, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnMenuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 511, Short.MAX_VALUE)
                .addComponent(pnStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(816, 617));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSairActionPerformed
        int sair = JOptionPane.showConfirmDialog(null, "Deseja sair da aplicação?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuSairActionPerformed
//------------------------------------------------------------------------------
    private void popInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popInsertActionPerformed
        acGrafo.insertVertex(mousePos.x, mousePos.y);
//        contComp++;
    }//GEN-LAST:event_popInsertActionPerformed
//------------------------------------------------------------------------------
    private void popDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popDeleteActionPerformed
        acGrafo.deleteCell(cell);
        cell = null;
    }//GEN-LAST:event_popDeleteActionPerformed

//------------------------------------------------------------------------------
    private void popInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popInfoActionPerformed
        acGrafo.infoGraph();
    }//GEN-LAST:event_popInfoActionPerformed
//------------------------------------------------------------------------------
    private void popPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popPropActionPerformed
        JanelaPropriedades tp = new JanelaPropriedades(this, true); 
        try {
            if (cell != null) {
                Componente prop = (Componente) cell.getValue();
                tp.iniciaForm(prop);
                tp.setLocationRelativeTo(null);
                tp.setVisible(true);
            }
        } catch (ClassCastException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_popPropActionPerformed
//------------------------------------------------------------------------------
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        graphComponent.refresh(); //atualizar componente.
    }//GEN-LAST:event_formWindowActivated
//------------------------------------------------------------------------------
    private void btNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewActionPerformed
        // TODO add your handling code here:
        JanelaGrafo jg = new JanelaGrafo();
        jg.pnJanela.add(mxPane);
        jg.pnJanela.add(popup);
        jg.setVisible(true);
    }//GEN-LAST:event_btNewActionPerformed
//------------------------------------------------------------------------------
    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        //criando filtro de arquivos, (nomeNaLista, extensão)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos do tipo (*.xml)", "xml"); 

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
        
        String nomeArquivo = "";
        
        if (arq == JFileChooser.APPROVE_OPTION) {
                nomeArquivo = arquivo.getSelectedFile().getAbsolutePath();
                
                if (!nomeArquivo.contains(".")) {
                    nomeArquivo += ".xml";
                }
            }

        Object[] vertices = acGrafo.getVertices();
        Object[] arestas = acGrafo.getArestas();
        
        try{
            FileOutputStream fos = new FileOutputStream(new File(nomeArquivo));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(vertices.length + arestas.length);
            
            for (Object obj : vertices){
                encoder.writeObject(obj);
            }
            for (Object obj : arestas){
                encoder.writeObject(obj);
            }
            
            encoder.close();
            fos.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btSaveActionPerformed
//------------------------------------------------------------------------------
    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btBackActionPerformed
//------------------------------------------------------------------------------
    private void btForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btForwardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btForwardActionPerformed
//------------------------------------------------------------------------------
    private void btCpnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCpnActionPerformed
        // TODO add your handling code here:
//        GerarCPN_ARCH_A cpn = new GerarCPN_ARCH_A();
//        GerarCPNTestes cpn = new GerarCPNTestes();
//        GerarCPNTestes_B cpn = new GerarCPNTestes_B();
//        GerarArquivoScriptCPN cpn = new GerarArquivoScriptCPN();
//        cpn.imprimirScript();
        GerarCPN cpn = new GerarCPN();
        cpn.imprimirScript(acGrafo.getVertices());
    }//GEN-LAST:event_btCpnActionPerformed
//------------------------------------------------------------------------------
    private void btViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewActionPerformed
        boolean status = graphComponent.isPageVisible();
        if (!status) {
            graphComponent.setPageVisible(true);
            graphComponent.refresh();
            btView.setIcon(new ImageIcon(getClass().getResource("/ico/eyesblue.png")));
            btView.repaint();
        } else {
            graphComponent.setPageVisible(false); 
            graphComponent.refresh();
            btView.setIcon(new ImageIcon(getClass().getResource("/ico/eyesoff.png")));
            btView.repaint();
        }
//        graphComponent.repaint();
    }//GEN-LAST:event_btViewActionPerformed
//------------------------------------------------------------------------------
    private void btGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGridActionPerformed
        boolean status = graphComponent.isGridVisible();
        if (!status) {
            graphComponent.setGridVisible(true);
            btGrid.setIcon(new ImageIcon(getClass().getResource("/ico/grid3.png")));
            btGrid.repaint();
        } else {
            graphComponent.setGridVisible(false);
            btGrid.setIcon(new ImageIcon(getClass().getResource("/ico/grid3off.png")));
            btGrid.repaint();
        }
        graphComponent.repaint();
    }//GEN-LAST:event_btGridActionPerformed
//------------------------------------------------------------------------------
    private void btZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btZoomOutActionPerformed
        // TODO add your handling code here:
        if (lim <= 10) {
            btZoomIn.setEnabled(true);
        }
        if (lim > 0) {
            graphComponent.zoomOut();
            lim--;
        }
        if (lim <= 0 ) {
            btZoomOut.setEnabled(false);
        }
    }//GEN-LAST:event_btZoomOutActionPerformed
//------------------------------------------------------------------------------
    private void btZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btZoomInActionPerformed
        // TODO add your handling code here:
        if (lim >= 0) {
            btZoomOut.setEnabled(true);
        }
        if (lim < 10) {
            graphComponent.zoomIn();
            lim++;
        }
        if (lim >= 10) {
            btZoomIn.setEnabled(false);
        } 
    }//GEN-LAST:event_btZoomInActionPerformed

//------------------------------------------------------------------------------
    private void btBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackgroundActionPerformed
        // TODO add your handling code here:
        escolherCor = new JColorChooser();
        corEscolhida = escolherCor.showDialog(null, "Ecolha a cor", Color.white);
        if (cell != null) {
        } else {
            graphComponent.getViewport().setBackground(corEscolhida);
            graphComponent.setOpaque(true); 
            graphComponent.refresh();
            
        }
    }//GEN-LAST:event_btBackgroundActionPerformed
//------------------------------------------------------------------------------
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
//        mxCell = (mxCell) graphComponent.getCellAt(evt.getX(), evt.getY());
//        System.out.println("a");
//            
//        if (evt.isPopupTrigger() && (!(mxCell.getValue().equals(null)))) {
//            if(mxCell.isEdge()){
//                Componente propriedades = (Componente) mxCell.getValue();
//                System.out.println("mttf " + propriedades.getMttf() + " mttr " + propriedades.getMttr());
//            }
//            addPopup(graphComponent, popup);
//        }
    }//GEN-LAST:event_formMouseReleased

    private void btRbdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRbdActionPerformed
        //String script = gRBD.gerarScript(acGrafo.getVertices());
        //System.out.println(script);
        
        GerarRBD rbd = new GerarRBD();
        rbd.imprimirScript(acGrafo.getVertices());
    }//GEN-LAST:event_btRbdActionPerformed

    private void btSpnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSpnActionPerformed
        GerarSPN spn = new GerarSPN();
        spn.imprimirScript(acGrafo.getVertices());
    }//GEN-LAST:event_btSpnActionPerformed

    private void menuCalcRBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCalcRBDActionPerformed
        Object[] vertices = acGrafo.getVertices();
        
        if(!(vertices.length == 0)){
            String script = gRBD.gerarScript(vertices);
            JanelaResultados jr = new JanelaResultados(this, true);
            System.out.println(script);
            jr.taResult.setText(this.arqS.calcRBD(script));
            jr.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum esquema encontrado.");
        }
        
    }//GEN-LAST:event_menuCalcRBDActionPerformed

    private void menuCalcSPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCalcSPNActionPerformed
//        GerarSPN spn = new GerarSPN();
//        GerarArqScript calcSpn = new GerarArqScript();

        Object[] vertices = acGrafo.getVertices();
        
        if(!(vertices.length == 0)){
            String script = gSPN.gerarScript(vertices);
            JanelaResultados jr = new JanelaResultados(this, true);
            jr.taResult.setText(this.arqS.calcSPN(script));
            jr.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum esquema encontrado.");
        }
    }//GEN-LAST:event_menuCalcSPNActionPerformed

    private void menuCalcEFMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCalcEFMActionPerformed
        JanelaPropEFM propEfm = new JanelaPropEFM(this, true);
        EfmDados efmD = new EfmDados();
        propEfm.iniciaForm(efmD);
        propEfm.setVisible(true);
        
        Object[] vertices = acGrafo.getVertices();
        if(!(vertices.length == 0)){
            boolean calcAv = efmD.isCalcAval();
            double aval;
            if (calcAv == false){
                aval = efmD.getAvaliability();
            }else{
                String scriptAv = gSPN.gerarScript(vertices);
                
                aval = this.arqS.calcAval(scriptAv);
            }
            
            String script = gEFM.gerarScript(vertices);
            JanelaResultados jr = new JanelaResultados(this, true);
            
            double period = efmD.getPeriod();
            double ec = efmD.getElectricityCost();
            double de = efmD.getDemandEnergy();
            jr.taResult.setText(this.arqS.calcEFM(script, aval, period, ec, de));
            jr.setVisible(true);
        }
    }//GEN-LAST:event_menuCalcEFMActionPerformed

    private void btEfmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEfmActionPerformed
        GerarEFM efm = new GerarEFM();
        efm.imprimirScript(acGrafo.getVertices());
    }//GEN-LAST:event_btEfmActionPerformed

    private void btOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOpenActionPerformed
        
        //criando filtro de arquivos, (nomeNaLista, extensão) pode ser um vetor com vários filtros
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto (.xml)", "xml");

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

        String nomeArquivo = "";
        
        if (arq == JFileChooser.APPROVE_OPTION) {
            nomeArquivo = arquivo.getSelectedFile().getAbsolutePath();
            System.out.println(nomeArquivo);

            try {
                FileInputStream fis = new FileInputStream(new File(nomeArquivo));
                XMLDecoder decoder = new XMLDecoder(fis);
                mxCell cell = new mxCell();
                int qtdcells = (Integer) decoder.readObject();
                while (!(qtdcells == 0)){
                    cell = (mxCell) decoder.readObject();
                    acGrafo.insertCell(cell);
                    qtdcells--;
                }
                System.out.println("fim");
                decoder.close();
                fis.close();
            }  catch (Exception ex) {
                ex.printStackTrace();
            }   
        }
    }//GEN-LAST:event_btOpenActionPerformed

    private void menuSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalvarActionPerformed
        //criando filtro de arquivos, (nomeNaLista, extensão)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos do tipo (*.xml)", "xml"); 

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
        
        String nomeArquivo = "";
        
        if (arq == JFileChooser.APPROVE_OPTION) {
                nomeArquivo = arquivo.getSelectedFile().getAbsolutePath();
                
                if (!nomeArquivo.contains(".")) {
                    nomeArquivo += ".xml";
                }
            }

        Object[] vertices = acGrafo.getVertices();
        Object[] arestas = acGrafo.getArestas();
        
        try{
            FileOutputStream fos = new FileOutputStream(new File(nomeArquivo));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(vertices.length + arestas.length);
            
            for (Object obj : vertices){
                encoder.writeObject(obj);
            }
            for (Object obj : arestas){
                encoder.writeObject(obj);
            }
            
            encoder.close();
            fos.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_menuSalvarActionPerformed

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        //criando filtro de arquivos, (nomeNaLista, extensão) pode ser um vetor com vários filtros
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto (.xml)", "xml");

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

        String nomeArquivo = "";

        if (arq == JFileChooser.APPROVE_OPTION) {
            nomeArquivo = arquivo.getSelectedFile().getAbsolutePath();
            System.out.println(nomeArquivo);
            
            try {
                FileInputStream fis = new FileInputStream(new File(nomeArquivo));
                XMLDecoder decoder = new XMLDecoder(fis);
                mxCell cell = new mxCell();
                int qtdcells = (Integer) decoder.readObject();
                while (!(qtdcells == 0)){
                    cell = (mxCell) decoder.readObject();
                    acGrafo.insertCell(cell);
                    qtdcells--;
                }
                System.out.println("fim");
                decoder.close();
                fis.close();
            }  catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuAlgGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAlgGenActionPerformed
        Object[] vertices = acGrafo.getVertices();
        if(!(vertices.length == 0)){
            
            /*String scriptAv = gRBD.gerarScript2(vertices);
            String script = gEFM.gerarScript2(vertices);
            this.arqS.preencherScript(scriptAv + "\n" + script);*/
            
            JanelaAlgGen algGen = new JanelaAlgGen(this, true);
            algGen.iniciaForm(vertices);

            algGen.setVisible(true);
            }else{
            JOptionPane.showMessageDialog(this, "Nenhum componente encontrado");
        }
        
    }//GEN-LAST:event_menuAlgGenActionPerformed

    private void btCorretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCorretActionPerformed
        GerarManutCorret.imprimirScript(acGrafo.getVertices());
    }//GEN-LAST:event_btCorretActionPerformed

    private void menuCalcManutCorretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCalcManutCorretActionPerformed
        Object[] vertices = acGrafo.getVertices();
        
        if(!(vertices.length == 0)){
            String script = GerarManutCorret.gerarScript(vertices);
            JanelaResultados jr = new JanelaResultados(this, true);
            jr.taResult.setText(this.arqS.calcSPN(script));
            jr.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum esquema encontrado.");
        }
    }//GEN-LAST:event_menuCalcManutCorretActionPerformed

    private void btPreventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPreventActionPerformed
        GerarManutPrev.imprimirScript(acGrafo.getVertices());
    }//GEN-LAST:event_btPreventActionPerformed

    private void menuCalcManutPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCalcManutPrevActionPerformed
        Object[] vertices = acGrafo.getVertices();
        
        if(!(vertices.length == 0)){
            String script = GerarManutPrev.gerarScript(vertices);
            JanelaResultados jr = new JanelaResultados(this, true);
            jr.taResult.setText(this.arqS.calcSPN(script));
            jr.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum esquema encontrado.");
        }
    }//GEN-LAST:event_menuCalcManutPrevActionPerformed

    private void menuAlgDifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAlgDifActionPerformed
        Object[] vertices = acGrafo.getVertices();
        if (!(vertices.length == 0)) {

            String scriptAv = gRBD.gerarScript2(vertices);
            String script = gEFM.gerarScript2(vertices);
            this.arqS.preencherScript(scriptAv + "\n" + script);

            JanelaDifEvo difEvo = new JanelaDifEvo(this, true);
            difEvo.iniciaForm(vertices);

            difEvo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum componente encontrado");
        }
    }//GEN-LAST:event_menuAlgDifActionPerformed

    private void menuNSGA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNSGA2ActionPerformed
        Object[] vertices = acGrafo.getVertices();
        if (!(vertices.length == 0)) {

            String scriptAv = gRBD.gerarScript2(vertices);
            String script = gEFM.gerarScript2(vertices);
            this.arqS.preencherScript(scriptAv + "\n" + script);

                        Componente comp;
            ArrayList<Componente> comps = new ArrayList<>();
            for (Object vert : vertices) {
                comp = (Componente) ((mxCell) vert).getValue();
                comps.add(comp);
            }
            new NSGA2.AlgNSGA2(comps, 15, 88888, 0.6, 2, 20);//objeto da classe algoritmo genetico, ao ser criado o algoritmo ja inicia
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum componente encontrado");
        }
    }//GEN-LAST:event_menuNSGA2ActionPerformed

//---------------------- INTRODUZIR MENU NO GRAPHCOMPONENT----------------------
    private static void addPopup(Component component, final JPopupMenu popMenu) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                        showMenu(e);
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                        showMenu(e);
                }
            }
            private void showMenu(MouseEvent e) {
                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }
//------------------------------------------------------------------------------
    
    public static void main(String args[]) {

//        UIManager.put( "nimbusBase", new Color(240, 240, 240));
//        UIManager.put( "nimbusBlueGrey", new Color(240, 240, 240));
//        UIManager.put( "control", new Color(240, 240, 240));
        
        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaPrincipal tp = new TelaPrincipal();
                tp.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel5;
    private javax.swing.JButton btBack;
    private javax.swing.JButton btBackground;
    private javax.swing.JButton btCorret;
    private javax.swing.JButton btCpn;
    private javax.swing.JButton btEfm;
    private javax.swing.JButton btForward;
    private javax.swing.JButton btGrid;
    private javax.swing.JButton btNew;
    private javax.swing.JButton btOpen;
    private javax.swing.JButton btPrevent;
    private javax.swing.JButton btRbd;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btSpn;
    private javax.swing.JButton btView;
    private javax.swing.JButton btZoomIn;
    private javax.swing.JButton btZoomOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JLabel lblOpAtiva;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuAlgDif;
    private javax.swing.JMenuItem menuAlgGen;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenuItem menuCalcCPN;
    private javax.swing.JMenuItem menuCalcEFM;
    private javax.swing.JMenuItem menuCalcManutCorret;
    private javax.swing.JMenuItem menuCalcManutPrev;
    private javax.swing.JMenuItem menuCalcRBD;
    private javax.swing.JMenuItem menuCalcSPN;
    private javax.swing.JMenu menuCalcular;
    private javax.swing.JMenuItem menuNSGA2;
    private javax.swing.JMenuItem menuSair;
    private javax.swing.JMenuItem menuSalvar;
    private javax.swing.JPanel pnMenuBar;
    private javax.swing.JPanel pnStatus;
    private javax.swing.JMenuItem popDelete;
    private javax.swing.JMenuItem popInfo;
    private javax.swing.JMenuItem popInsert;
    private javax.swing.JMenuItem popProp;
    private javax.swing.JPopupMenu popup;
    // End of variables declaration//GEN-END:variables
    
}
