import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.utils.DLDEConfiguration;
import edu.bit.dlde.utils.DLDELogger;


public class TestConfiguration extends TestCase{
	
	private DLDELogger log=new DLDELogger();

	@Test
	public void testConfigFile(){
		
		String configFile="searchserver.properties";
		
		DLDEConfiguration config=DLDEConfiguration.getInstance(configFile);
		String user;
		try {
			user = config.getValue("indexPath");
			log.info(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
