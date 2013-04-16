package edu.bit.dlde.math;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO 记得补上单元测试，这个太影响算法了
 * @author ChenJie
 *
 */
public class BaseMatrixTest {
	private BaseMatrix matrix;
	@Before
	public void  init(){
		matrix=new BaseMatrix(3,4);
		double[] orign={
				1,2,3,2,
				0,1,3,2,
				2,2,1,0};
		matrix.colon(orign);
	}
	@Test
	public void testSetValue() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testClone(){
//		BaseMatrix matrixNew = matrix;
		BaseMatrix matrixNew = new BaseMatrix(matrix.getRows(), matrix.getCols());
		matrixNew = matrix.clone();
		matrixNew.setValue(1, 1, 3);
		System.out.println("new matrix should be changed");
		System.out.println(matrixNew.toString());
		System.out.println("new matrix should not be changed");
		System.out.println(matrix.toString());
	}

	@Test
	public void testGetRow() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDot() {
		
		System.out.println(matrix.toString());
		
		matrix.dot(0.1);
		
		System.out.println(matrix.toString());
		
	}

	@Test
	public void testGetCol() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testColonListOfDouble() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testColonDoubleArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToMatlab() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSwapRows() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSimplex() {
		fail("Not yet implemented"); // TODO
	}

}
