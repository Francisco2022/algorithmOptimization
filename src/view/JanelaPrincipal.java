package view;

//import editordetexto.util.ManipulaArquivoTXT;
//import editordetexto.util.ManipulaArquivoBin;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JanelaPrincipal extends JFrame{

    private JMenuBar menuBar;
    private JMenu mnArquivo;
    private JMenuItem miNovo;
    private JMenuItem miAbrir;
    private JMenuItem miSalvar;
    private JMenuItem miSair;
    private JLabel lbQtdAcesso;
    public  JTextArea taTexto;

    public JanelaPrincipal() {
        super("..: Código Gerado :..");
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icone = new ImageIcon(getClass().getResource("/ico/blueico2.png")); 
        setIconImage(icone.getImage());
        
        menuBar = new JMenuBar();
        mnArquivo = new JMenu("Arquivo");
        miNovo = new JMenuItem("Novo");
        miAbrir = new JMenuItem("Abrir");
        miSalvar = new JMenuItem("Salvar");
        miSair = new JMenuItem("Sair");
        lbQtdAcesso = new JLabel("Qtd. Acesso: 0");
        
        taTexto = new JTextArea();
        
        mnArquivo.add(miNovo);
        mnArquivo.add(miAbrir);
        mnArquivo.add(miSalvar);
        mnArquivo.addSeparator();
        mnArquivo.add(miSair);
        
        menuBar.add(mnArquivo);
        this.setJMenuBar(menuBar);

        this.getContentPane().add(taTexto, BorderLayout.CENTER); // adicionando componente TextArea a tela.
        this.getContentPane().add(lbQtdAcesso, BorderLayout.SOUTH); // adicionando componente lbQtdAcesso a tela.
        
        //tratamento do eento de maneira anônima.
        miNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                miNovoOnClick();
            }
        });
    
        miAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                miAbrirOnClick();
            }
        });
        
        miSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                miSalvarOnClick();
            }
        });
        
        miSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                miSairOnClick();
            }
        });
        
        tratarQtdAcesso();
        this.setSize(480, 640);
        this.setVisible(true);
    }
    
    private void miNovoOnClick() {
        taTexto.setText("");
    }
    
    private void miAbrirOnClick() {
        try {
            //criando filtro de arquivos, (nomeNaLista, extensão) pode ser um vetor com vários filtros
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto (.txt)", "txt");
            
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

                taTexto.setText(retorno);
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
                String conteudo = taTexto.getText();
                
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
    
    private void miSairOnClick() {
        int sair = JOptionPane.showConfirmDialog(this, "Deseja sair a aplicação?", "Atenção",JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
//    public static void main(String[] args) {
//        
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//        }
//        
//        new JanelaPrincipal();
//    }

    private void tratarQtdAcesso() {
        ManipulaArquivoBin arqBin = new ManipulaArquivoBin("draft.bin");
        int qtdAcesso = 0;
        
        try {
            qtdAcesso = arqBin.lerArquivoBin();
        } catch (FileNotFoundException e) {
            qtdAcesso = 0;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
        qtdAcesso++;
        lbQtdAcesso.setText("Qtd. Acesso: " + qtdAcesso);
        
        try {
            arqBin.gravarArquivoBin(qtdAcesso);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
    
}
