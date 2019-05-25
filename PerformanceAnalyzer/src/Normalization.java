//import java.util.*;
import java.text.DecimalFormat;

public class Normalization{
  public static void main(String[] args) {

	 // double[] a1 = new double[5];//{16, 12, 25, 44};

     //   int[] a2 = new int[5];// {587, 23, 1975, 19};

    //    int[] a3 = new int[5];//{42};
    /*Scanner scan=new Scanner(System.in); 
    System.out.println("Enter elements");
	for(int i=0;i<5;i++)
	a1[i]=scan.nextInt();
    System.out.println("Min is : "+min); // 12
    System.out.println("Max is : "+max); // 44
    System.out.println("New array is : ");*/
    
	
    //normalize(a1);
}
    public static double findMax(double[] nums) {
    	double max = nums[0];
        for (int i=1; i<nums.length; i++) {

            if (nums[i] > max) {
                max = nums[i];
        }
        }

        return max;
    }
    public static double findMin(double[] nums) {
    	double min = nums[0];
       for (int i=1; i<nums.length; i++) {
           if (nums[i] < min) {
              min = nums[i];
              }
       }
       return min;
	}

	public static void normalize(double[][] normalizedMatrix,double[] valuesArray,int y)
	{		  
		DecimalFormat df2 = new DecimalFormat(".##");
         int n=valuesArray.length;
		  double max=findMax(valuesArray);
		  double min =findMin(valuesArray);
//		  double[][] normalizedMatrix = new double[n][20];
		  //System.out.println("min "+min+" max "+max);
		double v1=0;
		double[] a2=new double[n];
		double new_min=0,new_max=1;
		for(int i=0;i<n;i++)
		{
		  double b=(max-min),c=(new_max-new_min);
		  double a=(valuesArray[i]-min);
		  
		  v1=(((a/b)*c)+new_min);
		a2[i]=Double.parseDouble(df2.format(v1));	
		//System.out.println(v1);
		//System.out.println("  "+a2[i]);		
		}
		//System.out.print("y= "+y);
		//int j=0;
		for(int i=0;i<n;i++) {
			//for(int j=0;j<n;j++) {
			switch(y) {
          case 0 :normalizedMatrix[i][0]=a2[i];	
      	  break;
          case 1 :normalizedMatrix[i][1]=a2[i];	
          break;
          case 2 :normalizedMatrix[i][2]=a2[i];	
          break;
          case 3 :normalizedMatrix[i][3]=a2[i];	
          break;
          case 4 :normalizedMatrix[i][4]=a2[i];	
          break;
          case 5 :normalizedMatrix[i][5]=a2[i];	
          break;
          case 6 :normalizedMatrix[i][6]=a2[i];	
          break;
          case 7 :normalizedMatrix[i][7]=a2[i];	
          break;
          case 8 :normalizedMatrix[i][8]=a2[i];	
          break;
          }
			}
	
	
		//Genetic genetic= new Genetic();
		//genetic.ga();
	}
    }
