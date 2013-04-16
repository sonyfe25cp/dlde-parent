package edu.bit.dlde.math;

import edu.bit.dlde.utils.DLDELogger;

/**
 * @author ChenJie
 *
 */
public class MatrixCompute {

	private static DLDELogger logger=new DLDELogger();
	
	/**
	 * 逆矩阵
	 * Apr 25, 2012
	 */
	public static void reverse(BaseMatrix matrix){
		
	}
	
	/**
	 * 简单加法，目前仅适用于 matrixA 与 matrixB 是相同大小的矩阵
	 * @param matrixA
	 * @param matrixB
	 * @return
	 * Apr 15, 2013
	 */
	public static BaseMatrix plus(BaseMatrix matrixA, BaseMatrix matrixB){
		int rows = matrixA.getRows();
		int cols = matrixA.getCols();

		BaseMatrix matrix = new BaseMatrix(rows, cols);
		for(int row = 0 ; row < rows; row ++){
			for(int col = 0; col < cols; col ++){
				matrix.setValue(row, col, matrixA.getValue(row, col) + matrixB.getValue(row, col));
			}
		}
		return matrix;
	}
	
	/**
	 * 伴随矩阵
	 * Apr 25, 2012
	 */
	public static BaseMatrix star(BaseMatrix matrix){
		int rows=matrix.getRows();
		int cols=matrix.getCols();
		BaseMatrix newMatrix=new BaseMatrix(cols,rows);
		for(int x=1;x<=rows;x++){
			for(int y=1;y<=cols;y++){
				double value=matrix.getValue(x,y);
				newMatrix.setValue(y, x, value);
			}
		}
		return newMatrix;
	}
	
	/**
	 * 矩阵相乘
	 * @param matrixA
	 * @param matrixB
	 * @return
	 * Apr 25, 2012
	 */
	public static BaseMatrix multiple(BaseMatrix matrixA,BaseMatrix matrixB){
		int rowsA=matrixA.getRows();
		int colsA=matrixA.getCols();
		int rowsB=matrixB.getRows();
		int colsB=matrixB.getCols();
		if(colsA!=rowsB){
			logger.error("cant mutiple this two matrix, the first one's cols does not match the second one's rows.");
			return null;
		}
		BaseMatrix resMatrix=new BaseMatrix(rowsA,colsB);
		for(int x=1;x<=rowsA;x++){//第一行
			for(int y=1;y<=colsB;y++){//第一列
				double value=0;
				
				double[] valueA=matrixA.getRow(x);
//				VectorCompute.viewVector(valueA);
				double[] valueB=matrixB.getCol(y);
//				VectorCompute.viewVector(valueB);
				value=VectorCompute.dotMulti(valueA, valueB);
				resMatrix.setValue(x, y, value);
			}
		}
		return resMatrix;
	}
	
	/**
	 * 转置矩阵
	 * @param matrix
	 * @return
	 * Apr 25, 2012
	 */
	public static BaseMatrix transpose(BaseMatrix matrix){
		int rows=matrix.getRows();
		int cols=matrix.getCols();
		BaseMatrix resMatrix=new BaseMatrix(cols,rows);
		for(int i=1;i<=rows;i++){
			for(int j=1;j<=cols;j++){
				double value=matrix.getValue(i, j);
				resMatrix.setValue(j, i, value);
			}
		}
		return resMatrix;
	}
	
	/**
	 * 化简成最间行列式
	 * @param matrix
	 * @return
	 * May 30, 2012
	 */
	public static BaseMatrix simplex(BaseMatrix matrix){
		
		for(int i=1;i<=matrix.getRows();i++){
			double[] row=matrix.getRow(i);
			double x_1=row[0];
		}
		return matrix;
	}
	
	/**
	 * 去掉矩阵里的第几行
	 * @param matrix
	 * @param rowNum
	 * @return
	 * @throws NullPointerException
	 * May 30, 2012
	 */
	public static BaseMatrix removeRow(BaseMatrix matrix,int rowNum)throws NullPointerException{
		int rows=matrix.getRows();
		int cols=matrix.getCols();
		if(rows==0||rowNum>rows||rowNum<1){
			logger.error("no rows can remove");
			throw new NullPointerException();
		}
		BaseMatrix newMatrix=new BaseMatrix(rows-1,cols);
		boolean flag=false;
		for(int j = 1 ; j<=rows-1;j++){
			for(int i = 1 ; i<=cols;i++){
				int row=j;
				if(rowNum==j){
					flag=true;
				}
				if(flag){
					row=j+1;
				}
				double value=matrix.getValue(row, i);
				newMatrix.setValue(j, i, value);
			}
		}
		return newMatrix;
	}
}
