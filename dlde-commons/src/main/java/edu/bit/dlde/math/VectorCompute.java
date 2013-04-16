package edu.bit.dlde.math;

import edu.bit.dlde.utils.DLDELogger;

/**
 * 简单向量计算
 * @author ChenJie
 *
 */
public class VectorCompute {
	private static DLDELogger logger=new DLDELogger();
	
	/**
	 * 简单的plus，只支持两向量同长度
	 * @param vector1
	 * @param vector2
	 * @return
	 * Apr 15, 2013
	 */
	public static double[] plus(double[] vector1, double[] vector2){
		double[] res = new double[vector1.length];
		for(int i = 0 ; i < vector1.length; i ++){
			res[i] = vector1[i] + vector2[i];
		}
		return res;
	}
	
	/**
	 * 去掉vector中第k个位置的数字，从0开始数
	 * @param vector
	 * @param k
	 * @return
	 * Apr 15, 2013
	 */
	public static double[] except(double[] vector, int k ){
		double[] res = new double[vector.length-1];
		for(int i = 0 ; i < res.length; i ++){
			if(i < k ){
				res[i] = vector[i];
			}else if(i > k){
				res[i] = vector[i+1];
			}else{
				res[i] = vector[i+1];
			}
		}
		return res;
	}
	
	/**
	 * 返回向量中最大的数
	 * @param vector
	 * @return
	 * Apr 15, 2013
	 */
	public static double max(double[] vector){
		double max = Double.MIN_VALUE;
		for(int i = 0 ; i < vector.length; i ++){
			if(vector[i] > max){
				max = vector[i];
			}
		}
		return max;
	}
	/**
	 * 返回向量中最小的数字
	 * @param vector
	 * @return
	 * Apr 15, 2013
	 */
	public static double min(double[] vector){
		double min = Double.MAX_VALUE;
		for(int i = 0 ; i < vector.length; i ++){
			if(vector[i] < min){
				min = vector[i];
			}
		}
		return min;
	}
	
	/**
	 * 平方
	 * @param vector
	 * Feb 23, 2012
	 */
	public static void square(double[] vector){
		for(int i=0;i<vector.length;i++){
			vector[i]=vector[i]*vector[i];
		}
	}
	/**
	 * 平方值
	 * @param vector
	 * @return
	 * Feb 23, 2012
	 */
	public static double squareValue(double[] vector){
		double res=0.0;
		for(int i=0;i<vector.length;i++){
			res+=vector[i]*vector[i];
		}
		return res;
	}
	
	/**
	 * 向量点乘
	 * @param v1
	 * @param v2
	 * @return
	 * Feb 23, 2012
	 */
	public static double dotMulti(double[] v1,double[] v2){
		double res=0.0;
		for(int i=0;i<v1.length;i++){
			res+=v1[i]*v2[i];
		}
		return res;
	}
	/**
	 * 向量模
	 * @param vector
	 * @return
	 * Feb 23, 2012
	 */
	public static double absValue(double[] vector){
		double res=squareValue(vector);
		return Math.sqrt(res);
	}
	
	/**
	 * 余弦夹角值
	 * @param v1
	 * @param v2
	 * @return
	 * Feb 23, 2012
	 */
	public static double cosValue(double[] v1,double[] v2){
		double res=0.0;
		res=(dotMulti(v1,v2))/(absValue(v1)*absValue(v2));
		return res;
	}
	
	/**
	 * 正弦夹角值
	 * @param v1
	 * @param v2
	 * @return
	 * Feb 23, 2012
	 */
	public static double sinValue(double[] v1,double[] v2){
		double cosValue=cosValue(v1,v2);
		double sin=Math.sqrt(1-(cosValue*cosValue));
		return sin;
	}
	
	/**
	 * 遍历
	 * @param vector
	 * Feb 23, 2012
	 */
	public static void viewVector(double[] vector){
		StringBuilder sb=new StringBuilder();
		sb.append("vector : {");
		for(int i = 0;i<vector.length;i++){
			if(i!=vector.length-1){
				sb.append(vector[i]+",");
			}else{
				sb.append(vector[i]+"");
			}
		}
		sb.append("}");
		logger.info(sb.toString());
	}
}
