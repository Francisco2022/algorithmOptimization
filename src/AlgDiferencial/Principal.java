package AlgDiferencial;


public class Principal {

	public static void main(String[] args) {
		double [][] populAntes= new double [6][3];
		double [][] popul= new double [6][3];
		//double [][] normalizada= new double [6][3];
		double [] melhorInd=new double [3];
		EvolucaoDiferencial ed= new EvolucaoDiferencial(6,3,100,1);
		populAntes=ed.getPopulacao();
		System.out.println("Populacao Inicial: ");

		for(int i=0;i<popul.length;i++) {
			for(int j=0;j<3;j++) {
				System.out.print((populAntes[i][j]+"\t").replace(".",","));

							}
			System.out.println("");

		}
		
		System.out.println("-------------------------------------------------------------------");
		/**normalizada = ed.normalizarPop(populAntes);
		for(int i1=0;i1<popul.length;i1++) {
			for(int j1=0;j1<3;j1++) {
				System.out.print((normalizada[i1][j1]+"\t").replace(".",","));
			}
				System.out.println("");

			}
		System.out.println("-------------------------------------------------------------------");
*/
		melhorInd=ed.melhorIndividuo(populAntes);
		System.out.print("Melhor Individuo da primeira Gera��o: ");
		for(int k=0;k<3;k++) {
			System.out.print("["+ melhorInd[k]+"]");
					}
		System.out.println("");
		System.out.println("Fitness: "+ed.calcularFitness(melhorInd));
		System.out.println("-------------------------------------------------------------------");
		System.out.println("maior vetor: ");
		for(int j=0;j<3;j++) {

		System.out.print(+ed.buscar_maior(populAntes)[j]+"\t");
		}
		System.out.println("");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("menor vetor: ");
		for(int j=0;j<3;j++) {

		System.out.print(+ed.buscar_menor(populAntes)[j]+"\t");
		}
		System.out.println("");
		System.out.println("-------------------------------------------------------------------");


		ed.rodarAlgoritmo(3, 0.5);
		popul = ed.getNovaPopulacao().clone();
		System.out.println("Populacao da ultima Geracao: ");

		for(int i=0;i<popul.length;i++) {
			for(int j=0;j<3;j++) {
				System.out.print((popul[i][j]+"\t").replace(".",","));

							}
			System.out.println("");

		}
		
		System.out.println("-------------------------------------------------------------------");
		melhorInd=ed.melhorIndividuo(popul);
		System.out.print("Melhor Individuo da ultima Gera��o: ");
		for(int k=0;k<3;k++) {
			System.out.print("["+ melhorInd[k]+"]");
		}
		System.out.println("");
		System.out.println("Fitness: "+ed.calcularFitness(melhorInd));

	
	}

	

	}

	


