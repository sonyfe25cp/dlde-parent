//package math.optimization.simple;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//
//import edu.bit.dlde.math.BaseFunction;
//import edu.bit.dlde.math.optimization.simple.HalfSplit;
//
//public class TestHalfSplit extends TestCase{
//
//	@Test
//	public void testHalf(){
//		PracticeHalfSplit phs=new PracticeHalfSplit();
//		
//		HalfSplit hs=new HalfSplit();
//		hs.setBase(phs);
//		
//		hs.setLeft(-1);
//		hs.setRight(1);
//		hs.setPrecision(0.1);
//		
//		hs.compute();
//		System.out.println(hs);
//		
//	}
//}
//class PracticeHalfSplit implements BaseFunction{
//
//	public double function(double... args) {
//		double x=args[0];
//		double fx=Math.exp(-x)+x*x;
//		return fx;
//	}
//
//}
