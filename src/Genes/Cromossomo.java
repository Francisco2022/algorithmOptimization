package Genes;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.modcs.tools.parser.model.ExecutionRuntime;
import org.modcs.tools.parser.model.Script;
import org.modcs.tools.parser.model.metrics.Metric;
import view.Componente;
public class Cromossomo implements Cloneable{
    ArrayList <Componente> genes;
    double score, totalCost, operationalExergy, availability;
    double scoreCold, redun, totalCostCold, operationalExergyCold, availabilityCold;
    int period;
    double ec;
    int de;
    String ideq;
    private int rank;
    private double crowdDist = 0;
    //private DecimalFormat decimalFormat;

    public Cromossomo(ArrayList<Componente> genes, int period, double ec, int de) {
        this.genes = genes;
        this.score = 0;
        this.period = period;
        this.ec = ec;
        this.de = de;
        //decimalFormat = new DecimalFormat("#.##");
        //decimalFormat.setRoundingMode(RoundingMode.DOWN);
        //this.setScore("Script.mry");
    }

    public Cromossomo() {
    }
    
    public String getDados(){
        
        String dados = "";
        dados += this.rank + "    ";
        dados += this.getOperationalExergy() + "    ";
        dados += this.getTotalCost() + "    ";
        dados += this.getAvailability() + "    \n";
        return dados;
    }
    
    @Override
    public String toString() {
        String nome = "";
        nome += "\nscore = " + this.score;
        nome += ideq;
        //nome += "\nav = " + this.availability*100;
        //nome += "\ntcost = " + this.totalCost/1000;
        //nome += "\nopEn = " + this.operationalExergy;
        
        return nome;
    }
    
    @Override 
    public Cromossomo clone() throws CloneNotSupportedException {
        Cromossomo clone = (Cromossomo)super.clone();
        return clone;
        // Como o Vini disse, é possível usar neste caso (em que se pode usar um "shallow cloning"
        // return super.clone(); que é mais fácil de usar.
    }
    public void setScore(String sript) {
        if(this.score != 0){
            this.score = 0;
        } 
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script( new File(sript));        
        runtime.evaluateScript(script);
        
        
        //double aquisitionCost = 0 ;
        double operacionalCost;
        Componente comp;
        ideq = "ids - ";
        for(int i = 0; i < this.genes.size(); i++){
            comp = this.genes.get(i);
            String idVert = comp.getIdVert();
            //System.out.println("id" + idVert + comp.getTipoComp());
            ideq += comp.getId() + " - ";
            runtime.getVariableTable().setValue("mttf"+idVert, comp.getMttf());
            runtime.getVariableTable().setValue("mttr"+idVert, comp.getMttr());
            
            runtime.getVariableTable().setValue("mp"+idVert, comp.getPoderMax());
            runtime.getVariableTable().setValue("ef"+idVert, comp.getEficiencia());
            runtime.getVariableTable().setValue("rp"+idVert, comp.getPreco());
            runtime.getVariableTable().setValue("emb"+idVert, comp.getEnergia());
            //aquisitionCost += (double)this.genes.get(i).getCost();                   
        }
        //System.out.println(ids);
        Metric Aval = runtime.getModel("ModelRBD").getMetric("av");
        this.availability = Aval.solve();
        /*
        runtime.getVariableTable().setValue("aval", this.availability);
        runtime.getVariableTable().setValue("period", this.period);
        runtime.getVariableTable().setValue("ec", this.ec);
        runtime.getVariableTable().setValue("de", this.de);
        
        Metric TCost = runtime.getModel("ModelEFM").getMetric("tc");
        this.totalCost = TCost.solve();
        Metric opExergy = runtime.getModel("ModelEFM").getMetric("oe");
        this.operationalExergy = opExergy.solve();
        
        */
        
        //Melhorar a funcao objetivo (fitness) para ser multiobjetivo
        this.score = this.availability;
    }
    public double fitness(String script) {
        /*//System.out.println("tc " +  (totalCost/1000000) + "\nav "+ (availability*100) + "\noe" + (operationalExergy/100000));
        //double score = totalCost/1000000 - (availability*100 + operationalExergy/100000);
        this.calcularMetricas(script);
        double availability, operationalExergy, totalCost;
        availability = GerarTodosA1.normalizar(GerarTodosA1.minDispV, GerarTodosA1.maxDispV, this.availability);
        totalCost = GerarTodosA1.normalizar(GerarTodosA1.minCustoV, GerarTodosA1.maxCustoV, this.totalCost);
        operationalExergy = GerarTodosA1.normalizar(GerarTodosA1.minExerV, GerarTodosA1.maxExerV, this.operationalExergy);
        
        this.score=  availability + operationalExergy - totalCost;
        return score;
        */System.out.println("git");
        return 0;
    }
    
