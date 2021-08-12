package GeradoresScript;

import com.mxgraph.model.mxCell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import view.Componente;
import view.JanelaScripts;

public class GerarManutPrev {    
    private static String metrica;
    private static String Script_meio;
    private static ArrayList<String> listaCaminhos;
    
    private static String Script_inicio = ("SPN ModelSPN{\n" +
            "\n" +
            "	place inicio( tokens= 1 );\n" +
            "	place ano1;\n" +
            "	place ano2;\n" +
            "	place ano3;\n" +
            "	place ano4;\n" +
            "	place ano5;\n" +
            "\n" +
            "	place eqpmanut( tokens= 1 );\n" );
    
    private static String Script_fim = ("}\");\n}\n"
            + "\n"
            + "main {\n"
            + "	av = solve( ModelSPN,av );\n"
            + "	println(av);\n"
            + "\n"
            + "}");
    
    private static String gerarPlace(String id, Componente comp_atual){
        String place = "	place P" + id + "on";
        place += "( tokens= 1 );\n	place P" + id + "off;\n";
        place += "	place repararP"+ id + ";\n";
        
        String mttf = Double.toString(comp_atual.getMttf());
        String mttr = Double.toString(comp_atual.getMttr());
        
        String time = "	timedTransition failureP" + id + "(\n"
                + "		inputs = [P" + id + "on],\n"
                + "		outputs = [P" + id + "off],\n"
                + "		delay = \"" + mttf + "-((#ano1*0.1)+(#ano2*0.2)+(#ano3*0.3)+(#ano4*0.4)+(#ano5*0.5))*" + mttf +"\"\n" + "	);\n" + "\n";
        
        time += "	immediateTransition imm"+ id +"(\n" +
                "		inputs = [P" + id + "off, eqpmanut],\n" +
                "		outputs = [repararP"+ id + "]\n" +
                "	);\n\n";
                
        time += "	timedTransition repairP" + id + "(\n"
                + "		inputs = [repararP" + id + "],\n"
                + "		outputs = [eqpmanut, P" + id + "on],\n"
                + "		delay = " + mttr + "\n" + "	);\n" + "\n";
        
        Script_meio += time;
        
        return (place);
    }
    
    private static String gerarPlace(String id, Componente comp_atual, String redundantes){
        String place = "	place P" + id + "on";
        place += "( tokens= 1 );\n	place P" + id + "off;\n";
        place += "	place repararP"+ id + ";\n";
        place += "	place repararP"+ id + "prev;\n";
        
        String mttf = Double.toString(comp_atual.getMttf());
        String mttr = Double.toString(comp_atual.getMttr());
        
        String time = "	timedTransition failureP" + id + "(\n"
                + "		inputs = [P" + id + "on],\n"
                + "		outputs = [P" + id + "off],\n"
                + "		delay = \"" + mttf + "-((#ano1*0.1)+(#ano2*0.2)+(#ano3*0.3)+(#ano4*0.4)+(#ano5*0.5))*" + mttf +"\"\n" + "	);\n" + "\n";
        
        time += "	immediateTransition imm"+ id +"(\n" +
                "		inputs = [P" + id + "off, eqpmanut],\n" +
                "		outputs = [repararP"+ id + "]\n" +
                "	);\n\n";
                
        time += "	timedTransition repairP" + id + "(\n"
                + "		inputs = [repararP" + id + "],\n"
                + "		outputs = [eqpmanut, P" + id + "on],\n"
                + "		delay = " + mttr + "\n" + "	);\n" + "\n";
        
        time += "	timedTransition iniPrevP" + id + "(\n"
                + "		inputs = [P" + id + "on, eqpmanut" + redundantes + "],\n"
                + "		outputs = [repararP"+ id + "prev" + redundantes + "],\n"
                + "		delay = \"" + mttf + "-((#ano1*0.1)+(#ano2*0.2)+(#ano3*0.3)+(#ano4*0.4)+(#ano5*0.5))*" + mttf +"\"\n" + "	);\n" + "\n";
        
        time += "	timedTransition fimPrevP" + id + "(\n"
                + "		inputs = [repararP"+ id + "prev],\n"
                + "		outputs = [eqpmanut, P" + id + "on],\n"
                + "		delay = " + mttr + "\n" + "	);\n" + "\n";
        Script_meio += time;
        
        return (place);
    }
    
