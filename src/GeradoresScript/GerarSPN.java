package GeradoresScript;

import com.mxgraph.model.mxCell;
import view.Componente;
import view.JanelaScripts;

public class GerarSPN {
    private String metrica;
    private String Script_meio;
    
    private String Script_inicio = ("SPN ModelSPN{\n"
            + "\n" );
    
    private String Script_fim = ("}\");\n}\n"
            + "\n"
            + "main {\n"
            + "	av = solve( ModelSPN,av );\n"
            + "	println(av);\n"
            + "\n"
            + "}");
    
    private String gerarPlace(String id, Componente comp_atual){
        
        String place = "	place P" + id + "on";
        place += "( tokens= 1 );\n	place P" + id + "off;\n";
        
        String mttf = Double.toString(comp_atual.getMttf());
        String mttr = Double.toString(comp_atual.getMttr());
        
        String time = "	timedTransition failureP" + id+ "(\n"
                + "		inputs = [P" + id + "on],\n"
                + "		outputs = [P" + id + "off],\n"
                + "		delay = " + mttf + "\n" + "	);\n" + "\n";
        
        time += "	timedTransition repairP" + id+ "(\n"
                + "		inputs = [P" + id + "off],\n"
                + "		outputs = [P" + id + "on],\n"
                + "		delay = " + mttr + "\n" + "	);\n" + "\n";

        Script_meio += time;
        
        return (place);
    }
    
    private String varrer(String percurso, mxCell vert_atual){
        String id_atual = vert_atual.getId();
        percurso += "(#P" + id_atual + "on=1)";
        String metrica = "";
        int cont = 0;
        int max = vert_atual.getEdgeCount();
        boolean veri = true;
        
        while (cont < max){
            mxCell edge = (mxCell)vert_atual.getEdgeAt(cont);
            mxCell target = (mxCell)edge.getTarget();
            
            if(target.getId() == id_atual){
                metrica += varrer(percurso + "AND", target);
                veri = false;
            }
            cont ++;
        }
        
        if (veri == true){
            percurso = "(" + percurso + ")OR";
            return percurso;
        }
        return metrica;
    }
    
    public String gerarScript(Object[] vertices){
        Script_meio = "\n";
        metrica = "\n	metric av = stationaryAnalysis( expression = \"P{";
        String saida = "";
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell)vertObject;
            String id = vertice.getId();
            
            String bloco = this.gerarPlace(id, (Componente)vertice.getValue());
            
            int cont = 0;
            boolean veri = true;
            while (cont<vertice.getEdgeCount()){
                mxCell edge = (mxCell)vertice.getEdgeAt(cont);
                String target = edge.getTarget().getId();
                
                if (target == id){
                    veri = false;
                }
                cont++;
            }
            saida += bloco;
            
            if (veri == true){
                metrica += varrer("", vertice);
            }
            
        }
        return (Script_inicio + saida + Script_meio + metrica.substring(0, metrica.length() - 2) + Script_fim);
    }
    
    public void imprimirScript(Object[] vertices){
        String texto = this.gerarScript(vertices);
//        System.out.println(Script_inicio + saida + Script_meio + metrica.substring(0, metrica.length() - 2) + Script_fim);
        JanelaScripts js = new JanelaScripts(null, true);
//        js.setLocationRelativeTo(null);
        js.taScripts.setText(texto);
        js.setVisible(true);
    }
}
