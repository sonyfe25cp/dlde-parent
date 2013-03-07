package store;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import bit.crawl.store.PageStoreReader;
import bit.crawl.store.StoredPage;
import edu.bit.dlde.utils.DLDELogger;

public class testReader extends TestCase{
	private DLDELogger logger=new DLDELogger();
	@Test
	public void testPageReader(){
		String path=this.getClass().getClassLoader().getResource(".").getPath();
		String testFilePath=path+"ofweek_2011-12-30-13-49-32.pages";
		File testFile=new File(testFilePath);
		PageStoreReader psr=new PageStoreReader(testFile);
		ArrayList<StoredPage> list=psr.loadAll();
		
		for(StoredPage page:list){
			logger.info(page.toString());
			logger.info("******************************************");
			logger.info("url:"+page.getHeader("URL"));
		}
		logger.info("共:"+list.size()+"个文档");
		assertEquals(21,list.size());
		
	}
	

}
