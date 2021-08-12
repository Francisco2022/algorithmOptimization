package GeradoresScript;

import com.mxgraph.model.mxCell;
import view.Componente;
import view.JanelaScripts;

public class GerarEFM {
    //inicialização da parte variavel do script
    private String Script_meio;
    
    //inicialização e preenchimentoda parte padrao (inicio e fim) do do script
    private String Script_inicio = ("EFM ModelEFM{\n" +
            "	component SourcePoint1(\n" +
            "		type = \"SourcePoint\",\n" +
            "		parameters = (\n" +
            "			efficiency = 100.0,\n" +
            "			retailPrice = 0.0\n" +
            "		)\n" +
            "	);\n" +
            "\n" +
            "	component TargetPoint1(\n" +
            "		type = \"TargetPoint\",\n" +
            "		parameters = (\n" +
            "			efficiency = 100.0,\n" +
            "			retailPrice = 0.0,\n" +
            "			demandedEnergy = de\n" +
            "		)\n" +
            "	);\n" +
            "\n");
    
    private String Script_fim1 = ("\n	metric ic = initialCost( eletricityCost = ec);\n" +
            "	metric oc = operationalCost( eletricityCost = ec, availability = aval, time = period );\n" +
            "	metric ee = embeddedExergy;\n" +
            "	metric oe = operationalExergy( time = period, availability = aval);\n" +
            "	metric tc(ic + oc);\n" +
            "	metric te(ee + oe);\n}\n" +
            "main{\n");
    private String Script_fim2 = ("	ic = solve(ModelEFM, ic);\n" +
            "	println(\"Acquisition Cost: \" .. ic);\n" +
            "	oc = solve(ModelEFM, oc);\n" +
            "	println(\"Operational Cost: \" .. oc);\n" +
            "	tc = solve(ModelEFM, tc);\n" +
            "	println(\"Total Cost: \" .. tc);\n" +
            "	\n" +
            "	println(\"\");\n" +
            "	\n" +
            "	ee = solve(ModelEFM, ee);\n" +
            "	println(\"Embedded Exergy: \" .. ee);\n" +
            "	oe = solve(ModelEFM, oe);\n" +
            "	println(\"Operational Exergy: \" .. oe);\n" +
            "	te = solve(ModelEFM, te);\n" +
            "	println(\"Total Exergy: \" .. te);\n}");
    
    private String gerarComponent(String comp_id, Componente comp_atual){
        String compTipo = comp_atual.getTipoComp();
        String component = "component " + compTipo + comp_id + "(\n";
        String tipo = "		type = \"" + compTipo + "\",\n" + "		parameters = (\n";

        String maxPower = "			maxPower = " + Double.toString(comp_atual.getPoderMax()) + ",\n";
        String efficiency = "			efficiency = " + Double.toString(comp_atual.getEficiencia()) + ",\n";
        String retailPrice = "			retailPrice = " + Double.toString(comp_atual.getPreco()) + ",\n";
        String embeddedEnergy = "			embeddedEnergy = " + Double.toString(comp_atual.getEnergia()) + "\n";
        
        component += tipo + maxPower + efficiency + retailPrice + embeddedEnergy + "		)\n" + "	);\n" + "\n";
        
        return component;
    }
    
    private String gerarComponent(String comp_id, String comp_tipo){
        String compTipo = comp_tipo;
        String component = "component " + compTipo + comp_id + "(\n";
        String tipo = "		type = \"" + compTipo + "\",\n" + "		parameters = (\n";

        String maxPower = "			maxPower = mp" + comp_id + ",\n";
        String efficiency = "			efficiency = ef" + comp_id + ",\n";
        String retailPrice = "			retailPrice = rp" + comp_id + ",\n";
        String embeddedEnergy = "			embeddedEnergy = emb" + comp_id + "\n";
        
        component += tipo + maxPower + efficiency + retailPrice + embeddedEnergy + "		)\n" + "	);\n" + "\n";
        
        return component;
    }
    
    private String varrer(String anterior,  mxCell vert_atual){
        String id_atual = vert_atual.getId();
        String compTipo = ((Componente) vert_atual.getValue()).getTipoComp();
        
        String atual = compTipo + id_atual;
        String Script_meio = "	arc " + anterior + " -> " + atual + ";\n";
        int cont = 0;
        int max = vert_atual.getEdgeCount();
        boolean veri = true;
        
        while (cont < max){
            mxCell edge = (mxCell)vert_atual.getEdgeAt(cont);
            mxCell target = (mxCell)edge.getTarget();
            String source = edge.getSource().getId();
            if(source == id_atual){
                Script_meio += varrer(atual, target);
                veri = false;
            }
            cont ++;
                
        }
        if (veri == true){
            Script_meio += "	arc " + atual + " -> TargetPoint1;\n";
        }
        return Script_meio;
    }
    
    public String gerarScript(Object[] vertices ){
        Script_meio = "\n";
        
        String saida = "";
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell) vertObject;
            String id = vertice.getId();
            
            String bloco = this.gerarComponent(id, (Componente)vertice.getValue());
            
            int cont = 0;
            boolean veri = true;
            while (cont < vertice.getEdgeCount()){
                mxCell edge = (mxCell) vertice.getEdgeAt(cont);
                String target = edge.getTarget().getId();
                
                if (target == id){
                    veri = false;
                }
                cont++;
            }
            saida += "	" + bloco;
            
            if (veri == true){
                Script_meio += varrer("SourcePoint1", vertice);
            }
            
        }
        return (Script_inicio + saida + Script_meio + Script_fim1 + Script_fim2);
    }
    
    public String gerarScript2(Object[] vertices){
        Script_meio = "\n";
        
        String saida = "";
        for (Object vertObject:vertices){
            mxCell vertice = (mxCell) vertObject;
            String id = vertice.getId();
            
            String bloco = this.gerarComponent(id, ((Componente)vertice.getValue()).getTipoComp());
            
            int cont = 0;
            boolean veri = true;
            while (cont < vertice.getEdgeCount()){
                mxCell edge = (mxCell) vertice.getEdgeAt(cont);
                String target = edge.getTarget().getId();
                
                if (target == id){
                    veri = false;
                }
                cont++;
            }
            saida += "	" + bloco;
            
            if (veri == true){
                Script_meio += varrer("SourcePoint1", vertice);
            }
            
        }
        return (Script_inicio + saida + Script_meio + Script_fim1 +  "\n	av = solve(ModelRBD, av);\n" + Script_fim2);
    }
    
    public void imprimirScript(Object[] vertices){
//        System.out.println(Script_inicio + saida + Script_meio + Script_fim);
        String texto = this.gerarScript(vertices);
//        System.out.println(Script_inicio + saida + Script_meio + metrica.substring(0, metrica.length() - 2) + Script_fim);
        JanelaScripts js = new JanelaScripts(null, true);
//        js.setLocationRelativeTo(null);
        js.taScripts.setText(texto);
        js.setVisible(true);
    }
}
