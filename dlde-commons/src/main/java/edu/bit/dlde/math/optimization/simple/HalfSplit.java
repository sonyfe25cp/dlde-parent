//package edu.bit.dlde.math.optimization.simple;
//
///**
// * 平分法 -- 要求函数必须一阶导数连续
// * @author ChenJie
// *
// */
//public class HalfSplit extends SimpleOptimization{
//	
//	
//	public void compute(){
//		init();
//		
//		if(derivativeFunction(left)>0||derivativeFunction(right)<0||precision<0){
//			logger.error("cant use this method to get min value");
//			return;
//		}
//		min=calculate(left,right);
//	}
//	
//	public double calculate(double left,double right){
//		if(right-left<precision){
//			minX=(right-left)/2;
//			min=function(minX);
//			return min;
//		}
//		double half=(right+left)/2;
//		double dFx=derivativeFunction(half);
//		if(dFx==0){ //dFx==0
//			minX=half;
//			min=function(minX);
//			return min;
//		}
//		if(dFx>0){
//			return calculate(left,half);
//		}else{
//			return calculate(half,right);
//		}
//	}
//	
//}
