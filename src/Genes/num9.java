/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Genes;

import org.modcs.tools.efm.analisys.engine.test.Main;

public class num9 {
    public static void main(String[] args) {
        double minDisp = 0.9988891531543046;
        double minCusto = 15048.654382023267;
        double minExer = 0.6112517260407564;
        double maxDisp = 0.999475725897039;
        double maxCusto = 22584.26568928625;
        double maxExer = 5.974806663364583;
        double atual = 0.999036891892746;
        double normal = normalizar(minDisp,maxDisp,atual);
        
        System.out.println("normal\t" + normal);
        
        System.out.println("A4 num9s    " + (-Math.log10(1-0.9999999789838206)));
        System.out.println("A5 num9s    " + (-Math.log(1-0.9999927989458165)));
    }
    
    static double normalizar(double min, double max, double atual){
        double normal;
        
        normal = ((atual-min))/(max-min);
    
        return normal;
    }
    
}
