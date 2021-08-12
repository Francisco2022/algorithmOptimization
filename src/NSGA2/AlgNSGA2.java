package NSGA2;

import Genes.Cromossomo;
import Genes.FuncoesFB;
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

public class AlgNSGA2 {
    //abaixo criacao das variaveis globais
    private int qtdCromo;//tamanho populacao
    private int period;//periodo de tempo em que a arquitetura sera analisado 
    private double ec;//custo eletrico
    private int de;//exergia demanda
    private String tempo1;//momento inicial
    private ArrayList<Componente> comps_arq;//componentes da arquitetura
    private ArrayList<Cromossomo> cromossomos = new ArrayList<>();//populacao, individuos aleatorios baseados na arquitetura com componetes da base
    private Map<String, ArrayList<Componente>> dicComp = new HashMap<>();//dicionario com as base de componentes
    private Random rand = new Random();//objeto que gera valores inteiros aleatorios
    private Map<String, ArrayList<Cromossomo>> dicRank = new HashMap<>();
    private String arq = "subsit_cool";
    private String script = arq + ".mry";
    private int dia1, hora1, min1, seg1, dia2, hora2, min2, seg2, duracao;   
    
    String ranqueamento = "";
    
    public static void main(String[] args){
        /*quando mudar para rodar com scripts que são hot
            alterar onde estiver calcularmetricasCold para apenas calcularmetricas
        */
        ArrayList<Componente> comps_arq = new ArrayList<>();
        /*comps_arq.add(new Componente());
        comps_arq.get(0).setAll("2", 5.0, 95.3, 3.1392, "UPS_5kVA");
        comps_arq.add(new Componente());
        comps_arq.get(1).setAll("3", 5.0, 98.5, 0.35928, "SDTransformer");
        comps_arq.add(new Componente());
        comps_arq.get(2).setAll("4", 5.0, 99.9, 0.4284, "Subpanel");
        comps_arq.add(new Componente());
        comps_arq.get(3).setAll("5", 5.0, 99.5, 0.35568, "PowerStrip");
        
        comps_arq.add(new Componente());
        comps_arq.get(0).setAll("6", 550.0, 98.5, 0.35568, "Server");
        comps_arq.add(new Componente());
        comps_arq.get(1).setAll("7", 550.0, 99.9, 0.35568, "Router");
        comps_arq.add(new Componente());
        comps_arq.get(2).setAll("8", 550.0, 99.5, 0.35568, "Switch");*/
        
        comps_arq.add(new Componente());
        comps_arq.get(0).setAll("9", 20.0, 98.5, 2.7296, "CRAC");
        comps_arq.add(new Componente());
        comps_arq.get(1).setAll("10", 15.0, 99.9, 4.5209, "Chiller");
        comps_arq.add(new Componente());
        comps_arq.get(2).setAll("11", 20.0, 99.5, 1.2795, "C_Tower_C");
        
        /*comps_arq.add(new Componente());
        comps_arq.get(4).setAll("6", 5.0, 95.3, 3.1392, "UPS_5kVA");
        comps_arq.add(new Componente());
        //comps_arq.get(5).setAll("7", 5.0, 99.5, 0.35928, "STS");
        comps_arq.add(new Componente());
        comps_arq.get(6).setAll("8", 5.0, 98.5, 0.35928, "SDTransformer");
        comps_arq.add(new Componente());
        comps_arq.get(7).setAll("9", 5.0, 99.9, 0.4284, "Subpanel");
        //comps_arq.add(new Componente());
        comps_arq.get(5).setAll("10", 5.0, 99.5, 0.35568, "PowerStrip");//ao ativar esta linha comenta a linha 50 e 51 - sts
        //comps_arq.add(new Componente());
        //comps_arq.get(8).setAll("11", 5.0, 99.5, 0.35568, "GeneratorGroup"); //ao ativar esta linha comenta a linha 59 e 60 posição da lista deve respeitar a ordem e descomentar o sts (se tiver comentado). */
        AlgNSGA2 alg = new AlgNSGA2(comps_arq, 10, 7000, 0.4, 1, 10);
    }
    
