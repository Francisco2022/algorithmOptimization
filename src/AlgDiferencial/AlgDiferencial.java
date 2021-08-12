package AlgDiferencial;

import Genes.Cromossomo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.Componente;


public class AlgDiferencial {
    private int qtdCromo;
    private int period;
    private double ec;
    private int de;
    private ArrayList<Componente> comps_arq;
    private ArrayList<Cromossomo> populacao = new ArrayList<>();
    private Map<String, ArrayList<Componente>> dicComp = new HashMap<>();
    private Random rand = new Random();
    private String script = "a2.mry";
    
    public static void main(String[] args){
        ArrayList<Componente> comps_arq = new ArrayList<>();
        comps_arq.add(new Componente());
        comps_arq.get(0).setAll("2", 5.0, 95.3, 3.1392, "UPS_5kVA");
        comps_arq.add(new Componente());
        comps_arq.get(1).setAll("3", 5.0, 98.5, 0.35928, "SDTransformer");
        comps_arq.add(new Componente());
        comps_arq.get(2).setAll("4", 5.0, 99.9, 0.4284, "Subpanel");
        comps_arq.add(new Componente());
        comps_arq.get(3).setAll("5", 5.0, 99.5, 0.35568, "PowerStrip");
        comps_arq.add(new Componente());
        comps_arq.get(4).setAll("6", 5.0, 95.3, 3.1392, "UPS_5kVA");
        comps_arq.add(new Componente());
        comps_arq.get(5).setAll("7", 5.0, 99.5, 0.35928, "STS");
        //comps_arq.add(new Componente());
        //comps_arq.get(6).setAll("8", 5.0, 98.5, 0.35928, "SDTransformer");
        //comps_arq.add(new Componente());
        //comps_arq.get(7).setAll("9", 5.0, 99.9, 0.4284, "Subpanel");
        //comps_arq.add(new Componente());
        //comps_arq.get(5).setAll("10", 5.0, 99.5, 0.35568, "PowerStrip");
        AlgDiferencial alg = new AlgDiferencial(comps_arq, 30, 7000, 0.4, 1, 10);
    }
    
    public AlgDiferencial(ArrayList<Componente> comps_arq,int qtdCromo, int period, double ec, int de, int eras) {
        Calendar data = Calendar.getInstance();
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int min = data.get(Calendar.MINUTE);
        int seg = data.get(Calendar.SECOND);
        String tempo1 = "" + hora + " : " + min + " : " + seg;
        System.out.println("tempo1 " + tempo1);
        
        this.comps_arq = comps_arq;
        this.qtdCromo = qtdCromo;
        this.period = period;
        this.ec = ec;
        this.de = de;
        this.lerComponentes();
        this.gerarCromossomos();
        this.rodarAlgoritmo(eras, 0.5);
        
    }
    
    
    private void gerarCromossomos() {//gera a populacao inicial
        ArrayList<Componente> genes;
        
        Cromossomo cromo;
        try{
            int cont = 0;
            String tipo;
            String idVert;
            while (cont< qtdCromo){
                genes = new ArrayList<>();
                for (Componente comp : comps_arq) {
                    tipo = comp.getTipoComp();
                    idVert = comp.getIdVert();
                    int var = this.rand.nextInt(dicComp.get(tipo).size());
                    comp = dicComp.get(tipo).get(var).clone();
                    comp.setIdVert(idVert);
                    genes.add(comp);
                }
                    cromo = new Cromossomo(genes, period, ec, de);
                    populacao.add(cromo);
                    cont++;
                }
            System.out.println("tam Pop   " + populacao.size());
        }catch(Exception ex){
            System.out.println("deu ruim");
            ex.printStackTrace();
        }
        
    }
    
