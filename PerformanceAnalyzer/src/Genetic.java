import java.util.*;
//import java.util.stream.IntStream;

public class Genetic {
	static int g=1;
	static double total_response;
	static double total_avail;
	static double total_through;
	static double total_success;
	static double total_reliability;
	static double total_compliance;
	static double total_bp;
	static double total_latency;
	static double total_docu;
	public static void genetic(List<List> concreteList) {
		//public static ga(double[][]arr2) {
		//20 random solution consist of 4 web services per solution
		int n=10 ; // number of WS for a particular task
		int m=5; //number of tasks
		int pop[][] = new int[n][m];
		//int pop [][]= {{1,121,12,54,33},{2,499,463,32,221},{3,98,34,100,103},{4,2,32,43,121},{5,265,87,94,50},{6,200,43,18,36},{7,27,132,271,327},{8,212,222,32,321},{9,165,25,75,89},{10,321,265,118,412}};
		int new_pop[][] = new int[n][m]; //for new generation
		double []fit=new double[pop.length];
		// double[] fit1=new double[pop.length];
		Random r = new Random();

		// population initialization
		for(int i = 0 ; i < n ; i++)
		{
			for(int j = 0 ; j < m ; j++)
			{
				pop [i][j] = r.nextInt(499);   //from 500 web services random WS will be allocated
			}
		} 
	
		System.out.println("Initialized population is");
		
		for(int i = 0 ; i < n ; i++)
		{
			for(int j = 0 ; j < m ; j++)
			{
				//System.out.print(" "+pop[i][j]);   //from 500 web services random WS will be allocated
			}
		//System.out.println();
		}
		//System.out.println("pop1 :"+pop[0][0]);
		
		//fitness calculation
		do
		{
			System.out.println("Generation: "+g);
			
			for(int i = 0 ; i < n ; i++)
			{
				for(int j = 0 ; j < m ; j++)
				{
					System.out.print(" "+pop[i][j]);   //from 500 web services random WS will be allocated
				}
			System.out.println();
			}
			
			fit=Fitness.fitness(concreteList,n,pop);
	/*	for(int i=0;i<pop.length;i++)
		{
		Concrete con1 = new Concrete();
		Concrete con2 = new Concrete();
		Concrete con3 = new Concrete();
		Concrete con4 = new Concrete();
		Concrete con5 = new Concrete();
		con1 = (Concrete) concreteList.get(0).get(pop[i][0]);
		con2 = (Concrete) concreteList.get(1).get(pop[i][1]);
		con3 = (Concrete) concreteList.get(2).get(pop[i][2]);
		con4 = (Concrete) concreteList.get(3).get(pop[i][3]);
		con5 = (Concrete) concreteList.get(4).get(pop[i][4]);
		*/
		/*total_response =con1.getResponse()+con2.getResponse()+con3.getResponse()+con4.getResponse()+con5.getResponse();
		total_avail=con1.getAvail()*con2.getAvail()*con3.getAvail()*con4.getAvail()*con5.getAvail();
		total_through=con1.getThrough()+con2.getThrough()+con3.getThrough()+con4.getThrough()+con5.getThrough();
		total_success=con1.getSuccess()+con2.getSuccess()+con3.getSuccess()+con4.getSuccess()+con5.getSuccess();
		total_reliability=con1.getReliability()*con2.getReliability()*con3.getReliability()*con4.getReliability()*con5.getReliability();
		total_compliance=con1.getCompliance()+con2.getCompliance()+con3.getCompliance()+con4.getCompliance()+con5.getCompliance();
		total_bp=con1.getBp()+con2.getBp()+con3.getBp()+con4.getBp()+con5.getBp();
		total_latency=con1.getLatency()+con2.getLatency()+con3.getLatency()+con4.getLatency()+con5.getLatency();
		total_docu=con1.getDocu()+con2.getDocu()+con3.getDocu()+con4.getDocu()+con5.getDocu();
		*/
		//since latency and response time are negative values
		//total_latency=1-total_latency;
		//total_response=1-total_response;
		
		//fitness calculation
		//fit[i] = (total_avail+total_through+total_success+total_reliability+total_compliance+total_bp+total_docu)/(total_response+total_latency);
		
		/*System.out.println("1. "+con1.getLatency());//+" and "+con1.getAvail()+ " and " + con1.getDocu());
		System.out.println("2. "+con2.getLatency());//+" and "+con2.getAvail()+ " and " + con2.getDocu());
		System.out.println("3. "+con3.getLatency());//+" and "+con3.getAvail()+ " and " + con3.getDocu());
		System.out.println("4. "+con4.getLatency());//+" and "+con4.getAvail()+ " and " + con4.getDocu());
		System.out.println("5. "+con5.getLatency());//+" and "+con5.getAvail()+ " and " + con5.getDocu());*/
		//System.out.println("Total latency= "+total_latency);
		
		/*}	*/
//		System.out.println("total latency + "+total_latency);
		//selection
	    int[][] selected=new int[pop.length][m];
		Roulett_wheel rw=new Roulett_wheel();
		selected=rw.roulett(pop,fit,g);
	/*	System.out.println("Selected:");
		for(int a=0;a<pop.length;a++)
			{for(int b=0;b<m;b++)
				{pop[a][b]=selected[a][b];
			System.out.print(" "+selected[a][b]);}
			System.out.println();
			}
		*/
			//cross-over when cross-over rate is given
		double co_rate=0.6; //cross over rate is 70%
		double k=co_rate*pop.length;
		int cp=3; // cross-over point
		for (int i=0;i<k;i=i+2)
		{
			 int i1=r.nextInt(pop.length-1); //2 random individuals are taken from selected individuals for cross over
			 int i2=r.nextInt(pop.length-1);
			// System.out.println("i1= "+i1+"i2= "+i2+"cp= "+cp+"k= "+k+""+i);
			
			 //int cp=r.nextInt(4);
			 for(int j=0;j<cp;j++)
				{
				 new_pop[i][j]=pop[i1][j];
				 new_pop[i+1][j]=pop[i2][j];
				}
				
				for(int j=cp;j<5;j++)
				{
					new_pop[i][j]=pop[i2][j];
					new_pop[i+1][j]=pop[i1][j];
				}
						}
		
		//Mutation 
		double mu_rate=0.6; //mutation rate is 2% here
		int l=(int)mu_rate*pop.length;
		for(int i=0;i<l;i++)
		{
			int mut=r.nextInt(pop.length-1);  //selecting individual for mutation
			int mp=r.nextInt(m-1); // for mutation point
			int p=r.nextInt(m-1); //for value on to be placed at mutation point
			while(pop[mut][mp]==p) 
				p=r.nextInt(m-1);	
			pop[mut][mp]=p;
			for(int j=0;j<m;j++)
				new_pop[mut][j]=pop[mut][j];			
		}
		
		//for rest of population
		int rest=(int)((pop.length-1)-(l+k));
		//System.out.println("rest= "+rest);
		for(int o=0;o<rest;o++)
		{
			int p=(pop.length-1)-o;
			for(int j=0;j<m;j++)
			{
			new_pop[p][j]=pop[p][j];
			//System.out.print("remaining pop= "+pop[p][j]);
		}
			System.out.println();
			}

		for(int o=0;o<pop.length;o++)
		{
			for(int j=0;j<m;j++)
			pop[o][j]=new_pop[o][j];
		}
	/*	System.out.println("new_pop");
		for(int i = 0 ; i < n ; i++)
		{
			for(int j = 0 ; j < m ; j++)
				System.out.print(" "+new_pop[i][j]);   //from 500 web services random WS will be allocated
		System.out.println();
		}
		*/g++;
		}while(g<=50);
		System.out.println("End");
  } //function
} //class