package Genes;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import view.Componente;

public class GeradorAleatorio {
    private Map<String, Map<String, Double[]>> valores;
    //private static double vari;
    //private static int qtd;
    
    
    public static void main(String[] args){
        double vari = 0.3;//variacao dos valores a partir do padrao
        int qtd = 10;//quantidade de componetes aleatorios a ser criado
        geraAleatorios(qtd);
        //ArrayList<Componente> comps = decodeCompsTipos();//decodifica os diferentes tipos de componentes
        //geraAleatorios(comps, vari, qtd);//gera varios componentes de cada tipo com valores aleatorios
        /*try{
            File arquivo = new File("Components.txt");
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            Componente comp;
            while (br.ready()){
                String lincomp = br.readLine();
                String atb[] = new String[5];
                atb = lincomp.split(";");
                comp = new Componente();
                comp.setId(Integer.parseInt(atb[0]));
                comp.setTipoComp(atb[2]);
                //comp.setMttf(Integer.parseInt(atb[3]));
                comp.setEficiencia(Double.parseDouble(atb[4]));
                comp.setPreco(Double.parseDouble(atb[5]));
                
                comps.add(comp);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }*/
        /*Map<String, ArrayList<Componente>>parcial = new HashMap<>();
        String tipo;
        for(Componente comp: comps){
            
            tipo = comp.getTipoComp();
            
            parcial.put(tipo, aleatorios(comp,0.32, qtd));
            
        }
        System.out.println(parcial.size());
        */
        
        
        /*try{
            FileOutputStream fos = new FileOutputStream(new File("aleat.xml"));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(parcial);
            System.out.println("fim");
            
            encoder.close();
            fos.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }*/
        
    }
    private static ArrayList<Componente> decodeCompsTipos(){
        ArrayList<Componente> comps = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File("comps.xml"));
            XMLDecoder decoder = new XMLDecoder(fis);
            Componente comp;
            int qtdc = (Integer) decoder.readObject();
            while (!(qtdc == 0)) {
                comp = (Componente) decoder.readObject();
                System.out.println(comp);
                comps.add(comp);
                qtdc--;
            }