    private static void varrer(String anterior, mxCell vert_atual, String caminho){
        
        String id_atual = vert_atual.getId();
        
        anterior += "(#P" + id_atual + "on=1)";
        
        caminho += "P" + id_atual + "on";
        int cont = 0;
        int max = vert_atual.getEdgeCount();
        boolean veri = true;
        
        while (cont < max){
            mxCell edge = (mxCell)vert_atual.getEdgeAt(cont);
            mxCell target = (mxCell)edge.getTarget();
            String source = edge.getSource().getId();
            if(source == id_atual){
                varrer(anterior + "AND", target, caminho + ",");
                veri = false;
            }
            cont ++;
        }
        
        if (veri == true){
            
            metrica += "(" + anterior + ")OR";
            listaCaminhos.add(caminho + ",");
        }
    }
    
    public static String gerarScript(Object[] vertices){
        Script_meio = "\n	timedTransition TE1(\n" +
                "		inputs = [inicio],\n" +
                "		outputs = [ano1],\n" +
                "		delay = 8760.0\n" +
                "	);\n" +
                "\n" +
                "	immediateTransition TI1(\n" +
                "		enablingFunction = \"()\",\n" +
                "		inputs = [ano1],\n" +
                "		outputs = [inicio]\n" +
                "	);\n" +
                "	timedTransition TE2(\n" +
                "		inputs = [ano1],\n" +
                "		outputs = [ano2],\n" +
                "		delay = 8760.0\n" +
                "	);\n" +
                "\n" +
                "	immediateTransition TI2(\n" +
                "		enablingFunction = \"()\",\n" +
                "		inputs = [ano2],\n" +
                "		outputs = [inicio]\n" +
                "	);\n" +
                "	timedTransition TE3(\n" +
                "		inputs = [ano2],\n" +
                "		outputs = [ano3],\n" +
                "		delay = 8760.0\n" +
                "	);\n" +
                "\n" +
                "	immediateTransition TI3(\n" +
                "		enablingFunction = \"()\",\n" +
                "		inputs = [ano3],\n" +
                "		outputs = [inicio]\n" +
                "	);\n" +
                "	timedTransition TE4(\n" +
                "		inputs = [ano3],\n" +
                "		outputs = [ano4],\n" +
                "		delay = 8760.0\n" +
                "	);\n" +
                "\n" +
                "	immediateTransition TI4(\n" +
                "		enablingFunction = \"()\",\n" +
                "		inputs = [ano4],\n" +
                "		outputs = [inicio]\n" +
                "	);\n" +
                "	timedTransition TE5(\n" +
                "		inputs = [ano4],\n" +
                "		outputs = [ano5],\n" +
                "		delay = 8760.0\n" +
                "	);\n"+
                "	immediateTransition TI5(\n" +
                "		enablingFunction = \"()\",\n" +
                "		inputs = [ano5],\n" +
                "		outputs = [inicio]\n" +
                "	);\n";
        
        metrica = "\n	metric av = stationaryAnalysis( expression = \"P{";
        String saida = "";
        listaCaminhos = new ArrayList();
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell)vertObject;
            String id = vertice.getId();
            
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
            if (veri == true){
                varrer("", vertice, "");
            }
        }
        
        int cont;
        ArrayList<mxCell> veticesRedun = new ArrayList<>();
        String prevPlaces = "";
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell)vertObject;
            String id = vertice.getId();
            cont = -1;
            for (String c: listaCaminhos){
                if (c.contains("P" + id + "on")){
                    cont += 1;
                }
            }
            if (cont+1 == listaCaminhos.size()){
                String bloco = gerarPlace(id, (Componente)vertice.getValue());
                saida += bloco;
                while (cont > -1){
                    listaCaminhos.set(cont, listaCaminhos.get(cont).replace("P" + id + "on" + ",", ""));
                    cont --;
                }
            }else{
                veticesRedun.add(vertice);
                prevPlaces += "(#repararP"+ id + "prev=1)OR";
            }
        }
        for (mxCell vertice:veticesRedun){
            String id = vertice.getId();
            String redundantes = "";
            for (String c: listaCaminhos){
                if (!(c.contains("P" + id + "on"))){
                    for (String r:c.split(",")){
                        if(!(redundantes.contains(r))){
                            redundantes += ", " + r;
                        }
                    }
                }
            }
            String bloco = gerarPlace(id, (Componente)vertice.getValue(), redundantes);
            saida += bloco;
        }
        Script_meio = Script_meio.replace("()" , prevPlaces.substring(0, prevPlaces.length() - 2));
        return (Script_inicio + saida + Script_meio + metrica.substring(0, metrica.length() - 2) + Script_fim + "\n\n" );
    }
    
    public static void imprimirScript(Object[] vertices){
        String texto = gerarScript(vertices);
//        System.out.println(Script_inicio + saida + Script_meio + metrica.substring(0, metrica.length() - 2) + Script_fim);
        JanelaScripts js = new JanelaScripts(null, true);
//        js.setLocationRelativeTo(null);
        js.taScripts.setText(texto);
        js.setVisible(true);
    }
}