    public double fitness() {
        //System.out.println("tc " +  (totalCost/1000000) + "\nav "+ (availability*100) + "\noe" + (operationalExergy/100000));
        //double score = totalCost/1000000 - (availability*100 + operationalExergy/100000);
                
        this.score = (availability - operationalExergy - totalCost);
        return score;
        
    }
    
    public void calcularMetricas(String sript) {
       /* if(this.score != 0){
            this.score = 0;
        } */
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script( new File(sript));        
        runtime.evaluateScript(script);
        
        //double aquisitionCost = 0 ;
        double operacionalCost;
        Componente comp;
        ideq = "ids - ";
        for(int i = 0; i < this.genes.size(); i++){
            comp = this.genes.get(i);
            String idVert = comp.getIdVert();
            //System.out.println("id" + idVert + comp.getTipoComp());
            ideq += comp.getId() + " - ";
            //System.out.println("id" + idVert);
            runtime.getVariableTable().setValue("mttf"+idVert, comp.getMttf());
            runtime.getVariableTable().setValue("mttr"+idVert, comp.getMttr());
            
            runtime.getVariableTable().setValue("mp"+idVert, comp.getPoderMax());
            runtime.getVariableTable().setValue("ef"+idVert, comp.getEficiencia());
            runtime.getVariableTable().setValue("rp"+idVert, comp.getPreco());
            runtime.getVariableTable().setValue("emb"+idVert, comp.getEnergia());
            //aquisitionCost += (double)this.genes.get(i).getCost();                   
        }
        //System.out.println(ids);
        Metric Aval = runtime.getModel("ModelRBD").getMetric("av");
        this.availability = Aval.solve();
        
        runtime.getVariableTable().setValue("aval", this.availability);
        runtime.getVariableTable().setValue("period", this.period);
        runtime.getVariableTable().setValue("ec", this.ec);
        runtime.getVariableTable().setValue("de", this.de);
        
        Metric TCost = runtime.getModel("ModelEFM").getMetric("tc");
        //this.totalCost = TCost.solve();
        Metric opExergy = runtime.getModel("ModelEFM").getMetric("oe");
        this.operationalExergy = opExergy.solve();
        
        System.out.println("passou");
        
        //Melhorar a funcao objetivo (fitness) para ser multiobjetivo
        //this.score = this.availability;//this.fitness();
    }
    