    public AlgNSGA2(ArrayList<Componente> comps_arq,int qtdCromo, int period, double ec, int de, int eras) {
        //marca o momento que o algoritmo começa a rodar
        Calendar data = Calendar.getInstance();
        dia1 = data.get(Calendar.DAY_OF_YEAR);
        hora1 = data.get(Calendar.HOUR_OF_DAY);
        min1 = data.get(Calendar.MINUTE);
        seg1 = data.get(Calendar.SECOND);
        this.tempo1 = ""+dia1+" , " + hora1 + " : " + min1 + " : " + seg1;
        System.out.println("começou em " + tempo1);
        //variaveis globais recebendo seus valores vindos da interface grafica
        this.comps_arq = comps_arq;//
        this.qtdCromo = qtdCromo;
        this.period = period;
        this.ec = ec;
        this.de = de;
        
        //inicio da execucao do algoritmo
        this.lerComponentes();//le e preenche o dicionario de componentes
        this.gerarCromossomos();//gera a populacao inicial
        ranqueamento += "rank iniciais\n" + this.ranquear();//ranquear a populacao
        this.percorrerEpocas(eras);//percorre as geracoes
    }
    
    public AlgNSGA2(ArrayList<Componente> comps_arq, String script,int qtdCromo, int period, double ec, int de, int eras) {
        //marca o momento que o algoritmo começa a rodar
        this.script = script;
        Calendar data = Calendar.getInstance();
        dia1 = data.get(Calendar.DAY_OF_YEAR);
        hora1 = data.get(Calendar.HOUR_OF_DAY);
        min1 = data.get(Calendar.MINUTE);
        seg1 = data.get(Calendar.SECOND);
        this.tempo1 = ""+dia1+" , " + hora1 + " : " + min1 + " : " + seg1;
        System.out.println("começou em " + tempo1);
        //variaveis globais recebendo seus valores vindos da interface grafica
        this.comps_arq = comps_arq;//
        this.qtdCromo = qtdCromo;
        this.period = period;
        this.ec = ec;
        this.de = de;
        
        //inicio da execucao do algoritmo
        this.lerComponentes();//le e preenche o dicionario de componentes
        this.gerarCromossomos();//gera a populacao inicial
        ranqueamento += "rank iniciais\n" + this.ranquear();//ranquear a populacao
        this.percorrerEpocas(eras);//percorre as geracoes
    }
    
