package math.optimization.simple;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.math.BaseFunction;
import edu.bit.dlde.math.optimization.simple.ComeAndGo;

public class TestComeAndGo extends TestCase{

	@Test
	public void testAlg(){
		ComeAndGo cag=new ComeAndGo();
		cag.setBase(new TCAG());
		cag.setStep(0.001);
		cag.compute();
		System.out.print(cag.toString());
		
	}
}

class TCAG implements BaseFunction{
	@Override
	public double function(double... args) {
		return Math.cos(args[0]);
	}
	
}