            decoder.close();
            fis.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comps;
    }
    private static void geraAleatorios(int qtd){
        try {
            FileWriter arq = new FileWriter("componentesBaseMenor.txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("id;name;type;mttf;eff;aquisitionCost");

            String dados = "";
            dados += aleatUPS("UPS_5kVA", qtd);
            dados += aleatUPS("UPS_250kVA", qtd);//mttf 25000, 75000; ac 11250, 18750; eff 90.535, 99.9//mttf = 200000,efficiency = 95.3,retailPrice = 60000.0,mttr = 0.5
            dados += aleatSDT("SDTransformer", qtd);
            dados += aleatSp("Subpanel", qtd);
            dados += aleatPS("PowerStrip", qtd);
            dados += aleatSTS("STS", qtd);
            dados += aleatATS("ATS", qtd);
            dados += aleat("ACSource", qtd, 2190.0, 6570.0, 11250.0, 18750.0,90.535, 99.9);//efficiency = 95.3,retailPrice = 15000.0,);
            dados += aleat("GeneratorGroup", qtd, 1095, 3285, 45000, 75000, 23.75, 26.25);//efficiency = 25.0,retailPrice = 66000.0,
            dados += aleat("GeneratorGroup500", qtd, 1095, 3285, 45000, 75000, 23.75, 26.25);
            dados += aleat("JunctionBox", qtd, 2612000, 7836000, 112.50, 187.50,94.905, 99.9);//
            
            gravarArq.printf(dados);
            
            arq.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static void geraAleatorios(ArrayList<Componente> comps, double vari, int qtd){
        try {
            FileWriter arq = new FileWriter("componentes.txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("id;name;type;mttf;eff;aquisitionCost");

            for (Componente comp : comps) {
                String aleat[] = aleatorios(comp, qtd, vari);
                for (String dado : aleat) {
                    gravarArq.printf(dado);
                }
            }
            arq.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static String[] aleatorios(Componente comp, int qtd, double vari){
        String dados;
        int cont = 0;
        String aleatorio[] = new String[qtd];
        while (cont< qtd){
            dados = "\n";
            dados += (cont+1) + ";";
            dados += comp.getNomeComp() + (cont+1) + ";";
            dados += comp.getTipoComp() + ";";
            dados += randMttf(comp.getMttf()) + ";";
            dados += randEff(comp.getEficiencia()) + ";";
            dados += randPreco(comp.getPreco());
            
            aleatorio[cont] = dados;
            cont ++;
        }
        return aleatorio;
    }
    
    private static ArrayList<Componente> aleatorios(Componente comp, double vari, int qtd){
        ArrayList<Componente> aleatorio = new ArrayList<>();
        
        int cont = 0;
        Componente newcomp;
        while (cont< qtd){
            cont ++;
            newcomp = new Componente();
            newcomp.setEficiencia(randEff(comp.getEficiencia()));
            newcomp.setEnergia(random(comp.getEnergia(),vari));
            newcomp.setPoderMax(random(comp.getPoderMax(),vari));
            newcomp.setPreco(randPreco(comp.getPreco()));
            newcomp.setMttf(randMttf(comp.getMttf()));
            //newcomp.setMttr(random(comp.getMttr(),vari));
            newcomp.setTipoComp(comp.getTipoComp());
            newcomp.setId(cont);
            
            aleatorio.add(newcomp);
        }
        
        return aleatorio;
    }
    
    /*private static double random(double atb, double vari){
        double min = atb - atb*vari;
        double max = atb + atb*vari;
        
        double rand = min + Math.random()*(max-min);
        return rand;
    }*/
    
    private static double random(double min, double max){
        double rand = min + Math.random()*(max-min);
        return rand;
    }
    
    private static double randPreco(double preco){
        double min = preco - preco*0.25;
        double max = preco + preco*0.25;
        
        double rand = min + Math.random()*(max-min);
        return rand;
    }
    
    private static double randEff(double eff){
        double min = eff - eff*0.15;
        double max = eff + eff*0.15;
        
        double rand = min + Math.random()*(max-min);
        if (rand > 0.99){
            rand = 0.99999;
        }
        return rand;
    }
    
    private static double randMttf(double mttf){
        double min = mttf - mttf*0.5;
        double max = mttf + mttf*0.5;
        
        double rand = min + Math.random()*(max-min);
        return rand;
    }
        private static String aleat(String comp, int qtd, double mttf_min, double mttf_max, double eff_min, double eff_max, double ac_min, double ac_max){
        int cont = 0;//mttf;eff;aquisitionCost
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(mttf_min, mttf_max) + ";";
            dados += random(eff_min, eff_max) + ";";
            dados += random(ac_min, ac_max);
            
            cont ++;
        }
        
        return dados;
    }
    
    private static String aleatUPS(String comp, int qtd){
        int cont = 0;
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(25000, 75000) + ";";
            dados += random(90.535, 99.9) + ";";
            dados += random(11250, 18750);
            
            cont ++;
        }
        
        return dados;
    }
    
    private static String aleatSTS(String comp, int qtd){
        int cont = 0;
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(24038, 72114) + ";";
            dados += random(94.525, 99.9) + ";";
            dados += random(600, 1000);
            
            cont ++;
        }
        
        return dados;
        
    }
    
    private static String aleatATS(String comp, int qtd){
        int cont = 0;
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(300000.00, 750000.00) + ";";
            dados += random(94.525, 99.9) + ";";
            dados += random(600, 1000);
            
            cont ++;
        }
        
        return dados;
        
    }
    
    private static String aleatSDT(String comp, int qtd){
        int cont = 0;
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(141290.5, 423871.5) + ";";
            dados += random(93.575, 99.9) + ";";
            dados += random(412.50, 687.50);
            
            cont ++;
            
        }
        
        return dados;
    }
    
    private static String aleatSp(String comp, int qtd){
        int cont = 0;
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(152000, 456000) + ";";
            dados += random(94.905, 99.9) + ";";
            dados += random(150, 250);
            
            cont ++;
            
        }
        
        return dados;
    }
    
    private static String aleatPS(String comp, int qtd){
        int cont = 0;
        String dados = "";
        while (cont< qtd){
            
            dados += "\n";
            dados += (cont+1) + ";";
            dados += comp + (cont+1) + ";";
            dados += comp + ";";
            dados += random(115111.8, 345335.3) + ";";
            dados += random(94.525, 99.9) + ";";
            dados += random(150, 250);
            
            cont ++;
        }
            
        return dados;
    }
}
