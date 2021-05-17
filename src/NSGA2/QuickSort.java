/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NSGA2;

import java.io.IOException;


public class QuickSort {
    public static void main(String[] args) throws IOException {
        int quantidade = 20000;
        int[] vetor = new int[quantidade];
        String dados = "";
        int valor = 0;
        for (int i = 0; i < vetor.length; i++) {
            valor = (int) (Math.random() * 10000);
            vetor[i] = valor;
            dados += valor + ", ";
        }
        long tempoInicial = System.currentTimeMillis();
        quickSort(vetor, 0, vetor.length - 1);//pop, pos_inicial, pos_final
        long tempoFinal = System.currentTimeMillis();//ele vai ter a comparacao entre o AG, FB, DE etc
        String ordenado = "";
        for (int i : vetor){
            ordenado += i + ", ";
        }
        System.out.println("base inicio:   " + dados);
        System.out.println("base ordenada:   " + ordenado);//sem repeticao???
        System.out.println("Executado em = " + (tempoFinal - tempoInicial) + " ms"); 
    }//construido é tipo o tempo que o codigo ficou executando ate terminar

    private static void quickSort(int[] vetor, int inicio, int fim) {//vetor = pop, 
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);
            quickSort(vetor, inicio, posicaoPivo - 1);//separa a medade esquerda
            quickSort(vetor, posicaoPivo + 1, fim);//separa a metade direita
        }
    }

    private static int separar(int[] vetor, int inicio, int fim) {//retorna a posicao do pivor
        int pivo = vetor[inicio];//pivo é o valor na pos 0 (valor na posicao inicial)
        int i = inicio + 1, f = fim;//i = posicao depois do pivo, fim = ultima posicao
        while (i <= f) {//enquanto i não chagar no fim do vetor
            if (vetor[i] <= pivo) {//vai do inicio pra direita
                i++;
            } else if (pivo < vetor[f]) {//vai do final pra esquerda
                f--;
            } else {//troca dois valores de posicao, os extremos atuais (o mais a direita com o mais a esquerda)
                int troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;// pivo é colocado no meio, eu acho
        return f;
    }
}