    private void percorrerEpocas(int eras){
        ArrayList<Cromossomo> nova_pop;
        
        int cont = 0;
        double mediasDisp[]= new double[3]; 
        double mediasCusto[]= new double[3];
        double mediasExergia[] = new double[3];
        mediasDisp[0] = 0;
        mediasDisp[1] = 0;
        mediasDisp[2] = 0;
        mediasCusto[0] = 0;
        mediasCusto[1] = 0;
        mediasCusto[2] = 0;
        mediasExergia[0] = 0;
        mediasExergia[1] = 0;
        mediasExergia[2] = 0;
        
        boolean convergiuDisp = false;
        boolean convergiuCusto = false;
        boolean convergiuExergia = false;
        double media;
        
        for (Map.Entry rank : dicRank.entrySet()) {//calcula a crowd distance
            this.crowdDist((ArrayList<Cromossomo>) rank.getValue());
        }
        try{
            String dado_i = "";
            FileWriter arq = new FileWriter("NSGA2 -" + this.arq+ " - pop_ini_NSGA2.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            for (Cromossomo c : cromossomos){
                cont = 0;
                while (cont < c.getGenes().size()) {
                    dado_i += c.getGenes().get(cont).getTipoComp();
                    dado_i += c.getGenes().get(cont).getId() + " - ";
                    cont++;
                }
                dado_i += c.getCrowdDist() + "\n";
            }
            gravarArq.printf(dado_i);
            arq.close();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        cont = 0;
        while (cont < eras){
            
            //System.out.println("geracao" + cont);
            for (Map.Entry rank : dicRank.entrySet()) {//calcula a crowd distance
                this.crowdDist((ArrayList<Cromossomo>) rank.getValue());
            }
            //System.out.println("Ord crowd");
            nova_pop = new ArrayList<>();
            this.gerarFilhos();//gera os filhos e junta com os pais
            //System.out.println("rt");
            
            this.ranquear();//ranqueia a pop Rt (pais + filhos)
            
            //pegando os melhores da Rt
            ranqueamento += "\n\ngeracao " + (++cont) + "\n";
            for (int i = 1; i < this.qtdCromo+1; i++){
                //enquanto um rank puder ser adicionado completamente, entra no if
                //caso contrario entra no else e os melhores são adicionados (rank esta em ordem crescente)
                if (dicRank.get(Integer.toString(i)).size() + nova_pop.size() <= this.qtdCromo){
                    for (Cromossomo c : dicRank.get(Integer.toString(i))){
                        nova_pop.add(c);
                        ranqueamento += c.getDados();
                    }
                }else{
                    Cromossomo[] ultimos = this.crowdDist(dicRank.get(Integer.toString(i)));
                    int n = ultimos.length;
                    while (nova_pop.size() < this.qtdCromo){
                        nova_pop.add(ultimos[n-1]);
                        ranqueamento += nova_pop.get(nova_pop.size()-1).getDados();
                        n--;
                    }
                    break;
                }
            }
            cromossomos = nova_pop;
            
            /* bloco do metodo de convergencia
            //System.out.println("fim geracao" + cont);
            //terminou uma geracao!!!
            //Calculando o desvio padrao da disponibilidade das 3 ultimas gerações.
            media = this.getMediaDisp();
            //System.out.println("media atual " + media);
            mediasDisp[0] = mediasDisp[1];
            mediasDisp[1] = mediasDisp[2];
            mediasDisp[2] = media;
            convergiuDisp = this.calcDevioPadrao(mediasDisp); 
           // System.out.println("disp " + media);
            
            //Calculando o desvio padrao da disponibilidade das 3 ultimas gerações.
            media = this.getMediaCusto();
            //System.out.println("media atual " + media);
            mediasCusto[0] = mediasCusto[1];
            mediasCusto[1] = mediasCusto[2];
            mediasCusto[2] = media;
            convergiuCusto = this.calcDevioPadrao(mediasCusto);
            //System.out.println("custo " + media);
            
            //Calculando o desvio padrao da disponibilidade das 3 ultimas gerações.
            media = this.getMediaExergia();
            //System.out.println("media atual " + media);
            mediasExergia[0] = mediasExergia[1];
            mediasExergia[1] = mediasExergia[2];
            mediasExergia[2] = media;
            convergiuExergia = this.calcDevioPadrao(mediasExergia);
            //System.out.println("exergia " + media);
            //System.out.println("desvio padrão " + convegiu);
            
            System.out.println("disp" + convergiuDisp +" custo "+ convergiuCusto +" exergia "+ convergiuExergia);
            
            if (convergiuDisp && convergiuCusto && convergiuExergia){
                System.out.println("convegiu em " + cont + " geracoes");
                break;
            }*/
            
        }
        
        
        try {
            FileWriter arqR = new FileWriter("NSGA2 -" + this.arq+ " - todosRanks.txt");
            PrintWriter gravarArqE = new PrintWriter(arqR);
            
            gravarArqE.printf(this.ranqueamento);
            arqR.close();
            
            FileWriter arq = new FileWriter("NSGA2 -" + this.arq+ " - resultadosNSGA2-" + script.substring(0, 3) + ".txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            //gravarArq.printf("ups - sdt - subp - ps - crowd");
            String dado = "";
            double maior = 0;
            double disp = 0;
            double custo=0;
            double exer=0;
            String max = "";
            double atual;
            for (Cromossomo c : cromossomos) {
                dado = "";
                cont = 0;
                atual = c.getCrowdDist();
                
                while (cont < c.getGenes().size()){
                    dado += c.getGenes().get(cont).getTipoComp();
                    dado += c.getGenes().get(cont).getId() + " - ";
                    cont ++;
                }
                dado += c.getCrowdDist();
                gravarArq.printf("\n" + dado);
                if (atual> maior){
                    maior = atual;
                    max = dado;
                    disp = c.getAvailability();
                    custo = c.getTotalCost();
                    exer = c.getOperationalExergy();
                }
            }
            System.out.println("maior   " + maior);
            double numNoves = Math.log(1-maior);
            System.out.println("num 9s " + numNoves + " ###  maior " + maior);
            gravarArq.printf("\n\n\nmaior   " + max);
            gravarArq.printf("\ndisp\t" + disp+"\tcusto: "+ custo+"\texer: "+exer);
            gravarArq.printf("\tDisp em num9s   " + (-Math.log10(1-disp)));
            
            Calendar data = Calendar.getInstance();
            dia2 = data.get(Calendar.DAY_OF_YEAR);
            hora2 = data.get(Calendar.HOUR_OF_DAY);
            min2 = data.get(Calendar.MINUTE);
            seg2 = data.get(Calendar.SECOND);
            String tempo2 = ""+dia2+" , "+ +hora2 + " : " + min2 + " : " + seg2;
            duracao = FuncoesFB.calcDuracao( dia1, hora1, min1, seg1 , dia2, hora2, min2, seg2);
                        
            gravarArq.printf("\n\n\ncomecou em: " + tempo1 + "\nfinalizou em: " + tempo2 + "\ndemorou:" + (duracao));
            arq.close();
            System.out.println("\ntempo2 " + tempo2);
            //System.out.println("fim");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
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
                    cromo.calcularMetricas(this.script);//.setScore("Script1.mry");
                    //cromo.calcularMetricasCold(this.script);
                    cont++;
                }
        }catch(Exception ex){
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
            this.dicComp.put("C_Tower_C", new ArrayList<Componente>());
            this.dicComp.put("CRAC", new ArrayList<Componente>());
            //preenche o dicionario com os componentes do arquivo, separando-os por chave
            Componente comp;
            while (br.ready()){
                String lincomp = br.readLine();
                String atb[] = lincomp.split(";");
                comp = new Componente();
                comp.setId(Integer.parseInt(atb[0]));//id do equipamento na lista de equipamentos do mesmo tipo
                comp.setTipoComp(atb[2]);//tipo do equipamento
                comp.setMttf(Double.parseDouble(atb[3]));//tempo de falha
                comp.setEficiencia(Double.parseDouble(atb[4]));//eficiencia
                comp.setPreco(Double.parseDouble(atb[5]));//preco
                
                dicComp.get(atb[2]).add(comp);//adiciona na chave de mesmo tipo
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private ArrayList<Cromossomo> gerarFilhos(){
        ArrayList<Cromossomo> filhos = cromossomos;
        
        for (int i = 0; i <  (int)(this.qtdCromo/2); i++){
            //System.out.println("par" + i + "   filhos");
            cruzarCromossomos(filhos);
        }
        if ((cromossomos.size()%2) == 1){
            try {
                filhos.add(cromossomos.get(0).clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AlgNSGA2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return filhos;
    }
    
    private void cruzarCromossomos(ArrayList<Cromossomo> nova) {
        Cromossomo cromo1 = this.selecaoTorneio();
        Cromossomo cromo2 = this.selecaoTorneio();
        
        cromo1.calcularMetricas(this.script);//.setScore("Script1.mry");
        cromo2.calcularMetricas(this.script);//.setScore("Script1.mry");
        
        //cromo1.calcularMetricasCold(this.script);//.setScore("Script1.mry");
        //cromo2.calcularMetricasCold(this.script);//.setScore("Script1.mry");
        
        ArrayList<Componente> genes;//tem alguma regra quanto o cruzamento em si e mutacao?
        
        genes = (ArrayList<Componente>)(cromo1.getGenes()).clone();
        Cromossomo cruza1 = new Cromossomo(genes, period, ec, de);
        
        genes = (ArrayList<Componente>)(cromo2.getGenes()).clone();
        Cromossomo cruza2 = new Cromossomo(genes, period, ec, de);
        
        int qtdEqp = cromo1.getGenes().size();
        int quebra = this.rand.nextInt((qtdEqp-1));
        //System.out.println("\nquebra" + quebra +"\n");
        int mutacao;
      //  System.out.println("mutacao iniciada");
        try{
            while (quebra>=0){
                mutacao = this.rand.nextInt(100);
                cruza1.getGenes().set(quebra, cromo2.getGenes().get(quebra).clone());
                if (mutacao<10){//taxa de mutacao
                    //System.out.println("mutacao cromossomo1, gene " + quebra);

                    cruza1.getGenes().set(quebra, this.aplicarMutacao(cruza1.getGenes().get(quebra)));
                }
                
                mutacao = this.rand.nextInt(100);
                cruza2.getGenes().set(quebra, cromo1.getGenes().get(quebra).clone());
                if (mutacao<10){
                    //System.out.println("mutacao cromossomo2, gene " + quebra);

                    cruza2.getGenes().set(quebra, this.aplicarMutacao(cruza1.getGenes().get(quebra)));
                }
            
                quebra--;
            }
           // System.out.println("mutacao terminada");
            cruza1.calcularMetricas(this.script);
            cruza2.calcularMetricas(this.script);
            
            //cruza1.calcularMetricasCold(this.script);
            //cruza2.calcularMetricasCold(this.script);
            
            
            nova.add(cruza1);
            nova.add(cruza2);
            //System.out.println("filhos add");
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
    
    private Cromossomo selecaoTorneio() {
        Cromossomo cromo1 = new Cromossomo();
        Cromossomo cromo2 = new Cromossomo();

        int var = this.rand.nextInt(this.qtdCromo);
        cromo1 = cromossomos.get(var);
        int var2;
        while (true){//tem que reduzir de 2n para n
            var2 = this.rand.nextInt(this.qtdCromo);
            if (!(var2 == var)){
                break;
            }
        }
        cromo2 = cromossomos.get(var2);
        
        Cromossomo saida;
        if (cromo1.getRank() < cromo2.getRank()){
            saida = cromo1;
        }else if (cromo1.getRank() > cromo2.getRank()){
            saida = cromo2;
        }else{
            saida = cromo2;
            if (cromo1.getCrowdDist() > cromo2.getCrowdDist()){
                saida = cromo1;
            }
        }//System.out.println("pai selecionado");
        return saida;
    }
    
    private String ranquear(){//aplicacao do non-dominated sort
        
        int qtd_nao_ranqueados = cromossomos.size();//System.out.println("tam pop" + qtd_nao_ranqueados);
        int rank = 0;
        int atual = 0;
        boolean objetivo = false;
        int rival = 0;
        ArrayList<Cromossomo> pop_atual = cromossomos;
        ArrayList<Cromossomo> restantes;
        String dados = "", teste = "";
        int empates;
        ArrayList<Cromossomo> rankeados;
        while (qtd_nao_ranqueados > 0){
            rank += 1;
            rankeados = new ArrayList<>();
            restantes = new ArrayList<>();
            for (Cromossomo c_atual : pop_atual){
                objetivo = false;
                
                //c_atual.setAvailability(1);
                //c_atual.setTotalCost(1);
                //c_atual.setOperationalExergy(1);
                for (Cromossomo c_compara : pop_atual){
                    //c_compara.setTotalCost(1);
                    //c_compara.setOperationalExergy(1);
                    empates = 3;
                    rival = 0;//total vitorias comparado
                    atual = 0;//total vitorias atual
                    if (c_atual.getTotalCost() > c_compara.getTotalCost()) {
                        rival += 1;
                        empates -=1;
                    }
                    else if (c_atual.getTotalCost() < c_compara.getTotalCost()) {
                        atual +=1;
                        empates -=1;
                    }
                    //ajustar para que seja dito se é funcao de max ou min
                    if (c_atual.getAvailability() < c_compara.getAvailability()) {
                        rival += 1;
                        empates -=1;
                    }
                    else if (c_atual.getAvailability() > c_compara.getAvailability()){
                        atual +=1;
                        empates -=1;
                    }
                    if (c_atual.getOperationalExergy() > c_compara.getOperationalExergy()) {//se o atual é melhor
                        rival += 1;
                        empates -=1;
                    }
                    else if (c_atual.getOperationalExergy() < c_compara.getOperationalExergy()){
                        atual +=1;
                        empates -=1;
                    }
                    
                    //if (rival > atual){//se o atual perdeu mais que ganhou de no minimo 1 outro
                    boolean atual_domina = ((atual + empates) == 3);
                    boolean rival_domina = ((rival + empates) == 3);
                    if (atual_domina){
                        objetivo = true;
                    }                   
                    else if (rival_domina){
                        objetivo = false;
                        restantes.add(c_atual);
                        break;
                    }
                    else if (!(atual_domina) && !(rival_domina)){
                        objetivo = true;
                    }
                    
                    //System.out.println("\nind " + c_atual + " comp " + (rival > atual) + "  rival  " + rival + "   atual " + atual);
                }
                //System.out.println("objtivo     " + objetivo);
                if (objetivo) {
                    //System.out.println("passou");
                    c_atual.setRank(rank);
                    rankeados.add(c_atual);
                    dados += c_atual.getDados() ;
                    qtd_nao_ranqueados -= 1;
                }
                
            }
            //uma colecao de ranks, onde cada rank é uma colecao de individuos daquele rank
            //crowd é via rank, isso facilitaria ja que todos ja estariam separados
            dicRank.put(Integer.toString(rank), rankeados);//montar o dicionario de ranks, que facilita na hora do crowd
            
            pop_atual = restantes;
        }
        
        try {
            FileWriter arq = new FileWriter("NSGA2 -" + this.arq+ " - ranques.txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("rank - op_exegergy - total_cost - availability\n");
            
            gravarArq.printf(dados);
            arq.close();
            //System.out.println("fim");
        } catch (IOException ex) {
            ex.printStackTrace();
        }//talvez seja por rank mesmo, mas nada deixa isso explicito
        return dados;
    }
    
    private Cromossomo[] crowdDist(ArrayList<Cromossomo> rank_atual){//recebe por rank
        double crowdD = 0;//pop = 20, r1 = 2, r2 = 7, r3 = 10, r4 = 20, r....
        for (Cromossomo c : rank_atual){
            c.setCrowdDist(crowdD);
        }
        int tam = rank_atual.size();
        Cromossomo[] pop_ord = new Cromossomo[tam];//array tamanho da pop
        double anterior, proximo, primeiro, ultimo;
        String dados = "---------------------------\ncrowd\n";
        try {
            FileWriter arq = new FileWriter("NSGA2 -" + this.arq+ " - ord valores.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            
            
            //ordenar por disponibilidade
            for (int c = 0;c < tam; c++){//insercao dos individuos no array
                pop_ord[c] = rank_atual.get(c);
            }
            quickSortDisp(pop_ord, 0, tam-1);//ordena por disp
            
            gravarArq.printf("\n\ndisponibilidade ordenado"+ "\n");
            for (Cromossomo c : pop_ord){
                gravarArq.printf(c.getAvailability() + "\n");//escreve num arquivo
            }//grava depois de ordenado

            //set dos extremos no infinito
            pop_ord[0].setCrowdDist(Double.MAX_VALUE);//primeiro da ordem
            pop_ord[tam - 1].setCrowdDist(Double.MAX_VALUE);//ultimo da ordem

            //valores para o calculo
            primeiro = pop_ord[0].getAvailability();//max ou funcao(primeiro)
            ultimo = pop_ord[tam - 1].getAvailability();//min ou funcao(ultimo)

            //dados += pop_ord[0].getCrowdDist() + "\n";
            
            for (int i = 1; i < (pop_ord.length - 1); i++) {
                anterior = pop_ord[i - 1].getAvailability();//anterior ao atual
                proximo = pop_ord[i + 1].getAvailability();//
                pop_ord[i].setCrowdDist(pop_ord[i].getCrowdDist() + ((proximo - anterior) / (ultimo - primeiro)));
            }
            quickSortCrowd(pop_ord, 0, tam-1);
            
            //ordenar por custo total
            for (int c = 0;c < tam; c++){//insercao dos individuos no array
                pop_ord[c] = rank_atual.get(c);
            }
            quickSortCusto(pop_ord, 0, tam-1);//ordena por custo
            gravarArq.printf("\n\ncusto ordenado"+ "\n");
            for (Cromossomo c : pop_ord){
                gravarArq.printf(c.getTotalCost()+ "\n");//escreve num arquivo
            }//
            
            //set dos extremos no infinito
            pop_ord[0].setCrowdDist(Double.MAX_VALUE);//primeiro da ordem
            pop_ord[tam - 1].setCrowdDist(Double.MAX_VALUE);//ultimo da ordem

            //valores para o calculo
            primeiro = pop_ord[0].getTotalCost();//max ou funcao(primeiro)
            ultimo = pop_ord[tam - 1].getTotalCost();//min ou funcao(ultimo)

            //dados += pop_ord[0].getCrowdDist() + "\n";
            //int tam = pop_ord.length;
            for (int i = 1; i < (pop_ord.length - 1); i++) {
                anterior = pop_ord[i - 1].getTotalCost();//anterior ao atual
                proximo = pop_ord[i + 1].getTotalCost();//
                pop_ord[i].setCrowdDist(pop_ord[i].getCrowdDist() + ((proximo - anterior) / (ultimo - primeiro)));
            }
            quickSortCrowd(pop_ord, 0, tam-1);//ordenado
            
            //ordenar por op-exergy
            for (int c = 0;c < tam; c++){//insercao dos individuos no array
                pop_ord[c] = rank_atual.get(c);
            }
            quickSortExergia(pop_ord, 0, tam-1);//ordena por exergia
            
            gravarArq.printf("\n\nexergia ordenado" + "\n");
            for (Cromossomo c : pop_ord) {
                gravarArq.printf(c.getOperationalExergy() + "\n");//escreve num arquivo
            }
            //set dos extremos no infinito
            pop_ord[0].setCrowdDist(Double.MAX_VALUE);//primeiro da ordem
            pop_ord[tam - 1].setCrowdDist(Double.MAX_VALUE);//ultimo da ordem

            //valores para o calculo
            primeiro = pop_ord[0].getOperationalExergy();//max ou funcao(primeiro)
            ultimo = pop_ord[tam - 1].getOperationalExergy();//min ou funcao(ultimo)

            for (int i = 1; i < (pop_ord.length - 1); i++) {
                anterior = pop_ord[i - 1].getOperationalExergy();//anterior ao atual
                proximo = pop_ord[i + 1].getOperationalExergy();//
                pop_ord[i].setCrowdDist(pop_ord[i].getCrowdDist() + ((proximo - anterior) / (ultimo - primeiro)));
            }
            quickSortCrowd(pop_ord, 0, tam-1);
            
            for (Cromossomo c : pop_ord){
                dados += c.getCrowdDist() + "\n";//escreve num arquivo
            }
            gravarArq.printf(dados);
            arq.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return pop_ord; 
    }
    
    private static void quickSortDisp(Cromossomo[] vetor, int inicio, int fim) {//vetor = pop, 
        if (inicio < fim) {
            int posicaoPivo = separarDisp(vetor, inicio, fim);
            quickSortDisp(vetor, inicio, posicaoPivo - 1);//separa a medade esquerda
            quickSortDisp(vetor, posicaoPivo + 1, fim);//separa a metade direita
        }
    }

    private static int separarDisp(Cromossomo[] vetor, int inicio, int fim) {//retorna a posicao do pivor
        Cromossomo pivo = vetor[inicio];//pivo é o valor na pos 0 (valor na posicao inicial)
        int i = inicio + 1, f = fim;//i = posicao depois do pivo, fim = ultima posicao
        while (i <= f) {//enquanto i não chagar no fim do vetor
            if (vetor[i].getAvailability() <= pivo.getAvailability()) {//vai do inicio pra direita
                i++;
            } else if (pivo.getAvailability() < vetor[f].getAvailability()) {//vai do final pra esquerda
                f--;
            } else {//troca dois valores de posicao, os extremos atuais (o mais a direita com o mais a esquerda)
                Cromossomo troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;// pivo é colocado no meio, eu acho
        return f;
    }
    
    private static void quickSortCusto(Cromossomo[] vetor, int inicio, int fim) {//vetor = pop, 
        if (inicio < fim) {
            int posicaoPivo = separarCusto(vetor, inicio, fim);
            quickSortCusto(vetor, inicio, posicaoPivo - 1);//separa a medade esquerda
            quickSortCusto(vetor, posicaoPivo + 1, fim);//separa a metade direita
        }
    }

    private static int separarCusto(Cromossomo[] vetor, int inicio, int fim) {//retorna a posicao do pivor
        Cromossomo pivo = vetor[inicio];//pivo é o valor na pos 0 (valor na posicao inicial)
        int i = inicio + 1, f = fim;//i = posicao depois do pivo, fim = ultima posicao
        while (i <= f) {//enquanto i não chagar no fim do vetor
            if (vetor[i].getTotalCost() <= pivo.getTotalCost()) {//vai do inicio pra direita
                i++;
            } else if (pivo.getTotalCost() < vetor[f].getTotalCost()) {//vai do final pra esquerda
                f--;
            } else {//troca dois valores de posicao, os extremos atuais (o mais a direita com o mais a esquerda)
                Cromossomo troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;// pivo é colocado no meio, eu acho
        return f;
    }
    
    private static void quickSortExergia(Cromossomo[] vetor, int inicio, int fim) {//vetor = pop, 
        if (inicio < fim) {
            int posicaoPivo = separarExergia(vetor, inicio, fim);
            quickSortExergia(vetor, inicio, posicaoPivo - 1);//separa a medade esquerda
            quickSortExergia(vetor, posicaoPivo + 1, fim);//separa a metade direita
        }
    }

    private static int separarExergia(Cromossomo[] vetor, int inicio, int fim) {//retorna a posicao do pivor
        Cromossomo pivo = vetor[inicio];//pivo é o valor na pos 0 (valor na posicao inicial)
        int i = inicio + 1, f = fim;//i = posicao depois do pivo, fim = ultima posicao
        while (i <= f) {//enquanto i não chagar no fim do vetor
            if (vetor[i].getOperationalExergy() <= pivo.getOperationalExergy()) {//vai do inicio pra direita
                i++;
            } else if (pivo.getOperationalExergy() < vetor[f].getOperationalExergy()) {//vai do final pra esquerda
                f--;
            } else {//troca dois valores de posicao, os extremos atuais (o mais a direita com o mais a esquerda)
                Cromossomo troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;// pivo é colocado no meio, eu acho
        return f;
    }
    
    private static void quickSortCrowd(Cromossomo[] vetor, int inicio, int fim) {//vetor = pop, 
        if (inicio < fim) {//o mais problematico seria como identificar o que temos de ordenar
            int posicaoPivo = separarCrowd(vetor, inicio, fim);
            quickSortCrowd(vetor, inicio, posicaoPivo - 1);//separa a medade esquerda
            quickSortCrowd(vetor, posicaoPivo + 1, fim);//separa a metade direita
        }
    }

    private static int separarCrowd(Cromossomo[] vetor, int inicio, int fim) {//retorna a posicao do pivor
        Cromossomo pivo = vetor[inicio];//pivo é o valor na pos 0 (valor na posicao inicial)
        int i = inicio + 1, f = fim;//i = posicao depois do pivo, fim = ultima posicao
        while (i <= f) {//enquanto i não chagar no fim do vetor
            if (vetor[i].getCrowdDist()<= pivo.getCrowdDist()) {//vai do inicio pra direita
                i++;
            } else if (pivo.getCrowdDist() < vetor[f].getCrowdDist()) {//vai do final pra esquerda
                f--;
            } else {//troca dois valores de posicao, os extremos atuais (o mais a direita com o mais a esquerda)
                Cromossomo troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }//sss
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;// pivo é colocado no meio, eu acho
        return f;
    }
    
    private double getMediaDisp() {//isso aqui é pra convergencia
        double media = 0;
        for (Cromossomo c : cromossomos) {
            media += c.getAvailability();
        }
        return (media/cromossomos.size());
    }
    
     private double getMediaCusto() {//isso aqui é pra convergencia
        double media = 0;
        for (Cromossomo c : cromossomos) {
            media += c.getTotalCost();
        }
        return (media/cromossomos.size());
    }
     
      private double getMediaExergia() {//isso aqui é pra convergencia
        double media = 0;
        for (Cromossomo c : cromossomos) {
            media += c.getOperationalExergy();
        }
        return (media/cromossomos.size());
    }
    
    //modificar este metodo para avaliar melhor.
    private boolean calcDevioPadrao(double[] medias) { 
        double media = (medias[0] + medias[1] + medias[2])/3;
        double val1, val2, val3;
        val1 = Math.pow(medias[0] - media,2);
        val2 = Math.pow(medias[1] - media,2);
        val3 = Math.pow(medias[2] - media,2);
        double desvio = Math.sqrt((val1 + val2 + val3)/3);
        //System.out.println("media " + media + " desvio " + desvio);
        if (desvio == 0){
            //System.out.println("zero\n\n");
            return true;
        }
        boolean b1, b2, b3;
        b1 = (medias[0] <= (media+desvio)) && (medias[0] >= (media-desvio));
        b2 = (medias[1] <= (media+desvio)) && (medias[1] >= (media-desvio));
        b3 = (medias[2] <= (media+desvio)) && (medias[2] >= (media-desvio));
        
        /*b1 = !((medias[0] >= (media+desvio)) || (medias[0] <= (media-desvio)));
        b2 = !((medias[1] >= (media+desvio)) || (medias[1] <= (media-desvio)));
        b3 = !((medias[2] >= (media+desvio)) || (medias[2] <= (media-desvio)));*/
        /*System.out.println("m1 " + medias[0] + " m2 " + medias[1] + " m3 " + medias[2]);
        System.out.println("b1 " + b1 + " b2 " + b2 +" b3 " + b3);*/
        
        return (b1 && b2 && b3);
    }
}

//geramos a populacao inicial
    //ranqueamos a pop
    //geramos os filhos por torneio, melhor via rank e depois crowd
    //add os filhos na pop 
    //ranquear a nova pop (pais + filhos)
    //pegar os melhores (metade pop), melhores ranks e depois crowd pro rank pior que possui melhores individuos
    //repetir o processo
    
    
    //passos do crowd
    //para cada individuo da pop crowd = 0
    //para cada funcao, ord pop pela funcao
        //setar o i0 e o if = infinito
        //calcular o crowd dos restantes
