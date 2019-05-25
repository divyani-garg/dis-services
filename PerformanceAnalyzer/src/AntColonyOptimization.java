import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

/*
 * default
 * private double c = 1.0;             //number of trails
 * private double alpha = 1;           //pheromone importance
 * private double beta = 5;            //distance priority
 * private double evaporation = 0.5;
 * private double Q = 500;             //pheromone left on trail per ant
 * private double antFactor = 0.8;     //no of ants per node
 * private double randomFactor = 0.01; //introducing randomness
 * private int maxIterations = 1000;
 */

public class AntColonyOptimization 
{
	static double[] fit= new double[50];
    static double total_response;
	static double total_avail;
	static int next=0;
	static double total_through;
	static double total_success;
	static double total_reliability;
	static double total_compliance;
	static double total_bp;
	static double total_latency;
	static double total_docu;
	private double eta;
    public String s="";
    private double c;             //number of trails
    private double alpha;           //pheromone importance
    private double beta;            //distance priority
    private double evaporation;
    private double Q;             //pheromone left on trail per ant
    private double antFactor;     //no of ants per node
    private double randomFactor; //introducing randomness

    private int maxIterations;

    private int numberOfCities;
    private int numberOfAnts;
    private double graph[][];
    private double trails[][];
    private List<Ant> ants = new ArrayList<>();
    private Random random = new Random();
    private double probabilities[];

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    public AntColonyOptimization(double tr, double al, double be, double ev,double q, double af, double rf, int iter, int noOfCities) 
    {
        c=tr; alpha=al; beta=be; evaporation=ev; Q=q; antFactor=af; randomFactor=rf; maxIterations=iter;
                
       try { graph = generateRandomMatrix(5);
        numberOfCities = noOfCities;
        numberOfAnts = (int) (numberOfCities * antFactor);

        trails = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];
        
        for(int i=0;i<numberOfAnts;i++)
            ants.add(new Ant(numberOfCities));
       }catch(Exception e){}}

    /**
     * Generate initial solution
     */
    Random r = new Random();
    public double[][] generateRandomMatrix(int n) 
    {
        double[][] randomMatrix = new double[n][n];
        
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                /*if(i==j)
                    randomMatrix[i][j]=0;
                else*/
                    randomMatrix[i][j]=r.nextInt(499);//Math.abs(random.nextInt(100)+1);
            }
        }
         
        s+=("\t");
        for(int i=0;i<n;i++) 		//'n' is no. of cities
            s+=(i+"\t");
        s+="\n";
        
        for(int i=0;i<n;i++)
        {
            s+=(i+"\t");
            for(int j=0;j<n;j++)
                s+=(randomMatrix[i][j]+"\t");
            s+="\n";
        }
        
        int sum=0;
        
        for(int i=0;i<n-1;i++)
            sum+=randomMatrix[i][i+1];
        sum+=randomMatrix[n-1][0];
        s+=("\nNaive solution 0-1-2-...-n-0 = "+sum+"\n");
        return randomMatrix;
    }

    /**
     * Perform ant optimization
     */
    public void startAntOptimization() 
    {
      for(int i=1;i<=10;i++)
        {
            s+=("\nGeneration #" +i);
            solve();
            s+="\n";
        }
      GraphingData gd=new GraphingData();
      gd.getData(fit);
    }

    /**
     * Use this method to run the main logic
     */
    public int[] solve() 
    {
        setupAnts();
        clearTrails();
        for(int i=0;i<maxIterations;i++)
        {
            moveAnts();
            updateTrails();
            updateBest();
        }
        s+=("\nBest tour length: " + (bestTourLength - numberOfCities));
        s+=("\nBest tour order: " + Arrays.toString(bestTourOrder));

        return bestTourOrder.clone();
       }

    /**
     * Prepare ants for the simulation
     */
    private void setupAnts() 
    {
        try{for(int i=0;i<numberOfAnts;i++)
        {
            for(Ant ant:ants)
            {
                ant.clear();
                ant.visitCity(-1, random.nextInt(numberOfCities));
            }
        }
        currentIndex = 0;
        }catch(Exception e){}}

    /**
     * At each iteration, move ants
     */
    private void moveAnts() 
    {
        for(int i=currentIndex;i<numberOfCities-1;i++)
        {
            for(Ant ant:ants)
            {
                ant.visitCity(currentIndex,selectNextCity(ant));
            }
            currentIndex++;
        }
    }

    /**
     * Select next city for each ant
     */
    private int selectNextCity(Ant ant) 
    {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) 
        {
            int cityIndex=-999;
            for(int i=0;i<numberOfCities;i++)
            {
                if(i==t && !ant.visited(i))
                {
                    cityIndex=i;
                    break;
                }
            }
            if(cityIndex!=-999)
                return cityIndex;
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i<numberOfCities; i++) 
        {
            total += probabilities[i];
            if (total >= r) 
                return i;
        }
        throw new RuntimeException("There are no other cities");
    }

    /**
     * Calculate the next city picks probabilites
     */
    public void calculateProbabilities(Ant ant) 
    {
    
    
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        double etaa=calculateEta();
        for (int l = 0; l < numberOfCities; l++) 
        {
            if (!ant.visited(l)) 
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(etaa, beta);
        }
        for (int j = 0; j < numberOfCities; j++) 
        {
            if (ant.visited(j)) 
                probabilities[j] = 0.0;
            else 
            {
            	//double etaa=calculateEta();
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(etaa, beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    private double calculateEta(){
    	int x,y,p;
    	//String[][] matrix = new String[n][20];
    	List<List> concreteList= new ArrayList<List>() ; //a list that contains 5 lists
    	double [] responseArray= null;//=new double[n];
    	double [] availArray=null;//=new double[n];
    	double [] throughArray=null;//=new double[n];
    	double [] successArray=null;//=new double[n];
    	double [] reliabilityArray=null;//=new double[n];
    	double [] complianceArray=null;//=new double[n];
    	double [] bpArray=null;//=new double[n];
    	double [] latencyArray=null;//=new double[n];
    	double [] docuArray=null;//=new double[n];
    	double[][] normalizedMatrix = new double[2507][20];
    	int rowCount=0;
    	
    	try
        {
    		//PrintStream o = new PrintStream(new File("A.txt"));
    		//PrintStream console = System.out;
    		String path=new String();
    		p=1;
    	//List<List> concreteList;
		do {	
    		switch(p) {
    		case 1: path="D:\\dataset\\1.csv";
    		rowCount=rowCount(path);
    		responseArray=new double[rowCount];
    		availArray=new double[rowCount];
    		throughArray=new double[rowCount];
    		successArray=new double[rowCount];
    		reliabilityArray=new double[rowCount];
    		complianceArray=new double[rowCount];
    		bpArray=new double[rowCount];
    		latencyArray=new double[rowCount];
    		docuArray=new double[rowCount];
    		break;
    		
    		case 2: path="D:\\dataset\\2.csv";
    		rowCount=rowCount(path);
    		responseArray=new double[rowCount];
    		availArray=new double[rowCount];
    		throughArray=new double[rowCount];
    		successArray=new double[rowCount];
    		reliabilityArray=new double[rowCount];
    		complianceArray=new double[rowCount];
    		bpArray=new double[rowCount];
    		latencyArray=new double[rowCount];
    		docuArray=new double[rowCount];
    		break;
    		
    		case 3:path="D:\\dataset\\3.csv";
    		rowCount=rowCount(path);
    		responseArray=new double[rowCount];
    		availArray=new double[rowCount];
    		throughArray=new double[rowCount];
    		successArray=new double[rowCount];
    		reliabilityArray=new double[rowCount];
    		complianceArray=new double[rowCount];
    		bpArray=new double[rowCount];
    		latencyArray=new double[rowCount];
    		docuArray=new double[rowCount];
    		break;
    		
    		case 4: path="D:\\dataset\\4.csv";
    		rowCount=rowCount(path);
    		responseArray=new double[rowCount];
    		availArray=new double[rowCount];
    		throughArray=new double[rowCount];
    		successArray=new double[rowCount];
    		reliabilityArray=new double[rowCount];
    		complianceArray=new double[rowCount];
    		bpArray=new double[rowCount];
    		latencyArray=new double[rowCount];
    		docuArray=new double[rowCount];
    		break;
    		
    		case 5: path="D:\\dataset\\5.csv";
    		rowCount=rowCount(path);
    		responseArray=new double[rowCount];
    		availArray=new double[rowCount];
    		throughArray=new double[rowCount];
    		successArray=new double[rowCount];
    		reliabilityArray=new double[rowCount];
    		complianceArray=new double[rowCount];
    		bpArray=new double[rowCount];
    		latencyArray=new double[rowCount];
    		docuArray=new double[rowCount];
    		break;
    		
    		default:
    			System.out.println();
    	}
    		//path="D:\\1.csv";
    	//BufferedReader in = new BufferedReader(new FileReader("D:\\Downloads\\Qws.csv"));	//reading files in specified directory
    		BufferedReader in = new BufferedReader(new FileReader(path));	//reading files in specified directory
    		String line;
    		x=0;
    		while ((line = in.readLine()) != null)	//file reading
    		{
    			String[] values = line.split(",");
    			int n=values.length;
    			
    			y=0;
            	for (String str : values)
            	{
            		//double str_double = Double.parseDouble(str);
            		if(y<9) {
           		
          			switch(y) {
          			case 0: responseArray[x]=Double.parseDouble(str);
          			//System.out.println("y is 0");
          				break;
          			case 1: availArray[x]=Double.parseDouble(str);
          			//System.out.println("y is 1");
          			break;
          			case 2: throughArray[x]=Double.parseDouble(str);
          			break;
          			case 3: successArray[x]=Double.parseDouble(str);
          			break;
          			case 4: reliabilityArray[x]=Double.parseDouble(str);
          			break;
          			case 5: complianceArray[x]=Double.parseDouble(str);
          			break;
          			case 6: bpArray[x]=Double.parseDouble(str);
          			break;
          			case 7: latencyArray[x]=Double.parseDouble(str);
          			break;
          			case 8: docuArray[x]=Double.parseDouble(str);
          			break;
          			default : System.out.println("Default");
          			}
          				
          			y=y+1;
            			//System.out.println("**************y= " +y);
            		}else {
            			break;
            		}
            	}
            	//System.out.println("normalizationArray= "+normalizationArray[x]);
            	//System.out.println();
            	x=x+1;
    		}
        	in.close();
        	
        	Normalization normalization= new Normalization();
        	
        	normalization.normalize(normalizedMatrix,responseArray,0);
        	normalization.normalize(normalizedMatrix,availArray,1);
        	normalization.normalize(normalizedMatrix,throughArray,2);
        	normalization.normalize(normalizedMatrix,successArray,3);
        	normalization.normalize(normalizedMatrix,reliabilityArray,4);
        	normalization.normalize(normalizedMatrix,complianceArray,5);
        	normalization.normalize(normalizedMatrix,bpArray,6);
        	normalization.normalize(normalizedMatrix,latencyArray,7);
        	normalization.normalize(normalizedMatrix,docuArray,8);
        	
        	List<Concrete> concreteObjectsList= new ArrayList<Concrete>() ;
        	for(int i=0;i<responseArray.length;i++) {
    				Concrete concrete= new Concrete();
    				concrete.setResponse(normalizedMatrix[i][0]);
    				concrete.setAvail(normalizedMatrix[i][1]);
    				concrete.setThrough(normalizedMatrix[i][2]);
    				concrete.setSuccess(normalizedMatrix[i][3]);
    				concrete.setReliability(normalizedMatrix[i][4]);
    				concrete.setCompliance(normalizedMatrix[i][5]);
    				concrete.setBp(normalizedMatrix[i][6]);
    				concrete.setLatency(normalizedMatrix[i][7]);
    				concrete.setDocu(normalizedMatrix[i][8]);
    				concreteObjectsList.add(concrete);
    			//System.out.println(" i ="+i);
    		}
        	
        	
        	concreteList.add(concreteObjectsList);
        	
        	p++;
        }while(p<=5);
    		
 
    	for(int i=0;i<maxIterations;i++)
		{
		Concrete con1 = new Concrete();
		Concrete con2 = new Concrete();
		Concrete con3 = new Concrete();
		Concrete con4 = new Concrete();
		Concrete con5 = new Concrete();
		//List<List> concreteList;
		con1 = (Concrete) concreteList.get(0).get((int)graph[i][0]);
		con2 = (Concrete) concreteList.get(1).get((int)graph[i][1]);
		con3 = (Concrete) concreteList.get(2).get((int)graph[i][2]);
		con4 = (Concrete) concreteList.get(3).get((int)graph[i][3]);
		con5 = (Concrete) concreteList.get(4).get((int)graph[i][4]);
		
		total_response =con1.getResponse()+con2.getResponse()+con3.getResponse()+con4.getResponse()+con5.getResponse();
		total_avail=con1.getAvail()*con2.getAvail()*con3.getAvail()*con4.getAvail()*con5.getAvail();
		total_through=con1.getThrough()+con2.getThrough()+con3.getThrough()+con4.getThrough()+con5.getThrough();
		total_success=con1.getSuccess()+con2.getSuccess()+con3.getSuccess()+con4.getSuccess()+con5.getSuccess();
		total_reliability=con1.getReliability()*con2.getReliability()*con3.getReliability()*con4.getReliability()*con5.getReliability();
		total_compliance=con1.getCompliance()+con2.getCompliance()+con3.getCompliance()+con4.getCompliance()+con5.getCompliance();
		total_bp=con1.getBp()+con2.getBp()+con3.getBp()+con4.getBp()+con5.getBp();
		total_latency=con1.getLatency()+con2.getLatency()+con3.getLatency()+con4.getLatency()+con5.getLatency();
		total_docu=con1.getDocu()+con2.getDocu()+con3.getDocu()+con4.getDocu()+con5.getDocu();
		
		//since latency and response time are negative values
		//total_latency=1-total_latency;
		//total_response=1-total_response;
		double total_params=(Math.pow(total_avail,2)+Math.pow(total_through,2)+Math.pow(total_success,2)+Math.pow(total_reliability,2)+Math.pow(total_compliance,2)+Math.pow(total_bp,2)+total_docu)+(1.0/Math.pow(total_response,2))+(1.0/Math.pow(total_latency,2));
		eta = 1/Math.sqrt(total_params);
		//fitness calculation
		//fit[i] = (total_avail+total_through+total_success+total_reliability+total_compliance+total_bp+total_docu)/(total_response+total_latency);
		
		/*System.out.println("1. "+con1.getLatency());//+" and "+con1.getAvail()+ " and " + con1.getDocu());
		System.out.println("2. "+con2.getLatency());//+" and "+con2.getAvail()+ " and " + con2.getDocu());
		System.out.println("3. "+con3.getLatency());//+" and "+con3.getAvail()+ " and " + con3.getDocu());
		System.out.println("4. "+con4.getLatency());//+" and "+con4.getAvail()+ " and " + con4.getDocu());
		System.out.println("5. "+con5.getLatency());//+" and "+con5.getAvail()+ " and " + con5.getDocu());*/
		//System.out.println("Total latency= "+total_latency);
		
		}		
    }catch( IOException ioException ) {
		
	}
    	return eta;
    }
    private static int rowCount(String path) throws IOException {
    	int count=0;
    	BufferedReader in = new BufferedReader(new FileReader(path));	//reading files in specified directory
    	while (in.readLine() != null){
    		count++;
            }
    	in.close();
    	return count;
    	}

    
    /**
     * Update trails that ants used
     */
    private void updateTrails() 
    {
        float rho=(float) 0.3;
        double delt;
        for (int i = 0; i < numberOfCities; i++) 
        {
            for (int j = 0; j < numberOfCities; j++) 
                trails[i][j] *= evaporation;
                
        }
        for (Ant a : ants) 
        {
            double contribution = Q / a.trailLength(graph);
            for (int i = 0; i < numberOfCities - 1; i++)
            { trails[a.trail[i]][a.trail[i + 1]] += contribution;
             //trails[a.trail[i+1]]=[a.trail[0]]*(1-rho);
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }
		
    /**
     * Update the best solution
     */
    private void updateBest() 
    {
        if (bestTourOrder == null) 
        {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(graph);
        }
        
        for (Ant a : ants) 
        {
            if (a.trailLength(graph) < bestTourLength) 
            {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
        fit[next]=bestTourLength;
        next++;
    }
    

    /**
     * Clear trails after simulation
     */
    private void clearTrails() 
    {
        for(int i=0;i<numberOfCities;i++)
        {
            for(int j=0;j<numberOfCities;j++)
                trails[i][j]=c;
        }
    }

}