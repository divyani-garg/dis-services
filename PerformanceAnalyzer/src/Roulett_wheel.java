import java.util.*;

public class Roulett_wheel{
		//static int pop_size, chro_size, re, mu, cr;
		//static Random r = new Random();
	       static double[] fit= new double[50];
		@SuppressWarnings("resource")
		public static int[][] roulett(int[][] population,double[] fitness,int g) // g for number of generation 
		{
		/*	System.out.println("Hello");
			for(int i=0;i<population.length;i++)
			{
				//System.out.println("Hello");
				System.out.println(" "+fitness[i]);
			}
                    
	*/	int pop_size=population.length;
			//calculate max_fitness
			double max_fitness = fitness[0]; 
			
                        for(int i =0 ; i<population.length;i++)
			{
				if(max_fitness < fitness[i])
					max_fitness = fitness[i];
			}
			System.out.println("Max fitness : "+max_fitness);
                        
                        double total_fitness = 0;
                       
                       
                       fit[g-1]=max_fitness;
                       
                        System.out.println("Generation!!! = "+g);
			//for(int i =0;i<50;i++)
              //          System.out.println("maxFit= "+fit[i]);
                        
                       for(int i =0 ; i<population.length;i++)
			{
				total_fitness = total_fitness + fitness[i];
			}
			
                        
			double avg_fitness = total_fitness / pop_size;
			
			//probability
			double[] probability = new double[pop_size];
			for(int i =0 ; i<pop_size;i++)
			{
				probability[i] = fitness[i] / total_fitness;
			}
			
                        
			//expected count
			//System.out.println("Expected_count : ");
			double[] expected_count = new double[pop_size];
			for(int i =0 ; i<pop_size;i++)
			{
				expected_count[i] = fitness[i] / avg_fitness;
				//System.out.println(" "+expected_count[i]);
			}
			
			//actual count
			int[] actual_count = new int[pop_size];
			for(int i =0 ; i<pop_size;i++)
			{
				if( (expected_count[i] >= (Math.floor( (double) expected_count[i] )) + 0.5))
				{
					actual_count[i] = (int) Math.ceil( (double) expected_count[i] );
				}
				else
				{
					actual_count[i] = (int) Math.floor( (double) expected_count[i] );
				}
			}
			 //Arrays.sort(expected_count); 
			 /*for(int j=0;j<expected_count.length/2;j++)
			 {
			   double temp=expected_count[j];
				expected_count[j]=expected_count[expected_count.length-1-j];
				expected_count[expected_count.length-1-j]=temp;
			 }*/
			
			 int n = expected_count.length;
		        for (int i = 0; i < n-1; i++)
		            for (int j = 0; j < n-i-1; j++)
		                if (expected_count[j] < expected_count[j+1])
		                {
		                    // arranging expected count in descending order
		                    double temp = expected_count[j];
		                    expected_count[j] = expected_count[j+1];
		                    expected_count[j+1] = temp;
		                    
		                    //swapping population array in accordance with expected count
		                    for(int m=0;m<5;m++)
		                    {
		                    int temp2 = population[j][m];
		                    population[j][m] = population[j+1][m];
		                    population[j+1][m] = temp2;
		                    }
		                }
			 
			 int total_actual_count = 0 ;
			for(int j =0 ; j<pop_size;j++)
			{
				total_actual_count = total_actual_count + actual_count[j];
			}
			if((pop_size - total_actual_count == 1))
			{
				for(int i = 0 ; i < pop_size ; i++)
				{
					if(actual_count[i] > 0)
					if( (expected_count[i] <= (Math.floor( (double) expected_count[i] )) + 0.5) && (expected_count[i] >= (Math.floor( (double) expected_count[i] ))))
					{
						actual_count[i] = actual_count[i] + 1;
					}
					//System.out.print(" "+actual_count[i]);
				}
	        }
			//System.out.println("Population size : "+pop_size);
			
			 int v = actual_count.length;
		        for (int i = 0; i < v-1; i++)
		            for (int j = 0; j < v-i-1; j++)
		                if (actual_count[j] < actual_count[j+1])
		                {
		                    // arranging actual count in descending order
		                    int temp1 = actual_count[j];
		                    actual_count[j] = actual_count[j+1];
		                    actual_count[j+1] = temp1;
		                }
			 
			
	/*		System.out.println("Actual_count : ");
			for(int j=0;j<pop_size;j++)
			{
				System.out.println(" "+actual_count[j]);
			}
			
			System.out.println("Expected_count : ");
			for(int j=0;j<expected_count.length;j++)	
				System.out.println(" "+expected_count[j]);
		
			System.out.println("population now ");
			for(int i = 0 ; i < population.length ; i++)
			{
				for(int j = 0 ; j <5 ; j++)
				{
					System.out.print(" "+population[i][j]);   //from 500 web services random WS will be allocated
				}
			System.out.println();
			}
		*/
			int count=0;
			int[] selected=new int[actual_count.length];
			for(int k=0;k<actual_count.length;k++)
			{
				count=count+actual_count[k];
				if(count<=actual_count.length)
				selected[k]=actual_count[k];
				else
					selected[k]=0;
			}
			
	//		System.out.println("selected by me : ");
	//		for(int j=0;j<selected.length;j++)
		//		System.out.println(" "+selected[j]);
			int[][] pop_new= new int[population.length][5];int o=0;
			for (int i=0;i<selected.length;i++)
			{
				int l=selected[i];
				for(int j=0;j<l;j++)
				{
					for(int m=0;m<5;m++)
						pop_new[o][m]=population[i][m];
					o++;
				}
			}
			if(g==50)
                        {
				for(int i =0;i<50;i++)
		              System.out.println("maxFit= "+fit[i]);
                        GraphingData gd=new GraphingData();
                        gd.getData(fit);
                        }
	/*		System.out.println("population now plus");
			for(int i = 0 ; i < pop_new.length ; i++)
			{
				for(int j = 0 ; j <5 ; j++)
				{
					System.out.print(" "+pop_new[i][j]);   //from 500 web services random WS will be allocated
				}
			System.out.println();
			}
		*/	
			return pop_new;
}//function
}//class
		/*	Scanner scan = new Scanner(System.in);

			System.out.println("Do you want to generate the random Population (Y/N) : ");
			String c = scan.next();
			
			if(c.equalsIgnoreCase("Y"))
			{
				// take population size from user
				System.out.print("Enter the population size : ");
				pop_size = scan.nextInt();
		
				// take chromosome size from user
				System.out.print("Enter the Chromosome size : ");
				chro_size = scan.nextInt();
		
				// create population 2D-array
				int population[][] = new int[pop_size][chro_size];
		
				// Randomly initialize Population
				for (int i = 0; i < pop_size; i++) {
					for (int j = 0; j < chro_size; j++) {
						population[i][j] = r.nextInt(2);
					}
				}
		
				//calculate fitness of initial population
				int[] fitness = new int[pop_size];
				fitness = cal_fitness(population);
				calculate_actual_count(population,fitness);
			}
			else
			{
				System.out.print("Enter the population size : ");
				pop_size = scan.nextInt();
				int population[][] = {{0},{0}};
				int[] fitness = new int[pop_size];
				
				for(int i = 0 ; i < pop_size ; i++)
				{
					System.out.println("Enter the fitness value for chromosome " + i);
					fitness[i] = scan.nextInt();
				}
			
				calculate_actual_count(population ,fitness);
			}
		}
		
		// calculate Fitness
		public static int[] cal_fitness(int[][] population)
		{
			int fitness[] = new int[pop_size];
			int count = 0;
			for (int i = 0; i < pop_size; i++) 
			{
				for (int j = 0; j < chro_size; j++) 
				{
					if (population[i][j] == 1) 
					{
						count++;
					}
				}
				fitness[i] = count;
				count = 0;
			}
			return fitness;
		}
		
		//calculate max_fitness
		public static int cal_max_fitness(int fitness[])
		{
			int max_fitness = fitness[0];
			for(int i =0 ; i<pop_size;i++)
			{
				if(max_fitness < fitness[i])
					max_fitness = fitness[i];
			}
			return max_fitness;
		}
		
		public static void calculate_actual_count(int [][] population , int [] fitness) 
		{
			int max_fitness = cal_max_fitness(fitness);
			
			float total_fitness = 0;
			for(int i =0 ; i<pop_size;i++)
			{
				total_fitness = total_fitness + fitness[i];
			}
			
			float avg_fitness = total_fitness / pop_size;
			
			//probability
			float[] probability = new float[pop_size];
			for(int i =0 ; i<pop_size;i++)
			{
				probability[i] = fitness[i] / total_fitness;
			}
			
			//expected count
			float[] expected_count = new float[pop_size];
			for(int i =0 ; i<pop_size;i++)
			{
				expected_count[i] = fitness[i] / avg_fitness;
			}
			
			//actual count
			int[] actual_count = new int[pop_size];
			for(int i =0 ; i<pop_size;i++)
			{
				if( (expected_count[i] >= (Math.floor( (double) expected_count[i] )) + 0.5))
				{
					actual_count[i] = (int) Math.ceil( (double) expected_count[i] );
				}
				else
				{
					actual_count[i] = (int) Math.floor( (double) expected_count[i] );
				}
			}
			
			int total_actual_count = 0 ;
			for(int j =0 ; j<pop_size;j++)
			{
				total_actual_count = total_actual_count + actual_count[j];
			}
			if((pop_size - total_actual_count == 1))
			{
				for(int i = 0 ; i < pop_size ; i++)
				{
					if(actual_count[i] > 0)
					if( (expected_count[i] <= (Math.floor( (double) expected_count[i] )) + 0.5) && (expected_count[i] >= (Math.floor( (double) expected_count[i] ))))
					{
						actual_count[i] = actual_count[i] + 1;
					}
				}
			}
			
			
			System.out.println("total_fitness: "+total_fitness);
			System.out.println("avg_fitness: "+avg_fitness);
			System.out.println("max_fitness: "+max_fitness);
			System.out.println();
			System.out.println("           Population                    Fitness           Probability             Expected Count            Actual Count");
			for (int i = 0; i < pop_size; i++) 
			{
				System.out.print(i + 1 + ".  :  ");
				for (int j = 0; j < chro_size; j++) 
				{
					System.out.print(population[i][j]);
				}
				System.out.print("              ");
				System.out.print("         "+fitness[i]);
				System.out.print("              "+probability[i]);
				System.out.print("                  "+expected_count[i]);
				System.out.println("                       "+actual_count[i]);
			}
		}
	}

*/