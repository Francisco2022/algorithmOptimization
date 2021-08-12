package Genes;

public class FuncoesFB {
    
    public static void main(String[] args) {
        System.out.println("disp fb " + (1-normalizar(0.999998130723709, 0.9999996942493534, 0.9999996636101843)));
        System.out.println("disp nsga2 " + (1-normalizar(0.999998130723709, 0.9999996942493534, 0.9999996730468349)));
        
        
        System.out.println("custo fb " + (normalizar(29700, 42371, 30325)));
        System.out.println("custo nsga2 " + (normalizar(29700, 42371, 30325)));
        
        
        System.out.println("exer fb " + (normalizar(0.2344, 7.0568, 0.2344)));
        System.out.println("exer nsga2 " + (normalizar(0.2344, 7.0568, 0.9454)));
    }
    static double normalizar(double min, double max, double atual) {
        double normal;

        normal = ((atual - min)) / (max - min);

        return normal;
    }

    static double numNove(double disp) {
        double num9;
        num9 = -Math.log10(1 - disp);
        return num9;
    }

    public static int calcDuracao(int dia1, int hora1, int min1, int seg1 ,int dia2, int hora2, int min2, int seg2){
        int duracao;
        if (seg2 < seg1){
            min2-=1;
            seg2 += 60;
        }
        if (min2 < min1){
            hora2-=1;
            min2 += 60;
        }
        if (hora2<hora1){
            dia2-=1;
            hora2+=24;
        }
        
        duracao = (((dia2-dia1)*24+(hora2-hora1))*60+(min2-min1))*60+(seg2-seg1);
        return duracao;
    }
}
