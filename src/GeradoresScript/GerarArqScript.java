package GeradoresScript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.modcs.tools.parser.model.ExecutionRuntime;
import org.modcs.tools.parser.model.Script;
import org.modcs.tools.parser.model.metrics.Metric;

public class GerarArqScript {
    
    
    public File preencherScript(String script){//metodo que preenche o arquivo .mry com o script gerado internamente na ferramenta
        //instancia uma variavel de arquivo no formato do Mercury (mry)
        File arq =new File("calc.mry");
        try{
            arq.createNewFile();//cria o arquivo
            
            BufferedWriter escrever = new BufferedWriter(new FileWriter(arq));//cria um escritor(escrever) e associa o arquivo(mry) a ele
            
            escrever.write(script);//preenche o arquivo(mry) com o script usando o escritor(escrever)
            
            escrever.close();//encerra o escritor(escrever)
        }catch(IOException ex){
            
        }
        
        return arq;
        
    }
    public String calcRBD(String modelo){
        File arq = preencherScript(modelo);//o jar executa até aqui, confimado
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script(arq);//new Script("C:\\Users\\wL\\Documents\\NetBeansProjects\\JavaApplication\\dist\\Script.mry");
        runtime.evaluateScript(script);
        
        double av, rel, mttf, mttr;
        Metric Aval, Reli, MTTR, MTTF;
        
        Aval = runtime.getModel("ModelRBD").getMetric("av");
        av = Aval.solve();
        Reli = runtime.getModel("ModelRBD").getMetric("rel");
        rel = Reli.solve();
        MTTF = runtime.getModel("ModelRBD").getMetric("mttf");
        mttf = MTTF.solve();
        MTTR = runtime.getModel("ModelRBD").getMetric("mttr");
        mttr = MTTR.solve();
        
        String texto = "Availability: " + av +
                "\nReliability: " + rel +
                "\nMean time to failure: " + mttf +
                "\nMean time to repair: " + mttr;
        
        //return texto;
        
        return "chegou e devolveu";
        
    }
    
    public double calcAval(String modelo){
        File arq = preencherScript(modelo);
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script(arq);
        runtime.evaluateScript(script);
        
        double av;
        Metric Aval;
        
        Aval = runtime.getModel("ModelSPN").getMetric("av");
        av = Aval.solve();
        
        return av;
    }
    
    public String calcSPN(String modelo){
        double av = calcAval(modelo);
        String texto = "Availability: " + av ;
        
        return texto;
    }
    
    public String calcEFM(String modelo, double aval, double period, double ec, double de){
        File arq = preencherScript(modelo);
        ExecutionRuntime runtime = new ExecutionRuntime();        
        Script script = new Script(arq);
        runtime.evaluateScript(script);
        runtime.getVariableTable().setValue("aval", aval);
        runtime.getVariableTable().setValue("period", period);
        runtime.getVariableTable().setValue("ec", ec);
        runtime.getVariableTable().setValue("de", de);
        
        double ic,oc, tc, ee, oe, te;
        Metric acCost, opCost, tCost, embExergy, opExergy, tExergy;
        
        acCost = runtime.getModel("ModelEFM").getMetric("ic");
        ic = acCost.solve();
        opCost = runtime.getModel("ModelEFM").getMetric("oc");
        oc = opCost.solve();
        tCost = runtime.getModel("ModelEFM").getMetric("tc");
        tc = tCost.solve();
        
        embExergy = runtime.getModel("ModelEFM").getMetric("ee");
        ee = embExergy.solve();
        opExergy = runtime.getModel("ModelEFM").getMetric("oe");
        oe = opExergy.solve();
        tExergy = runtime.getModel("ModelEFM").getMetric("te");
        te = tExergy.solve();
        
        String texto = "Cost\n" + "======" +
                "\nAcquisition Cost: " + ic +
                "\nOperational Cost: " + oc +
                "\nTotal Cost: " + tc +
                "\n\nExergy\n" + "======" +
                "\nEmbedded Exergy: " + ee +
                "\nOperational Exergy: " + oe +
                "\nTotal Exergy: " + te;
        
        return texto;
    }
    
}
