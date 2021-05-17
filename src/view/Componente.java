package view;

import java.io.Serializable;

public class Componente implements Serializable, Cloneable{

    private String nomeComp;
    private double mttf = 100;
    private double mttr = 8;
    
    private double poderMax = 5.0;
    private double eficiencia = 95.3;
    private double preco = 3600.0;
    private double energia = 3.1392;
    private String tipoComp = "UPS_5kVA";
    
    private static int num = 1;
    private String idVert;
    private int id = 0;
    
    private double velocidade = Math.random();
    private double melhor_posi = 0.0;
    private double posi_atual = this.getMttf();
    

    public Componente() {
        setNumComp(num);
    } 
    
    public void setAll(String idVert,double mttf, double mttr, double poderMax, double eficiencia, double preco, double energia, String tipoComp){
        this.idVert = idVert;
        this.mttf = mttf;
        this.mttr = mttr;
        this.poderMax = poderMax;
        this.eficiencia = eficiencia;
        this.preco = preco;
        this.energia = energia;
        this.tipoComp = tipoComp;
    }
    public void setAll(String idVert, double poderMax, double eficiencia, double energia, String tipoComp){
        this.idVert = idVert;
        this.mttf = mttf;
        this.mttr = mttr;
        this.poderMax = poderMax;
        this.eficiencia = eficiencia;
        this.preco = preco;
        this.energia = energia;
        this.tipoComp = tipoComp;
    }
    
    private void setNumComp(int num){
        nomeComp = "Quadro " + num;
        //this.num++;
    }
    //-------------------------------------------------------------------
    @Override
    public String toString() {
        return nomeComp;
    }
    
    @Override 
    public Componente clone() throws CloneNotSupportedException {
        Componente clone = (Componente)super.clone();
        return clone;
        // Como o Vini disse, é possível usar neste caso (em que se pode usar um "shallow cloning"
        // return super.clone(); que é mais fácil de usar.
    }
    
    public double[] getVetor(){
        double[] vetor = new double[3];
        vetor [0]= this.getPreco();
        vetor [1]= this.getEficiencia();
        vetor [2]= this.getMttf();
        return vetor;
    }
    
    public void changeType(String novo_tipo){
        if (novo_tipo.equals("UPS_5kVA")) {
            this.setTipoComp("UPS_5kVA");
            this.setPoderMax(5.0);
            this.setEficiencia(95.3);
            this.setPreco(3600.0);
            this.setEnergia(3.1392);
            this.setMttf(250000.0);
            this.setMttr(8.0);
        } else if (novo_tipo.equals("UPS_250kVA")) {
            this.setTipoComp("UPS_250kVA");
            this.setPoderMax(250.0);
            this.setEficiencia(95.3);
            this.setPreco(60000.0);
            this.setEnergia(61.03986048);
            this.setMttf(250000.0);
            this.setMttr(8.0);
        } else if (novo_tipo.equals("STS")) {
            this.setTipoComp("STS");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(800.0);
            this.setEnergia(0.35928);
            this.setMttf(240384.62);
            this.setMttr(6.0);
        } else if (novo_tipo.equals("ATS")) {
            this.setTipoComp("ATS");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(800.0);
            this.setEnergia(0.35928);
            this.setMttf(500000.00);
            this.setMttr(0.33);
        } else if (novo_tipo.equals("SDTransformer")) {
            this.setTipoComp("SDTransformer");
            this.setPoderMax(5.0);
            this.setEficiencia(98.5);
            this.setPreco(550.0);
            this.setEnergia(0.35928);
            this.setMttf(1412908.33);
            this.setMttr(156.01);
        } else if (novo_tipo.equals("Subpanel")) {
            this.setTipoComp("Subpanel");
            this.setPoderMax(5.0);
            this.setEficiencia(99.9);
            this.setPreco(200.0);
            this.setEnergia(0.4284);
            this.setMttf(1520000.00);
            this.setMttr(2.4);
        } else if (novo_tipo.equals("PowerStrip")) {
            this.setTipoComp("PowerStrip");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(200.0);
            this.setEnergia(0.35568);
            this.setMttf(11511175.63);
            this.setMttr(3.8);
        } else if (novo_tipo.equals("ACSource")) {
            this.setTipoComp("ACSource");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(200.0);
            this.setEnergia(0.35568);
            this.setMttf(11511175.63);
            this.setMttr(3.8);
        } else if (novo_tipo.equals("GeneratorGroup")) {
            this.setTipoComp("GeneratorGroup");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(200.0);
            this.setEnergia(0.35568);
            this.setMttf(11511175.63);
            this.setMttr(3.8);
        } else if (novo_tipo.equals("GeneratorGroup500")) {
            this.setTipoComp("GeneratorGroup500");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(200.0);
            this.setEnergia(0.35568);
            this.setMttf(11511175.63);
            this.setMttr(3.8);
        } else if (novo_tipo.equals("JunctionBox")) {
            this.setTipoComp("JunctionBox");
            this.setPoderMax(5.0);
            this.setEficiencia(99.5);
            this.setPreco(200.0);
            this.setEnergia(0.35568);
            this.setMttf(11511175.63);
            this.setMttr(3.8);
        }
    }

    public String getIdVert() {
        return idVert;
    }

    public void setIdVert(String idVert) {
        this.idVert = idVert;
    }
    
    public int getId() {
        return id;
    }

    //-------------------------------------------------------------------
    public void setId(int id) {    
        this.id = id;
    }

    public double getMttf() {
        return mttf;
    }

    public void setMttf(double mttf) {
        this.mttf = mttf;
    }

    public double getMttr() {
        return mttr;
    }

    public void setMttr(double mttr) {
        this.mttr = mttr;
    }

    public String getNomeComp() {
        return nomeComp;
    }

    public void setNomeComp(String nomeComp) {
        this.nomeComp = nomeComp;
    }

    public double getPoderMax() {
        return poderMax;
    }

    public void setPoderMax(double poderMax) {
        this.poderMax = poderMax;
    }

    public double getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(double eficiencia) {
        this.eficiencia = eficiencia;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = energia;
    }

    public String getTipoComp() {
        return tipoComp;
    }

    public void setTipoComp(String tipoComp) {
        this.tipoComp = tipoComp;
    }

    public Double[] getPsoValues() {
        Double[] pso_values = new Double[3];
        pso_values[0] = this.velocidade;
        pso_values[1] = this.melhor_posi;
        return pso_values;
    }

    public void atualizarPsoValues(Double[] pso_values) {
        this.velocidade = pso_values[0];
        this.melhor_posi = pso_values[1];
        this.posi_atual = this.mttf;
        if (melhor_posi < this.posi_atual){
            this.melhor_posi = this.posi_atual;
        }
    }
    
}
