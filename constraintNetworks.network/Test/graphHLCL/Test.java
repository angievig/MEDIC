package graphHLCL;

import static org.junit.Assert.*;

import transform.HLCL2Graph;

public class Test {
	static LoganHLCL testCase;
	public static void init(){
		 testCase= new LoganHLCL();
		testCase.prepareTest();

		
		
	}

	@org.junit.Test
	public void test() {
		//fail("Not yet implemented");
		init();
		HLCL2Graph o= new HLCL2Graph(testCase.getProgram());
		ConstraintGraphHLCL net=  o.transform();
		net.printNetwork(net);
	}
	
	public static void main (String[] a ){
		init();
		HLCL2Graph o= new HLCL2Graph(testCase.getProgram());
		ConstraintGraphHLCL net=  o.transform();
		net.printNetwork(net);
		
	}

}
