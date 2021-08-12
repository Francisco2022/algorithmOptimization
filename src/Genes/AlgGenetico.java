package Genes;

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
import view.Componente;

public class AlgGenetico {
    private int qtdCromo;
    private int period;
    private double ec;
    private int de;
    private String tempo1;
    private ArrayList<Componente> comps_arq;
    private ArrayList<Cromossomo> cromossomos = new ArrayList<>();
    private Map<String, ArrayList<Componente>> dicComp = new HashMap<>();
    private Random rand = new Random();
    private String script = "a1.mry";
    
    
    public static void main(String[] args){
        ArrayList<Componente> comps_arq = new ArrayList<>();
        comps_arq.add(new Componente());
        comps_arq.get(0).setAll("1", 5.0, 95.3, 3.1392, "UPS_5kVA");
        comps_arq.add(new Componente());
        comps_arq.get(1).setAll("2", 5.0, 98.5, 0.35928, "SDTransformer");
        comps_arq.add(new Componente());
        comps_arq.get(2).setAll("3", 5.0, 99.9, 0.4284, "Subpanel");
        comps_arq.add(new Componente());
        comps_arq.get(3).setAll("4", 5.0, 99.5, 0.35568, "PowerStrip");
        
        comps_arq.add(new Componente());
        comps_arq.get(4).setAll("5", 550.0, 98.5, 0.35568, "Server");
        comps_arq.add(new Componente());
        comps_arq.get(5).setAll("11", 550.0, 98.5, 0.35568, "Server");
        comps_arq.add(new Componente());
        comps_arq.get(6).setAll("6", 550.0, 99.9, 0.35568, "Router");
        comps_arq.add(new Componente());
        comps_arq.get(7).setAll("7", 550.0, 99.5, 0.35568, "Switch");
        
        comps_arq.add(new Componente());
        comps_arq.get(8).setAll("8", 20.0, 98.5, 2.7296, "CRAC");
        comps_arq.add(new Componente());
        comps_arq.get(9).setAll("9", 15.0, 99.9, 4.5209, "Chiller");
        comps_arq.add(new Componente());
        comps_arq.get(10).setAll("10", 20.0, 99.5, 1.2795, "C_Tower");
        AlgGenetico alg = new AlgGenetico(comps_arq, 30, 7000, 0.4, 1, 50);
    }

    public AlgGenetico(ArrayList<Componente> comps_arq,int qtdCromo, int period, double ec, int de, int eras) {
        Calendar data = Calendar.getInstance();
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int min = data.get(Calendar.MINUTE);
        int seg = data.get(Calendar.SECOND);
        tempo1 = "" + hora + " : " + min + " : " + seg;
        
        
        this.comps_arq = comps_arq;
        this.qtdCromo = qtdCromo;
        this.period = period;
        this.ec = ec;
        this.de = de;
        this.lerComponentes();
        this.gerarCromossomos();
        this.percorrerEpocas(eras);
        //this.cruzarCromossomos();
        
    }
    
    public AlgGenetico(ArrayList<Componente> comps_arq,int qtdCromo, int period, double ec, int de, int eras, String script) {
        Calendar data = Calendar.getInstance();
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int min = data.get(Calendar.MINUTE);
        int seg = data.get(Calendar.SECOND);
        String tempo1 = "" + hora + " : " + min + " : " + seg;
        System.out.println("tempo1 " + tempo1);
        
        this.script = script;
        
        this.comps_arq = comps_arq;
        this.qtdCromo = qtdCromo;
        this.period = period;
        this.ec = ec;
        this.de = de;
        this.lerComponentes();
        this.gerarCromossomos();
        this.percorrerEpocas(eras);
        //this.cruzarCromossomos();
        
    }
    
