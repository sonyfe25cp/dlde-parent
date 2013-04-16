package math;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.math.VectorCompute;
import edu.bit.dlde.utils.DLDELogger;

public class TestVector extends TestCase{
	private  DLDELogger logger=new DLDELogger();
//	double[] v1=new double[]{1.1,2.2,3.3};
//	double[] v2=new double[]{1.5,2.0,3.9};
	
	double[] v1=new double[]{0,1,2,3,4,5,6};
	double[] v2=new double[]{1,2,3,4,5,6,7};
	
	@Test
	public void testSquare(){
		VectorCompute.square(v1);
		VectorCompute.viewVector(v1);
	}
	@Test
	public void testCosValue(){
		double res=VectorCompute.cosValue(v1, v2);
		logger.info("v1,v2 cos value is:"+res);
	}
	@Test
	public void testExcept(){
		System.out.println("----test except---");
		VectorCompute.viewVector(v1);
		double[] res = VectorCompute.except(v1, 1);
		VectorCompute.viewVector(res);
	}
	
}
