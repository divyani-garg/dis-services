package sgsits.cse.dis.moodle;

public class Test {
	
	public static void main(String[] args) throws ClassNotFoundException {
		String test = "CO34007_2018_2019_Sem_A";
		int ch = test.indexOf("_");
		System.out.println(test.substring(0, ch));
		
	}
}
