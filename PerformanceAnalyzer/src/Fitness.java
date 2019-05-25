import java.util.*;
public class Fitness{
static double total_response;
	static double total_avail;
	static double total_through;
	static double total_success;
	static double total_reliability;
	static double total_compliance;
	static double total_bp;
	static double total_latency;
	static double total_docu;
	public static double[] fitness(List<List> concreteList,int plength,int[][] pop) {
		
		double []fit=new double[plength];
		for(int i=0;i<plength;i++)
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
		
		//fitness calculation
		fit[i] = (total_avail+total_through+total_success+total_reliability+total_compliance+total_bp+total_docu)/(total_response+total_latency);
		
		/*System.out.println("1. "+con1.getLatency());//+" and "+con1.getAvail()+ " and " + con1.getDocu());
		System.out.println("2. "+con2.getLatency());//+" and "+con2.getAvail()+ " and " + con2.getDocu());
		System.out.println("3. "+con3.getLatency());//+" and "+con3.getAvail()+ " and " + con3.getDocu());
		System.out.println("4. "+con4.getLatency());//+" and "+con4.getAvail()+ " and " + con4.getDocu());
		System.out.println("5. "+con5.getLatency());//+" and "+con5.getAvail()+ " and " + con5.getDocu());*/
		//System.out.println("Total latency= "+total_latency);
		
		}	
//		
		return fit;
	}
	
}