    private void lerComponentes() {//le de um arquivo com varios componentes de todos os tipos e salva num dicionario (dicComp)
        try{
            //le o arquivo
            File arquivo = new File("base3em1.txt");
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            
            //insere as chaves do dicionario (tipo do componente)
            this.dicComp.put("UPS_5kVA", new ArrayList<Componente>());
            this.dicComp.put("UPS_250kVA", new ArrayList<Componente>());
            this.dicComp.put("STS", new ArrayList<Componente>());
            this.dicComp.put("SDTransformer", new ArrayList<Componente>());
            this.dicComp.put("Subpanel", new ArrayList<Componente>());
            this.dicComp.put("PowerStrip", new ArrayList<Componente>());
            this.dicComp.put("ATS", new ArrayList<Componente>());
            this.dicComp.put("ACSource", new ArrayList<Componente>());
            this.dicComp.put("GeneratorGroup", new ArrayList<Componente>());
            this.dicComp.put("GeneratorGroup500", new ArrayList<Componente>());
            this.dicComp.put("JunctionBox", new ArrayList<Componente>());
            this.dicComp.put("Server", new ArrayList<Componente>());
            this.dicComp.put("Switch", new ArrayList<Componente>());
            this.dicComp.put("Router", new ArrayList<Componente>());
            this.dicComp.put("Chiller", new ArrayList<Componente>());
            this.dicComp.put("C_Tower", new ArrayList<Componente>());
            this.dicComp.put("CRAC", new ArrayList<Componente>());
            
            //preenche o dicionario com os componentes do arquivo, separando-os por chave
            Componente comp;
            while (br.ready()){
                String lincomp = br.readLine();
                String atb[] = new String[5];
                atb = lincomp.split(";");
                comp = new Componente();
                comp.setId(Integer.parseInt(atb[0]));
                comp.setTipoComp(atb[2]);
                comp.setMttf(Double.parseDouble(atb[3]));
                comp.setEficiencia(Double.parseDouble(atb[4]));
                comp.setPreco(Double.parseDouble(atb[5]));
                
                dicComp.get(atb[2]).add(comp);
            }
            for (String chave : dicComp.keySet()){
                this.getExtremos(dicComp.get(chave));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private double[] getExtremos(ArrayList<Componente> comps){
        double maiorPreco = -1;
        double menorPreco = 999999999;
        
        double maiorMttf = -1;
        double menorMttf = 999999999;
        
        double[] vMaior = new double[3];
        double[] vMenor = new double[3];
        
        double preco_atual;
        for (int i = 0; i < comps.size(); i++) {
            preco_atual = comps.get(i).getPreco();
            if (preco_atual > maiorPreco){
                maiorPreco = preco_atual;
            }
            
            if (preco_atual < menorPreco){
                menorPreco = preco_atual;
            }
            
            if (preco_atual > maiorMttf){
                maiorMttf = preco_atual;
            }
            
            if (preco_atual < menorMttf){
                menorMttf = preco_atual;
            }
            //eficiencia ficou com valor fixo
        }
        
        vMaior[0] = maiorPreco;
        vMenor[0] = menorPreco;
        
        vMaior[1] = 0.99999;
        vMenor[1] = 0.99999;
        
        vMaior[2] = maiorMttf;
        vMenor[2] = menorMttf;
        return new double[4];
    }
    
    public void rodarAlgoritmo(int numIt,double crossover){
        //System.out.println("rodando");
        Cromossomo cromoDonor, Vtrial;
        ArrayList<Componente> genes;
        ArrayList<Cromossomo> nova_populacao;
        for (int contador = 0; contador < numIt; contador++) {//geracoes
            //System.out.println("geracao" + contador);
            nova_populacao = new ArrayList<>();
            for (int i = 0; i < populacao.size(); i++) {//individuos da populacao
                //System.out.println("individuo " + i);
                genes = new ArrayList<>();
                Cromossomo c_atual = populacao.get(i);
                for (int j = 0; j<c_atual.getGenes().size(); j++){//componentes de um individuo
                    //System.out.println("id  " + c_atual.getGenes().get(j).getId());
                    String tipo = c_atual.getGenes().get(j).getTipoComp();
                    int aleat[] = new int[3];
                    Componente torneio[] = new Componente[3];
                    
                    int var = this.rand.nextInt(dicComp.get(tipo).size());
                    aleat[0] = var;
                    torneio[0] = dicComp.get(tipo).get(var);

                    for (int k = 1; k < 3; k++) {
                        var = this.rand.nextInt(dicComp.get(tipo).size());
                        aleat[k] = var;
                        torneio[k] = dicComp.get(tipo).get(var);
                        for (int m = 0; m < k; m++) {
                            if (aleat[k] == aleat[m]) {
                                k--;
                            }
                        }//  sim
                    }
                    double donorPreco, donorEficiencia, donorMttf;
                    double[] Vdonor = new double[3];//novo componente
                    
                    
                    //mutacao
                    donorPreco = torneio[0].getPreco() + 0.45 * (torneio[1].getPreco() - torneio[2].getPreco());
                    donorEficiencia = torneio[0].getEficiencia()+ 0.45 * (torneio[1].getEficiencia() - torneio[2].getEficiencia());
                    donorMttf = torneio[0].getMttf()+ 0.45 * (torneio[1].getMttf() - torneio[2].getMttf());
                    //criar metodo para comparar com a base de dados 
                    //colocar dentro dos limites
                        
                    //atibuicao das caracteristicas
                    Vdonor[0] = donorPreco;
                    Vdonor[1] = donorEficiencia;
                    Vdonor[2] = donorMttf;
                    //metodo comparar fitness do vdonr com os fitness da base
                    
                    //this.checarLimite(Vdonor);
                    
                    Componente comp_donor = this.difVetorial(Vdonor, tipo);
                    comp_donor.setIdVert(c_atual.getGenes().get(j).getIdVert());
                    //aqui um gene mutante pronto
                    genes.add(comp_donor);
                }
                
                //individuo donor a ser montado
                cromoDonor = new Cromossomo(genes, period, ec, de);//individuo mutante com todos os componentes
                cromoDonor.setScore(script);//calcula o fitnes internamente
                
                /*crossover/cruzamento (ver quais genes do individuo atual 
                  serao trocados por genes do individuo donor)*/
                Vtrial = c_atual;
                
                
                
                try {
                    for (int g = 0; g < genes.size(); g++) {
                        double pc = Math.random();//chance de uma parte do donor entrar no trial
                        if (pc < crossover) {
                            //System.out.println("trocou");
                            Vtrial.getGenes().set(g, cromoDonor.getGenes().get(g).clone());
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                /*individuo tentativa criado 
                (mistura entre o individuo atual e o mutante*/
                Vtrial.setScore(script);
                
                //verificacao se o novo individuo é melhor que o atual
                //caso sim entra na nova populacao
                if (Vtrial.getScore() > c_atual.getScore()) {
                    nova_populacao.add(Vtrial);
                }
                else {
                    nova_populacao.add(c_atual);
                }
                //aqui termina a modificao de 1 individuo da populacao

            }
            //aqui nova populacao ja formada
            populacao = nova_populacao;
            
            Cromossomo maior = populacao.get(0);

            int cont = 0;
            double score;
            while (cont < populacao.size()) {
                score = populacao.get(cont).getScore();
                if (score > maior.getScore()) {
                    maior = populacao.get(cont);
                    
                }
                cont++;
            }
            
            //System.out.println("geracao " + contador + "melhor score " + maior.getScore());

        }
        //aqui todas as geracoes ja rodaram
        
        try {
            FileWriter arq = new FileWriter("resultados_dif.txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            //gravarArq.printf("ups - sdt - subp - ps - score");
            int cont;
            String dado = "";
            double maior = 0;
            String max = "";
            double atual;
            for (Cromossomo c : populacao) {
                dado = "";
                cont = 0;
                atual = c.getScore();

                gravarArq.printf("Algoritmo Diferencial Evolucionario");
                while (cont < c.getGenes().size()) {
                    dado += c.getGenes().get(cont).getTipoComp();
                    dado += c.getGenes().get(cont).getId() + " - ";
                    cont++;
                }
                dado += c.getScore();
                gravarArq.printf("\n" + dado);
                if (atual > maior) {
                    maior = atual;
                    max = dado;
                }
            }
            System.out.println("maior   " + maior);
            double numNoves = Math.log10(1 - maior);
            System.out.println("Dif Evo     num 9s " + numNoves + " ###  maior " + maior);
            gravarArq.printf("\n\n\nmaior   " + max);
            arq.close();
            //System.out.println("fim");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Calendar data = Calendar.getInstance();
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int min = data.get(Calendar.MINUTE);
        int seg = data.get(Calendar.SECOND);
        String tempo2 = "" + hora + " : " + min + " : " + seg;
        System.out.println("\ntempo2 " + tempo2);

        //return novaPopulacao;
	}
    
    private Componente difVetorial(double[] Vdonor, String tipo){//traz um componente da base mais parecido ao Vdonor
        double preco, eficiencia, mttf, dist_atual, dist_menor;
        dist_menor = 999999999;
        Componente menor = new Componente();
        
        for (Componente comp : dicComp.get(tipo)){
            preco = comp.getPreco() - Vdonor[0];
            eficiencia = comp.getEficiencia() - Vdonor[1];
            mttf = comp.getMttf() - Vdonor[2];
            dist_atual = Math.sqrt(Math.pow(preco, 2) + Math.pow(eficiencia, 2) + Math.pow(mttf, 2));
            if (dist_atual < dist_menor){
                dist_menor = dist_atual;
                try {
                    menor = comp.clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(AlgDiferencial.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //System.out.println("vdonor [" + Vdonor[0] + ", " + Vdonor[1] + ", " + Vdonor[2] +  "]  \ncomp_donor [   " + menor.getVetor()[0] + ", " + menor.getVetor()[1] + ", " + menor.getVetor()[2]);
        return menor;
    }
    
    
    
    private double fitnessDonor(double[] comp) {
        //System.out.println("tc " +  (totalCost/1000000) + "\nav "+ (availability*100) + "\noe" + (operationalExergy/100000));
        double score = comp[0] / 1000000 - (comp[1] * 100 + comp[2] / 100000);
        return score;

    }
    
    /*private void checarLimite(double[] comp){
        if (comp[0] > limMaxPreco){
            comp[0] = limMaxPreco;
        }
        
        if (comp[0] < limMinPreco){
            comp[0] = limMinPreco;
        }
        
        if (comp[1] > limMaxEff){
            comp[1] = limMaxEff;
        }
        
        if (comp[1] < limMinEff){
            comp[1] = limMinEff;
        }
        
        if (comp[2] > limMaxMttf){
            comp[2] = limMaxMttf;
        }
        
        if (comp[2] < limMinMttf){
            comp[2] = limMinMttf;
        }
    }*/
}
