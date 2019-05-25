import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVToTwoDArray {

	public static void main(String[] args) {
		int n = 2507; //no of rows
		String[][] matrix = new String[n][20];
		double[][] normalizedMatrix = new double[n][20];
		double [] responseArray=new double[n];
		double [] availArray=new double[n];
		double [] throughArray=new double[n];
		double [] successArray=new double[n];
		double [] reliabilityArray=new double[n];
		double [] complianceArray=new double[n];
		double [] bpArray=new double[n];
		double [] latencyArray=new double[n];
		double [] docuArray=new double[n];
		int x=0, y=0;
		String path="D:\\Downloads\\Qws.csv";
		try
        {
		BufferedReader in = new BufferedReader(new FileReader(path));	//reading files in specified directory
			String line;
			while ((line = in.readLine()) != null)	//file reading
			{
				String[] values = line.split(",");
				y=0;
	        	for (String str : values)
	        	{
	        		//double str_double = Double.parseDouble(str);
	        		if(y<9) {
	        			matrix[x][y]=str;
	        			System.out.println(matrix[x][y]+" - ");
	        			if(y==0) {
	        				responseArray[x]=Double.parseDouble(str);
	        				
	        				//normalizationArray[x]=Double.parseDouble(str);
	        				//System.out.println("normalizationArray= "+normalizationArray[x]);
	        			}
	      			
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
	      			
	      			
	      			
	      			default : System.out.println("HII");
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
        	

    		for(int i=0;i<n;i++) {
    			for(int k=0;k<9;k++) {
    				System.out.print(" "+normalizedMatrix[i][k]);
    				
    					}
    			System.out.println(" i ="+i);
    		}
                
        	
       }catch( IOException ioException ) {}
		
	}
}
