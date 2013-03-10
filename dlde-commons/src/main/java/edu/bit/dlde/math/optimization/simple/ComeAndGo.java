//package edu.bit.dlde.math.optimization.simple;
//
///**
// * 进退法找下单峰区间
// * @author ChenJie
// *
// */
//public class ComeAndGo extends SimpleOptimization{
//
//	private double step=0.01;//初始步长
//	
//	private double leftEnd;
//	private double rightEnd;
//	
//	public void compute(){
//		double x0=0;
////		calculate(step,x0);
//		calculate(step,x0,0,0,0,true);
//	}
//	public String toString(){
//		return "leftEnd: "+leftEnd+" , rightEnd: "+rightEnd;
//	}
//	/**
//	 * 简约算法
//	 * Mar 15, 2012
//	 */
//	private void calculate(double step,double x0){
//		
//		double f0=function(x0);
//
//		double x1=x0+step;
//		double f1=function(x1);
//		
//		step=2*step;
//		System.out.println("step:"+step);
//		if(f1<f0){
//			double x2=x1+step;
//			double f2=function(x2);
//			
//			if(f1<=f2){
//				leftEnd=x0;
//				rightEnd=x2;
//				return;
//			}else{
//				calculate(step,x1);
//			}
//		}else{
//			double x2=x0-step;
//			double f2=function(x2);
//			if(f0<=f2){
//				leftEnd=x2;
//				rightEnd=x0;
//				return;
//			}else{
//				calculate(step,x2);
//			}
//		}
//	}
//	
//	/**
//	 * 书本算法
//	 * @param step
//	 * @param x0
//	 * @param x1
//	 * @param f0
//	 * @param f1
//	 * @param add
//	 * Apr 24, 2012
//	 */
//	private void calculate(double step,double x0,double x1,double f0,double f1,boolean add){
//		if(f0==0){
//			f0=function(x0);
//		}
//		if(x1==0){
//			x1=x0+step;
//			f1=function(x1);
//		}
//		step=2*step;
//		System.out.println("step:"+step);
//		if(add){
//			double x2=x1+step;
//			double f2=function(x2);
//			
//			if(f1<=f2){
//				leftEnd=x0;
//				rightEnd=x2;
//				return;
//			}else{
//				x0=x1;
//				x1=x2;
//				f0=f1;
//				f1=f2;
//				calculate(step,x0,x1,f0,f1,true);
//			}
//		}else{
//			double x2=x0-step;
//			double f2=function(x2);
//			if(f0<=f2){
//				leftEnd=x2;
//				rightEnd=x0;
//				return;
//			}else{
//				x1=x0;
//				x0=x2;
//				f1=f0;
//				f0=f2;
//				calculate(step,x0,x1,f0,f1,false);
//			}
//		}
//	}
//
//	public double getStep() {
//		return step;
//	}
//	public void setStep(double step) {
//		this.step = step;
//	}
//	public double getLeftEnd() {
//		return leftEnd;
//	}
//	public double getRightEnd() {
//		return rightEnd;
//	}
//}
