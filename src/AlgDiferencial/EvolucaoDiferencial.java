package AlgDiferencial;

public class EvolucaoDiferencial {
	
	private double[][] populacao=new double [6][3];
	private double [][] aux_populacao=new double [6][3];
	private double [] Vdonor=new double[3];
	private double [] Vu=new double[3];
	private double[] vetorBase=new double [3];;
	private double [][] novaPopulacao=new double [6][3];
	private double[] vetorIndividuo1=new double[3];
	private double[] vetorIndividuo2=new double [3];
	private double donor=0, ld=0, lu=100;
	private double []melhor=new double [3];
	private double pc, pi;

	
	public EvolucaoDiferencial(int tamPop,int dim,double max,double min){
		populacao=new double[tamPop][dim];/*cast pois populacao eh um double e esta recebendo dois int*/
			for(int i=0;i<populacao.length;i++){
				for(int j=0;j<dim;j++){
					populacao[i][j]=Math.random()*(max-min)+min;
				 
			}
		}
		aux_populacao=populacao;
	}
	
	
	public double[][] rodarAlgoritmo(int numIt,double crossover){
			for(int contador=0;contador<numIt;contador++){
				for(int i=0;i<populacao.length;i++) {
					
					int r1=(int) (Math.random()*populacao.length);//cada elemento randomico ele tem que garantir aleatoriedade
					while(r1==i)
						 r1=(int) (Math.random()*populacao.length);
				    vetorBase=aux_populacao[r1].clone();
					int r2=(int) (Math.random()*populacao.length);
					while ((r2==i)||(r2==r1))
						r2=(int)(Math.random()*populacao.length);
					vetorIndividuo1=aux_populacao[r2].clone();
					int r3=(int) (Math.random()*populacao.length);
					while ((r3==i)||(r3==r2)||(r3==r1))
						r3=(int) (Math.random()*populacao.length);
					vetorIndividuo2=aux_populacao[r3].clone();
					//mutacao
					for(int d=0;d<3;d++){
						donor=vetorBase[d]+0.45*(vetorIndividuo2[d]-vetorIndividuo1[d]);
						Vdonor[d]=donor;
						donor=0;}
					pc=Math.random();
					pi=(int)(Math.random()*populacao.length);
					if(pc<=crossover || i==pi) //FAZER AQUI O CROSSOVER ROLETA
						Vu= Vdonor.clone();
					if(pc>crossover && i!=pi)
						Vu=aux_populacao[i].clone();
					if (calcularFitness(Vu)>calcularFitness(aux_populacao[i]))
						novaPopulacao [i]=Vu.clone();
					if (calcularFitness (Vu)<=calcularFitness(aux_populacao[i]))
						novaPopulacao[i]=aux_populacao[i].clone();
				
					check_bound(novaPopulacao[i]);
			
					
			}
				System.arraycopy(novaPopulacao, 0, aux_populacao, 0, novaPopulacao.length); 

			
			}
		
		return novaPopulacao;
	}

	

		public double[][] getNovaPopulacao() {
		return novaPopulacao;
	}

	public void setNovaPopulacao(double[][] novaPopulacao) {
		this.novaPopulacao = novaPopulacao;
	}

		public double calcularFitness(double[] pnormal){//retorna um vetor de fitness
			double fitnessInd=0;
				fitnessInd = Math.pow(pnormal[0], 2)+Math.pow(pnormal[1], 2)+Math.pow(pnormal[2], 2);
			return fitnessInd;	
	
		}
	
	public double[] melhorIndividuo(double [][] pnormalizada){//revisar necessidade deste m�todo
		double []vmelhor=new double[3];

			for(int a=1;a<populacao.length;a++){
				if (calcularFitness(pnormalizada[a-1])>calcularFitness(pnormalizada[a]))
					vmelhor=pnormalizada[a-1].clone();
							}
				
		return check_bound(vmelhor);
		}


	public double[] buscar_maior (double [][] x) {
		double maior=-1;
		x=populacao;
		double []vMaior = new double[3];

		for(int j=0; j<3;j++) {
			for(int i=0; i<x.length;i++) {
				if(x[i][j] > maior)
					maior=x[i][j];						
				}
			vMaior[j]=maior;
			maior=-1;
		}	
	return vMaior;
	}
	
	
	public double[] buscar_menor (double [][] x) {
		double menor = 10000000;
		double [] vMenor = new double [3];
		for(int j=0; j<3;j++) {
			for(int i=0; i<x.length;i++) {
				if(x[i][j]< menor)
					menor=x[i][j];		
				}
			vMenor[j]=menor;
			menor=100000;
		}	
		
	
	return vMenor;
	}
	
	public double[][] getPopulacao() {
		return populacao;
	}
	public void setPopulacao(double[][] populacao) {
		this.populacao = populacao;
	}
	
	
	public double []check_bound(double [] vector){
		for(int a=0;a<vector.length;a++) {
			if(vector[a]<ld) 
				vector[a]=ld;
			if(vector[a]>lu)
				vector[a]=lu;
		}
	return vector;
	}
	
	
	public double [][] normalizarPop(double [][] pop){//normaliza os valores da matriz entre 0 e 1
		double [][] popNormal = new double [6][3];
		double formula;
		double []u = buscar_maior(pop);
		double []l= buscar_menor(pop);
			for(int b=0;b<3;b++) {
				for(int a=0; a<pop.length;a++) {
					formula = (pop[a][b]-l[b])/(u[b]-l[b]);
					popNormal[a][b]=formula;
							}
			
		}
			return popNormal;
		
	}
	public double[] getMelhor() {
		return melhor;
	}

	public void setMelhor(double[] melhor) {
		this.melhor = melhor;
	}

	
}
