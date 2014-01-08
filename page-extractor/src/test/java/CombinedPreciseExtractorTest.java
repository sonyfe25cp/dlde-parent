import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.extractor.CombinedPreciseExtractor;

public class CombinedPreciseExtractorTest extends TestCase {
	@Test
	public void testCPE() throws FileNotFoundException {
		CombinedPreciseExtractor cpe = new CombinedPreciseExtractor();
//		/*
//		 * case 1
//		 */
//		cpe.configWith(new File("src/test/resources/gn-news.xml"));
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/gn-news.html")), "chinanews");
//		System.out.println(cpe.extract());
//
//		/*
//		 * case 2
//		 */
//		File xml2 = new File("src/test/resources/bit.xml");
//		cpe.configWith(xml2);
//		cpe.setResource(
//				new FileReader(new File("src/test/resources/bit.html")), "bit");
//		System.out.println(cpe.extract());
//
//		/*
//		 * case 3
//		 */
//		File xml3 = new File("src/test/resources/fenghuang.xml");
//		cpe.configWith(xml3);
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/fenghuang.html")), "fenghuang");
//		System.out.println(cpe.extract());
//		
//		/*
//		 * case 4
//		 */
//		File xml4 = new File("src/test/resources/gn-news-qq.xml");
//		cpe.configWith(xml4);
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/gn-news-qq.html")), "gn-news-qq");
//		System.out.println(cpe.extract());
		
		/*
		 * case 5
		 */
		File xml5 = new File("src/test/resources/gn-news-qq-new.xml");
		cpe.configWith(xml5);
		cpe.setResource(new FileReader(new File(
				"src/test/resources/004222.html")), "gn-news-qq");
//		cpe.extract();
		System.out.println(cpe.extract());
//		System.out.println(result);
		
		/**
		 * case 6
		 */
		File xml6 = new File("src/test/resources/gn-news-qq-new.xml");
		cpe.configWith(xml6);
		cpe.setResource(new FileReader(new File(
				"src/test/resources/012732.html")), "gn-news-qq");
//		cpe.extract();
		System.out.println(cpe.extract());
	}

}
