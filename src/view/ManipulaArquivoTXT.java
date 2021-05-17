package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ManipulaArquivoTXT {
    
    public static boolean gravarArquivoTXT(String nomeArquivo, String conteudo) throws IOException{
        FileWriter fout = new FileWriter(nomeArquivo);
        PrintWriter pout = new PrintWriter(fout);

        pout.println(conteudo);

        pout.close();
        fout.close();
        
        return true;
    }
    
    public static String lerArquivoTXT(String nomeArquivo) throws FileNotFoundException, IOException{
        FileReader fin = new FileReader(nomeArquivo);
        BufferedReader bin = new BufferedReader(fin);

        String linha = bin.readLine();
        String retorno = "";

        while (linha != null) {                
            retorno += linha + "\n";
            linha = bin.readLine();
        }

        bin.close();
        fin.close();
        
        return retorno;
    }

}
