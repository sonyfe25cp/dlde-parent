

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.extractor.SimpleHtmlExtractor;

public class TestSimpleExtractor extends TestCase{
	
	@Test
	public void testExtractor(){
		String html="src/test/resources/russellbailyn.html";
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(html)));
			SimpleHtmlExtractor she=new SimpleHtmlExtractor();
			she.setReader(br);
			she.extract();
			String content=she.getContent();
			System.out.println("title:"+she.getTitle());
			System.out.println(content);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

}
