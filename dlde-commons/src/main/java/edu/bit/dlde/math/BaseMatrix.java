package edu.bit.dlde.math;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.bit.dlde.utils.DLDELogger;

/**
 * 基本的矩阵操作
 * @author ChenJie
 *
 */
/**
 * @author ChenJie
 *
 */
public class BaseMatrix {
	
	private int rows;//行数
	private int cols;//列数
	
	private DLDELogger logger=new DLDELogger();
	
	public BaseMatrix(int rows, int cols) {
		super();
		if(cols==0||rows==0){
			logger.error("wrong matrix settings !! please confirm the rows and cols count.");
			return;
		}
		this.rows = rows;
		this.cols = cols;
		
		matrix=new double[rows*cols];
	}
	
	private double[] matrix;
	
	/**
	 * 输入第x行,y列的数值 , 从0开始计数
	 * @param x
	 * @param y
	 * Apr 24, 2012
	 */
	public void setValue(int x, int y,double value){
		int pos=x*cols+y;
		if(pos<0||pos>matrix.length){
			logger.error("can't set the value,beacuse wrong positon of matrix. pos:"+pos+",matrix.length:"+matrix.length);
			logger.error("x:"+x+",y:"+y+",value:"+value);
		}
		matrix[pos]=value;
	}
	
	/**
	 * 获得第x行
	 * @param x
	 * @return
	 * Apr 25, 2012
	 */
	public double[] getRow(int x){
		double[] row_value=new double[cols];
		for(int i=0;i<cols;i++){
			row_value[i]=getValue(x,i);
		}
		return row_value;
	}
	/**
	 * 点乘
	 * @param x
	 * Apr 15, 2013
	 */
	public BaseMatrix dot(double x){
		for(int row = 0; row < rows; row ++){
			for(int col = 0; col < cols; col ++){
				this.setValue(row, col, getValue(row, col) * x);
			}
		}
		return this;
	}
	
	
	/**
	 * 获得第x列
	 * @param x
	 * @return
	 * Apr 25, 2012
	 */
	public double[] getCol(int x){
		double[] col_value=new double[rows];
		for(int i=0;i<rows;i++){
			col_value[i]=getValue(i,x);
		}
		return col_value;
	}
	
	/**
	 * 取第x行，y列的值
	 * @param x
	 * @param y
	 * @return
	 * Apr 24, 2012
	 */
	public double getValue(int x ,int y){
		int pos=x*cols+y;
		if(pos<0||pos>matrix.length){
			logger.error("can't get the value,beacuse wrong positon of matrix. x:"+x+" ,y:"+y+" ,pos:"+pos);
		}
//		logger.info("x:"+x+" ,y:"+y+" ,pos:"+pos+",matrix[pos]:"+matrix[pos]);
		return matrix[pos];
	}
	/**
	 * 从数组中直接复制矩阵
	 * @param array
	 * Apr 24, 2012
	 */
	public void colon(List<Double> array){
		if(array.size()!=matrix.length){
			logger.error("can't init the matrix with this array,beacuse the size does not match");
			return;
		}
		for(int i=0;i<array.size();i++){
			matrix[i]=array.get(i);
		}
	}
	/**
	 * 从数组中直接复制矩阵
	 * @param array
	 * Apr 24, 2012
	 */
	public void colon(double[] array){
		if(array.length != matrix.length){
			logger.error("can't init the matrix with this array,beacuse the size does not match");
			return;
		}
		for(int i=0;i<array.length ;i++){
			matrix[i]=array[i];
		}
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(int x=0;x<rows;x++){
			sb.append(printYi(cols)+"\n");
			for(int y=0;y<cols;y++){
				sb.append(getValue(x,y)+" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private String printYi(int count){
		String str="";
		for(int i=0;i<count;i++){
			str+="----";
		}
		return str;
	}
	
	/**
	 * 交换第x行与第y行
	 * @param x
	 * @param y
	 * Apr 25, 2012
	 */
	public void swapRows(int x ,int y){
		for(int i=0;i<cols;i++){
			Double x_i=getValue(x,i);
			Double y_i=getValue(y,i);
			Double temp=x_i;
			setValue(x,i,y_i);
			setValue(y,i,temp);
		}
	}
	/**
	 * 交换第x列，y列
	 * @param x
	 * @param y
	 * Apr 25, 2012
	 */
//	public void swapCols(int x, int y){
//		for(int i=1;i<=rows;i++){
//			MathObject i_x=getObject(i,x);
//			MathObject i_y=getObject(i,y);
//			MathObject temp=i_x;
//			setValue(i,x,i_y);
//			setValue(i,y,temp);
//		}
//	}
	
	/**
	 * 把第x行加到第y行上
	 * @param x
	 * @param y
	 * Apr 25, 2012
	 */
//	public void addRows(int x,int y){
//		for(int i =1;i<=cols;i++){
//			double x_i=getValue(x,i);
//			double y_i=getValue(y,i);
//			double temp=x_i+y_i;
//			setValue(y,i,temp);
//		}
//	}
	
	/**
	 * 第row行 乘 value
	 * @param row
	 * @param value
	 * May 30, 2012
	 */
//	public void multipleRow(int row,double value){
//		for(int i = 1 ; i<=cols;i++){
//			double x= getValue(row,i)*value;
//			setValue(row,i,x);
//		}
//	}
	
	/**
	 * 化成最简形
	 * May 30, 2012
	 */
	public void simplex(){
		/*
		 * 转化为阶梯形 
		 */
		Map<Integer,Boolean> checkMap=new HashMap<Integer,Boolean>();
		for(int i=0;i<rows;i++){
			double value=getValue(i,1);
			if(value==0){
				checkMap.put(i, false);
			}else{
				checkMap.put(i, true);
			}
		}
		boolean map_flag=checkMap.containsValue(false);//是否有首元为0
		if(!map_flag){//无首元为0||首元都是0
			return;
		}
		for(int i=0;i<rows;i++){
			if(checkMap.size()<1){
				break;
			}
			boolean flag=checkMap.get(i);
			checkMap.remove(i);
			if(flag){//首元不为0
				continue;
			}else{//改行首元为0
				if(checkMap.size()>0){
					map_flag=checkMap.containsValue(true);//是否还有首元非0
					int swap_row=0;
					for(Entry<Integer,Boolean> entry:checkMap.entrySet()){
						boolean entry_value=entry.getValue();
						if(entry_value==true){
							swap_row=entry.getKey();
						}
					}
					swapRows(i,swap_row);
					checkMap.remove(swap_row);
				}else{
					break;
				}
			}
		}
		
		/*
		 * 化成最简形 
		 */
	}
	
	/**
	 * 获得行数
	 * @return
	 * Apr 25, 2012
	 */
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 获得列数
	 * @return
	 * Apr 25, 2012
	 */
	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
}
