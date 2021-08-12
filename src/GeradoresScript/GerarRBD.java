package GeradoresScript;

import com.mxgraph.model.mxCell;
import view.Componente;
import view.JanelaScripts;

public class GerarRBD {
    private int cont_serie;
    private String Script_meio;
    private String paralelo;
    
    private String Script_inicio = ("t = 100;"
                + "\n"
                + "\n"
                + "\n"
                + "RBD ModelRBD{\n"
                + "\n");
    
    private String Script_fim = (");\n"
                + "\n"
                + "   top p0;\n"
                + "\n"
                + "   metric av = availability;\n"
                + "   metric rel = reliability( time = t );\n"
                + "   metric mttf = mttf;\n"
                + "   metric mttr = mttr;\n"
                + "}\n"
                + "\n"
                + "main{\n"
                + "\n"
                + "	av = solve(ModelRBD, av);\n"
                + "	rel = solve(ModelRBD, rel);\n"
                + "	mttf = solve(ModelRBD, mttf);\n"
                + "	mttr = solve(ModelRBD, mttr);\n"
                + "\n"
                + "	println(\"Availability: \" .. av );\n"
                + "	println(\"Reliability: \" .. rel );\n"
                + "	println(\"Mean time to failure: \" .. mttf );\n"
                + "	println(\"Mean time to repair: \" .. mttr );\n"
                + "}");
    
    
    private String gerarBlock(String comp_id, Componente comp_atual){
        String block = "block b" + comp_id;
        
        String mttf = "( MTTF = " + Double.toString(comp_atual.getMttf());
        String mttr = ", MTTR = " + Double.toString(comp_atual.getMttr());
        
        block += mttf + mttr + ");\n";
        
        return block;
    }
    
    private String gerarBlock(String comp_id){
        String block = "block b" + comp_id;
        
        String mttf = "( MTTF = mttf" + comp_id;
        String mttr = ", MTTR = mttr" + comp_id;
        
        block += mttf + mttr + ");\n";
        
        return block;
    }
    
    private void varrer(String anterior, mxCell vert_atual){
        String id_atual = vert_atual.getId();
        
        anterior += "b" + id_atual + ", ";
        
        int cont = 0;
        int max = vert_atual.getEdgeCount();
        boolean veri = true;
        
        while (cont < max){
            mxCell edge = (mxCell)vert_atual.getEdgeAt(cont);
            mxCell target = (mxCell)edge.getTarget();
            String source = edge.getSource().getId();
            if(source == id_atual){
                varrer(anterior, target);
                veri = false;
            }
            cont ++;
                
        }
        if (veri == true){
            String serie = "s" + cont_serie ;
            Script_meio += "   series " + serie + "(" + anterior.substring(0, anterior.length() - 2) + ");\n";
            paralelo += serie + ",";
            cont_serie ++;
        }
    }
    
    public String gerarScript(Object[] vertices){
        cont_serie = 1;
        Script_meio = "\n";
        paralelo = "\n   parallel p0(";
        
        String saida = "";
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell)vertObject;
            String id = vertice.getId();
            
            String bloco = this.gerarBlock(id, (Componente)vertice.getValue());
            
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
            saida += "   " + bloco;
            
            if (veri == true){
                varrer("", vertice);
            }
            
        }
        
        return (Script_inicio + saida + Script_meio + paralelo.substring(0, (paralelo.length() - 1)) + Script_fim);
    }
    
    public String gerarScript2(Object[] vertices){
        cont_serie = 1;
        Script_meio = "\n";
        paralelo = "\n   parallel p0(";
        String saida = "";
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell)vertObject;
            String id = vertice.getId();
            
            String bloco = this.gerarBlock(id);
            
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
            saida += "   " + bloco;
            
            if (veri == true){
                varrer("", vertice);
            }
            
        }
        return (Script_inicio + saida + Script_meio + paralelo.substring(0, (paralelo.length() - 1)) + 
                ");\n"
                + "\n"
                + "   top p0;\n"
                + "\n"
                + "   metric av = availability;\n}");
    }
    
    public void imprimirScript(Object[] vertices){
        String texto = this.gerarScript(vertices);
        JanelaScripts js = new JanelaScripts(null, true);
        //js.setLocationRelativeTo(null);
        js.taScripts.setText(texto);
        js.setVisible(true);
    }
  
}
