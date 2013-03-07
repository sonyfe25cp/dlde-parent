package math.optimization.simple;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.math.BaseFunction;
import edu.bit.dlde.math.optimization.simple.GoldSplit;

public class TestGoldSpilt extends TestCase{

	@Test
	public void testIt(){
		PracticeGoldSpilt pract=new PracticeGoldSpilt();
		double f2=pract.function(2);
		System.out.println(f2);
		double pres=0.1;
		
		GoldSplit gs=new GoldSplit();
		gs.setBase(pract);
		gs.setPrecision(pres);
		gs.setLeft(-1);
		gs.setRight(1);
		gs.compute();
		double min=gs.getMin();
		double minX=gs.getMinX();
		System.out.println("x= "+minX+"\nf(x)= "+min);
	}
}
class PracticeGoldSpilt implements BaseFunction{

	//f(x)=e^(-x)+x^2
	

	@Override
	public double function(double... args) {
		double x=args[0];
		double fx=Math.exp(-x)+x*x;
		return fx;
	}
	
}