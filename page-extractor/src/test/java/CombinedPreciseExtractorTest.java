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
		
		
		/**
		 * case 5 
		 */
		//File xml5 = new File("src/test/resources/zh1.xml");
		//cpe.configWith(xml5);
		//cpe.setResource(new FileReader(new File(
		//		"src/test/resources/zh1.html")), "zh1");
		//System.out.println(cpe.extract());
		
		
		/*
		 * case 6
		 */
//		File xml6 = new File("src/test/resources/bitpt.xml");
//		cpe.configWith(xml6);
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/bitpt.html")), "bitpt");
//		System.out.println(cpe.extract());
		
		/*
		 * case 7
		 */
//		File xml7 = new File("src/test/resources/zhilian.xml");
//		cpe.configWith(xml7);
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/fenghuang.html")), "zhilian");
//		System.out.println(cpe.extract());
		
		/*
		 * case 8
		 */
//		File xml8 = new File("src/test/resources/bitu.xml");
//		cpe.configWith(xml8);
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/bitu")), "bitu");
//		System.out.println(cpe.extract());
		
		/*
		 * case 9
		 */
//		File xml9 = new File("src/test/resources/1feng.xml");
//		cpe.configWith(xml9);
//		cpe.setResource(new FileReader(new File(
//				"src/test/resources/1feng.html")), "1feng");
//		System.out.println(cpe.extract());
		
		/*
		 * case 10
		 */
		File xml10 = new File("src/test/resources/zhilian-details.xml");
		cpe.configWith(xml10);
		cpe.setResource(new FileReader(new File(
				"src/test/resources/zhilian-details.html")), "zhilian");
		System.out.println(cpe.extract());
	}

}
