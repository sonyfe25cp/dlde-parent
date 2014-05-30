//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
//import junit.framework.TestCase;
//
//import org.apache.commons.io.FileUtils;
//import org.junit.Test;
//
//import edu.bit.dlde.extractor.NormalExtractor;
//
//
//public class TestNormalExtractor extends TestCase{
//
//	@Test
//	public  void testExtractLinks() throws FileNotFoundException{
//		NormalExtractor ne=new NormalExtractor();
//		String filePath="src/test/resources/russellbailyn.html";
//		String html="";
//		try {
//			html = FileUtils.readFileToString(new File(filePath));
////			System.out.println(html);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<String> links=ne.getLinksFromHtml(html);
//		for(String link : links){
//			System.out.println(link);
//		}
//		System.out.println("-----------------------------------------");
//	}
//	@Test
//	public  void testExtractAnchor() throws FileNotFoundException{
//		NormalExtractor ne=new NormalExtractor();
//		String filePath="src/test/resources/russellbailyn.html";
//		String html="";
//		try {
//			html = FileUtils.readFileToString(new File(filePath));
////			System.out.println(html);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String anchor=ne.getAnchorFromHtml(html);
//		System.out.println(anchor);
//	}
//}
