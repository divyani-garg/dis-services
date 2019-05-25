import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class Services {
   //static long startTime = System.nanoTime();
        static long start = System.currentTimeMillis();
public static void main(String[] args){
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

	 double t = 1.0;             //number of trails
	  double a = 1;           //pheromone importance
	  double b = 5;            //distance priority
	  double e = 0.5;
	  double qq = 500;             //pheromone left on trail per ant
	  double aaf = 0.8;     //no of ants per node
	  double rrf = 0.01; //introducing randomness
	  int it = 1000;
	  int noc=10;
	try
    {
		//PrintStream o = new PrintStream(new File("A.txt"));
		//PrintStream console = System.out;
		String path=new String();
		p=1;
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
		
	
	Timestamp ts = new Timestamp(System.currentTimeMillis());
	Date date = new Date();
	date.setTime(ts.getTime());
	String formattedDate = new SimpleDateFormat("yyyyMMddhhmmss").format(date);
	
	PrintStream o1 = new PrintStream(new File("D:/outputs/"+formattedDate+".txt"));
	System.setOut(o1);
	//System.out.println("Normalized matrix:");
	for(int i=0;i<responseArray.length;i++) {
		for(int j=0;j<9;j++) { 
			//System.out.print("  "+normalizedMatrix[i][j]);
		}
	 //System.out.println();
	}
	Genetic.genetic(concreteList);
	//PSO.pso(concreteList);
	AntColonyOptimization antTSP = new AntColonyOptimization(t,a,b,e,qq,aaf,rrf,it,noc);
    antTSP.startAntOptimization();
    


long end = System.currentTimeMillis();
NumberFormat formatter = new DecimalFormat("#0.00000");
System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");

	}catch( IOException ioException ) {
		
	}
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
}