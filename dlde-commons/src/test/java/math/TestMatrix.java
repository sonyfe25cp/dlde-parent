package math;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.math.BaseMatrix;
import edu.bit.dlde.math.MatrixCompute;

public class TestMatrix  extends TestCase{

	private BaseMatrix matrix;
	
	@Test 
	public void testSimplexMatrix(){
		System.out.println("origin matirx is :\n"+matrix.toString());
		matrix.simplex();
		System.out.println("simplx matirx is :\n"+matrix.toString());
		System.out.println("testSimplexMatrix is over");
	}
	
	@Test 
	public void testRemoveRow(){
		System.out.println("origin matirx is :\n"+matrix.toString());
		BaseMatrix matrix=MatrixCompute.removeRow(this.matrix, 1);
		System.out.println(matrix.toString());
		System.out.println("testRemoveRow is over");
	}
	
	@Test
	public void testPrint(){
		TestMatrix tm=new TestMatrix();
		System.out.println(tm.matrix.toString());
		System.out.println("testPrint is over");
	}
	
	/**
	 * 伴随矩阵
	 * Apr 25, 2012
	 */
	@Test
	public void testStar(){
		TestMatrix tm=new TestMatrix();
		BaseMatrix star=MatrixCompute.star(tm.matrix);
		System.out.println(star.toString());
		System.out.println("testStar is over");
	}
	/*
	 * 矩阵乘法
	 */
	@Test
	public void testMultiple(){
		BaseMatrix matrixA=new BaseMatrix(3,4);
		BaseMatrix matrixB=new BaseMatrix(4,3);
		double[] arrayA={1,0,-1,2,-1,1,3,0,0,5,-1,4};
		List<Double> tolist=new ArrayList<Double>();
		for(double a:arrayA){
			tolist.add(a);
		}
		matrixA.colon(tolist);
		System.out.println(matrixA.toString());
		double[] arrayB={0,3,4,1,2,1,3,1,-1,-1,2,1};
		matrixB.colon(arrayB);
		System.out.println(matrixB.toString());
		BaseMatrix matrixC=MatrixCompute.multiple(matrixA, matrixB);
		System.out.println(matrixC.toString());
		System.out.println("testMultiple is over");
	}
	@Test
	public void testTranspose(){
		BaseMatrix matrixA=new BaseMatrix(3,4);
		double[] arrayA={1,0,-1,2,-1,1,3,0,0,5,-1,4};
		matrixA.colon(arrayA);
		BaseMatrix matrixB=MatrixCompute.transpose(matrixA);
		System.out.println(matrixA.toString());
		System.out.println(matrixB.toString());
		System.out.println("testTranspose is over");
	}
	
	public TestMatrix(){
		init();
	}
	public void  init(){
		matrix=new BaseMatrix(3,4);
		double[] orign={1,2,3,2,0,1,3,2,2,2,1,0};
		matrix.colon(orign);
	}
	
}