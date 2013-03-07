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
