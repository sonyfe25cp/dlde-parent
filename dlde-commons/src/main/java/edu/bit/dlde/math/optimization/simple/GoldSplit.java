//package edu.bit.dlde.math.optimization.simple;
//
//
///**
// * 黄金分割法--函数只要连续即可
// * @author ChenJie
// *
// */
//public class GoldSplit extends SimpleOptimization{
//	
//	public void compute(){
//		init();
//		min=calculate(left,right,0,INIT);
//	}
//	
//	/**
//	 * 黄金分割法算法
//	 * @param left 左边界
//	 * @param right 右边界
//	 * @param fx 缓存的函数值
//	 * @param flag 标志往左还是往右
//	 * @return
//	 * Mar 8, 2012
//	 */
//	private double calculate(double left,double right,double fx,int flag){
//		double ltemp=right-(goldParam*(right-left));//左探测点
//		double rtemp=left+(goldParam*(right-left));//右探测点
//		
//		double lfx=(flag==LEFT?fx:function(ltemp));//左探测点的值
//		double rfx=(flag==RIGHT?fx:function(rtemp));//右探测点的值
//		
//		if(right-left<precision){
//			minX=(right-left)/2;
//			return function(minX);
//		}else{
//			if(lfx<rfx){
//				return calculate(left,rtemp,lfx,LEFT);
//			}else{
//				return calculate(ltemp,right,rfx,RIGHT);
//			}
//		}
//	}
//
//	public static double goldParam=(Math.sqrt(5.0)-1)/2;
//
//	
//}
