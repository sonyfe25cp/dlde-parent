import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.utils.DLDELogger;


public class TestDLDELogger extends TestCase {

	private static DLDELogger logger=new DLDELogger();
	
	@Test
	public void testLogger(){
		File f =null;
		try{
			logger.info(f.getName());
		}catch(NullPointerException e){
			logger.error("f is null !");
		}
	}
	
}