    /*public double fitnessCold() {
        //System.out.println("tc " +  (totalCost/1000000) + "\nav "+ (availability*100) + "\noe" + (operationalExergy/100000));
        //double score = totalCost/1000000 - (availability*100 + operationalExergy/100000);
                
        this.scoreCold =  availabilityCold + operationalExergyCold - totalCostCold;
        return scoreCold;
        
    }
/*   
    public void calcularMetricasCold(String sript) {
       
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script( new File(sript));        
        runtime.evaluateScript(script);
        
        
        //double aquisitionCost = 0 ;
        double operacionalCost;
        Componente comp;
        ideq = "ids - ";
        
        for(int i = 0; i < this.genes.size(); i++){
            comp = this.genes.get(i);
            String idVert = comp.getIdVert();
            //System.out.println("id" + idVert + comp.getTipoComp());
            ideq += comp.getId() + " - ";
            //System.out.println("id" + idVert);
            
                       
            runtime.getVariableTable().setValue("mttf"+idVert, comp.getMttf());
            runtime.getVariableTable().setValue("mttr"+idVert, comp.getMttr());
            
            runtime.getVariableTable().setValue("mp"+idVert, comp.getPoderMax());
            runtime.getVariableTable().setValue("ef"+idVert, comp.getEficiencia());
            runtime.getVariableTable().setValue("rp"+idVert, comp.getPreco());
            runtime.getVariableTable().setValue("emb"+idVert, comp.getEnergia());
            //aquisitionCost += (double)this.genes.get(i).getCost();                   
        }
        //System.out.println(ids);
        Metric RedunCold = runtime.getModel("ModelSPN").getMetric("redun");
        this.redun = RedunCold.solve();
        runtime.getVariableTable().setValue("result",redun);
        Metric Aval = runtime.getModel("ModelRBDCold").getMetric("avCold");
        this.availabilityCold = Aval.solve();
        
        runtime.getVariableTable().setValue("aval", this.availabilityCold);
        runtime.getVariableTable().setValue("period", this.period);
        runtime.getVariableTable().setValue("ec", this.ec);
        runtime.getVariableTable().setValue("de", this.de);
        
        Metric TCost = runtime.getModel("ModelEFM").getMetric("tc");
        this.totalCostCold = TCost.solve();
        Metric opExergy = runtime.getModel("ModelEFM").getMetric("oe");
        this.operationalExergyCold = opExergy.solve();
        System.out.println("exer: " + operationalExergyCold);
        
        //Melhorar a funcao objetivo (fitness) para ser multiobjetivo
        //this.score = this.availability;//this.fitness();
    }
*/
    public void calcularMetricasCold(String sript) {
       
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script( new File(sript));        
        runtime.evaluateScript(script);
        
        
        //double aquisitionCost = 0 ;
        double operacionalCost;
        Componente comp;
        ideq = "ids - ";
        
        for(int i = 0; i < this.genes.size(); i++){
            comp = this.genes.get(i);
            String idVert = comp.getIdVert();
            //System.out.println("id" + idVert + comp.getTipoComp());
            ideq += comp.getId() + " - ";
            //System.out.println("id" + idVert);temos de ver qual o script que está sendo lido
            
                       
            runtime.getVariableTable().setValue("mttf"+idVert, comp.getMttf());
            runtime.getVariableTable().setValue("mttr"+idVert, comp.getMttr());
            
            runtime.getVariableTable().setValue("mp"+idVert, comp.getPoderMax());
            runtime.getVariableTable().setValue("ef"+idVert, comp.getEficiencia());
            runtime.getVariableTable().setValue("rp"+idVert, comp.getPreco());
            runtime.getVariableTable().setValue("emb"+idVert, comp.getEnergia());
            //aquisitionCost += (double)this.genes.get(i).getCost();                   
        }
        //System.out.println(ids);
        Metric RedunCold = runtime.getModel("ModelSPN").getMetric("redun");
        this.redun = RedunCold.solve();
        runtime.getVariableTable().setValue("result",redun);
        Metric Aval = runtime.getModel("ModelRBDCold").getMetric("avCold");
        this.availability = Aval.solve();
        
        runtime.getVariableTable().setValue("aval", this.availability);
        runtime.getVariableTable().setValue("period", this.period);
        runtime.getVariableTable().setValue("ec", this.ec);
        runtime.getVariableTable().setValue("de", this.de);
        
        Metric TCost = runtime.getModel("ModelEFM").getMetric("tc");
        this.totalCost = TCost.solve();
        Metric opExergy = runtime.getModel("ModelEFM").getMetric("oe");
        this.operationalExergy = opExergy.solve();
      //  System.out.println("exer: " + operationalExergy);
        
        //Melhorar a funcao objetivo (fitness) para ser multiobjetivo
        //this.score = this.availability;//this.fitness();
    }
    
    public ArrayList<Componente> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Componente> genes) {
        this.genes = genes;
    }
    
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTotalCost() {
        /*String t_cost = Double.toString(totalCost);
        return Double.parseDouble(t_cost.substring(0, 4));*/
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getOperationalExergy() {
        /*String op_e = Double.toString(operationalExergy);
        return Double.parseDouble(op_e.substring(0, 4));*/
        return operationalExergy;
    }

    public void setOperationalExergy(double operationalExergy) {
        this.operationalExergy = operationalExergy;
    }

    public double getAvailability() {
        //return Double.parseDouble(decimalFormat.format(availability).replace(',', '.'));
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }
    
    public double getTotalCostCold() {
        /*String t_cost = Double.toString(totalCost);
        return Double.parseDouble(t_cost.substring(0, 4));*/
        return totalCostCold;
    }

    public void setTotalCostCold(double totalCost) {
        this.totalCostCold = totalCost;
    }

    public double getOperationalExergyCold() {
        /*String op_e = Double.toString(operationalExergy);
        return Double.parseDouble(op_e.substring(0, 4));*/
        return operationalExergyCold;
    }

    public void setOperationalExergyCold(double operationalExergy) {
        this.operationalExergyCold = operationalExergy;
    }

    public double getAvailabilityCold() {
        //return Double.parseDouble(decimalFormat.format(availability).replace(',', '.'));
        return availabilityCold;
    }

    public void setAvailabilityCold(double availability) {
        this.availabilityCold = availability;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public double getCrowdDist() {
        return crowdDist;
    }

    public void setCrowdDist(double crowdDist) {
        this.crowdDist = crowdDist;
    }

   /* public void atualizaPosicao(int posi_arq, Componente comp) {
        comp.atualizarPsoValues(this.genes.get(posi_arq).getPsoValues());
        
        this.genes.set(posi_arq, comp);
    }*/
}
