//package edu.bit.dlde.math.optimization.simple;
//
//import edu.bit.dlde.math.BaseFunction;
//import edu.bit.dlde.utils.DLDELogger;
//
///**
// * 一元函数通用
// * @author ChenJie
// *
// */
//public class SimpleOptimization {
//	protected DLDELogger logger=new DLDELogger();
//	private BaseFunction base;
//	/**
//	 * 待求解方程
//	 * @param x
//	 * @return
//	 * Mar 8, 2012
//	 */
//	public double function(double... args){
//		if(base==null){
//			logger.error("no function input , please set baseFunction");
//			return 0;
//		}
//		return base.function(args);
//	}
//	
//	/**
//	 * 中值法求函数在x处的一阶导数
//	 * @param x
//	 * @return
//	 * Apr 24, 2012
//	 */
//	public double derivativeFunction(double x){//导数函数
//		double step=0.05;
//		double df=(function(x+step)-function(x-step))/(step*2);
//		return df;
//	}
//	
//	protected double left;//左边界
//	protected double right;//右边界
//	
//	protected double min;//最小值
//	protected double minX;//最小值时x的值
//	protected double precision;//精度
//	
//	protected final int LEFT=1;
//	protected final int RIGHT=2;
//	protected final int INIT=0;
//	
//	public void init(){
//		
//		if(left>right){
//			double tmp=left;
//			left=right;
//			right=tmp;
//		}else if(left==right){
//			logger.error("left edge equals right edge !! ");
//		}
//		
//		if(left==0&&right==0){
//			logger.error("left edge && right edge is not setted !!");
//			return;
//		}
//		if(precision==0){
//			logger.error("precision is not setted !!");
//			return;
//		}
//	}
//	
//	public String toString(){
//		return "minX = "+minX
//			  +"min  = "+min;
//	}
//	
//	public double getLeft() {
//		return left;
//	}
//	public void setLeft(double left) {
//		this.left = left;
//	}
//	public double getRight() {
//		return right;
//	}
//	public void setRight(double right) {
//		this.right = right;
//	}
//	public double getMin() {
//		return min;
//	}
//	public double getPrecision() {
//		return precision;
//	}
//	public void setPrecision(double precision) {
//		this.precision = precision;
//	}
//	public double getMinX() {
//		return minX;
//	}
//
//	public void setBase(BaseFunction base) {
//		this.base = base;
//	}
//}
