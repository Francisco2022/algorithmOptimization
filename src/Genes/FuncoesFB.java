package Genes;

public class FuncoesFB {
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
