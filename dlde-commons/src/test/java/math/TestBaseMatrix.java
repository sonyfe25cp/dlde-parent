package math;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.math.BaseMatrix;
import edu.bit.dlde.math.VectorCompute;

public class TestBaseMatrix extends TestCase{

	private BaseMatrix matrix;
	@Test
	public void testRowGet(){
		double[] row = matrix.getRow(1);
		VectorCompute.viewVector(row);
	}
	@Test
	public void testColGet(){
		double[] col = matrix.getCol(1);
		VectorCompute.viewVector(col);
	}
	@Test
	public void testGetValue(){
		double value = matrix.getValue(2, 3);
//		assertEquals(0,value);
		System.out.println(value);
	}
	public TestBaseMatrix(){
		init();
	}
	public void  init(){
		matrix=new BaseMatrix(3,4);
		double[] orign={
				1,2,3,2,
				0,1,3,2,
				2,2,1,0};
		matrix.colon(orign);
	}
}
