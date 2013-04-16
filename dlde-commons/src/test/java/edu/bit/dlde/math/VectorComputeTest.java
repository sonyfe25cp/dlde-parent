package edu.bit.dlde.math;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO 记得补上单元测试，这个太影响算法了
 * 
 * @author ChenJie
 *
 */
public class VectorComputeTest {
//	double[] v1=new double[]{0,1,2,3,4,5,6};
	double[] v1;
	double[] v2=new double[]{1,2,3,4,5,6,7};
	
	@Before
	public void init(){
		v1=new double[]{0,1,2,3,4,5,6};
	}

	@Test
	public void testPlus() {
		fail("Not yet implemented");
	}

	@Test
	public void testExcept() {
		VectorCompute.viewVector(v1);
		double[] e1 = VectorCompute.except(v1, 2);
		VectorCompute.viewVector(e1);
		
		VectorCompute.viewVector(v1);
		double[] e2 = VectorCompute.except(v1, new int[]{1,2});
		VectorCompute.viewVector(e2);
		
	}

	@Test
	public void testMax() {
		fail("Not yet implemented");
	}

	@Test
	public void testMin() {
		fail("Not yet implemented");
	}

	@Test
	public void testSquare() {
		fail("Not yet implemented");
	}

	@Test
	public void testSquareValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testDotMulti() {
		fail("Not yet implemented");
	}

	@Test
	public void testAbsValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testCosValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testSinValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testViewVector() {
		fail("Not yet implemented");
	}

}