    private void percorrerEpocas(int eras){
        int cont = 0;
        double medias[] = new double[3];
        medias[0] = 0;
        medias[1] = 0;
        medias[2] = 0;
        
        boolean convegiu = false;
        double media;
        while (cont < eras){
            
            this.cruzarCromossomos();
            
            this.removerMenor();
            this.removerMenor();
            
            media = this.getMedia();
            //System.out.println("media atual " + media);
            medias[0] = medias[1];
            medias[1] = medias[2];
            medias[2] = media;
            convegiu = this.calcDevioPadrao(medias);
            //System.out.println("desvio padrão " + convegiu);
            cont ++;
            if (convegiu){
                System.out.println("convegiu em " + cont + " geracoes");
                break;
            }
        }
        try {
            FileWriter arq = new FileWriter("resultados.txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            //gravarArq.printf("ups - sdt - subp - ps - score");
            String dado = "";
            double maior = 0;
            String max = "";
            double atual;
            double disp = 0;
            double custo=0;
            double exer=0;
            for (Cromossomo c : cromossomos) {
                dado = "";
                cont = 0;
                atual = c.getScore();
                
                while (cont < c.getGenes().size()){
                    dado += c.getGenes().get(cont).getTipoComp();
                    dado += c.getGenes().get(cont).getId() + " - ";
                    cont ++;
                }
                dado += c.getScore();
                gravarArq.printf("\n" + dado);
                if (atual> maior){
                    maior = atual;
                    max = dado;
                    disp = c.getAvailability();
                    custo= c.getTotalCost();
                    exer=c.getOperationalExergy();
                }
            }
            System.out.println("maior   " + maior);
            double numNoves = Math.log10(1-maior);
            System.out.println("Alg Gen    num 9s " + numNoves + " ###  maior " + maior);
            gravarArq.printf("\n\n\nmaior   " + max);
            gravarArq.printf("\ndisp\t" + disp+"\tcusto: "+ custo+"\texer: "+exer);
            gravarArq.printf("\tDisp em num9s   " + (-Math.log10(1-disp)));
            arq.close();
            //System.out.println("fim");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Calendar data = Calendar.getInstance();
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int min = data.get(Calendar.MINUTE);
        int seg = data.get(Calendar.SECOND);
        System.out.println("tempo1 " + tempo1);
        String tempo2 = "" + hora + " : " + min + " : " + seg;
        System.out.println( "\ntempo2 " + tempo2);
    }

    private void gerarCromossomos() {
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
                    cromossomos.add(cromo);
                    cromo.setScore(this.script);//.setScore(script);
                    cont++;
                }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }

    private void lerComponentes() {//le de um arquivo com varios componentes de todos os tipos e salva num dicionario (dicComp)
              try{
            //le o arquivo
            File arquivo = new File("componentesBaseMenor.txt");
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
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void cruzarCromossomos() {
        Cromossomo cromo1 = new Cromossomo();
        Cromossomo cromo2 = new Cromossomo();
        Object obj[] = this.selecaoTorneio();
        
        cromo1 = (Cromossomo) obj[0];
        cromo2 = (Cromossomo) obj[1];
        //System.out.println("esc");
        cromo1.setScore(this.script);//cromo1.setScore(this.script);
        cromo2.setScore(this.script);//cromo2.setScore(this.script);
        
        //System.out.println(cromo1);
        //System.out.println(cromo2);
        ArrayList<Componente> genes;
        
        genes = (ArrayList<Componente>)(cromo1.getGenes()).clone();
        Cromossomo cruza1 = new Cromossomo(genes, period, ec, de);;
        
        genes = (ArrayList<Componente>)(cromo2.getGenes()).clone();
        Cromossomo cruza2 = new Cromossomo(genes, period, ec, de);;
        
        int qtdEqp = cromo1.getGenes().size();
        int quebra = this.rand.nextInt((qtdEqp-1));
        //System.out.println("\nquebra" + quebra +"\n");
        int mutacao;
        
        try{
            while (quebra>=0){
                
                mutacao = this.rand.nextInt(10);
                cruza1.getGenes().set(quebra, cromo2.getGenes().get(quebra).clone());
                if (mutacao<5){
                    //System.out.println("mutacao cromossomo1, gene " + quebra);

                    cruza1.getGenes().set(quebra, this.aplicarMutacao(cruza1.getGenes().get(quebra)));
                }
                
                mutacao = this.rand.nextInt(10);
                cruza2.getGenes().set(quebra, cromo1.getGenes().get(quebra).clone());
                if (mutacao<5){
                    //System.out.println("mutacao cromossomo2, gene " + quebra);

                    cruza2.getGenes().set(quebra, this.aplicarMutacao(cruza1.getGenes().get(quebra)));
                }
            
                quebra--;
            }
            cruza1.setScore(this.script);//.setScore(this.script);
            cruza2.setScore(this.script);//.setScore(this.script);
            /*System.out.println(cruza1);
            System.out.println(cruza2);
            System.out.println("\n\n---------------------------------------\n\n");*/
            
            cromossomos.add(cruza1);
            cromossomos.add(cruza2);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Componente aplicarMutacao(Componente comp) {
        String tipo_comp = comp.getTipoComp();
        int id = comp.getId();
        String id_vert = comp.getIdVert();
        int limite = dicComp.get(tipo_comp).size();
        int muda_para;
        while (true) {
            muda_para = rand.nextInt(limite);
            if (!(muda_para == id)) {
                try{
                    comp = dicComp.get(tipo_comp).get(muda_para).clone();
                    break;
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        comp.setIdVert(id_vert);
        return comp;
    }
    
    private Object[] selecaoTorneio() {
        Cromossomo cromo1 = new Cromossomo();
        Cromossomo cromo2 = new Cromossomo();
        int aleat[] = new int[4];
        Cromossomo torneio[] = new Cromossomo[4];

        int var = this.rand.nextInt(cromossomos.size());
        aleat[0] = var;
        torneio[0] = cromossomos.get(var);
        torneio[0].setScore(this.script);//.setScore(script);
        
        for (int i = 1; i<4;i++){
            var = this.rand.nextInt(cromossomos.size());
            aleat[i] = var;
            torneio[i] = cromossomos.get(var);
            torneio[i].setScore(this.script);//.setScore(script);
            for (int j = 0; j<i;j++){
                if (aleat[i] == aleat[j]){
                    i--;
                }
            }
        }
        double maior1 = 0;
        double maior2 = 0;
        double score;
        for (Cromossomo i : torneio){
            score = i.getScore();
            if (score > maior1){
                maior1 = score;
                cromo2 = cromo1;
                cromo1 = i;
            }else if (score > maior2){
                maior2 = score;
                cromo2 = i;
            }
        }
        Object obj[] = new Object[2];
        obj[0] = cromo1;
        obj[1] = cromo2;
        return obj;
    }
    
    private void removerMenor() {
        int cont;
        double score;
        int posi_menor = 0;
        Cromossomo menor = cromossomos.get(0);
        
        cont = 0;
        while (cont < cromossomos.size()){
            score = cromossomos.get(cont).getScore();
            if (score < menor.getScore()){
                menor = cromossomos.get(cont);
                posi_menor = cont;
            }
            cont ++;
        }
        cromossomos.remove(posi_menor);
        //System.out.println("tam = " + cromossomos.size());
        
    }

    private double getMedia() {
        double media = 0;
        for (Cromossomo c : cromossomos) {
            media += c.getScore();
        }
        return (media/cromossomos.size());
    }

    private boolean calcDevioPadrao(double[] medias) {
        double media = (medias[0] + medias[1] + medias[2])/3;
        double val1, val2, val3;
        val1 = Math.pow(medias[0] - media,2);
        val2 = Math.pow(medias[1] - media,2);
        val3 = Math.pow(medias[2] - media,2);
        double desvio = Math.sqrt((val1 + val2 + val3)/3);
        if (desvio == 0){
            //System.out.println("zero\n\n");
            return true;
        }
        boolean b1, b2, b3;
        b1 = (medias[0] >= (media+desvio)) || (medias[0] <= (media-desvio));
        b2 = (medias[1] >= (media+desvio)) || (medias[1] <= (media-desvio));
        b3 = (medias[2] >= (media+desvio)) || (medias[2] <= (media-desvio));
        
        return (b1 && b2 && b3);
    }
}
