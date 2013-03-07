import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.extractor.BlockExtractor;


public class TestReadFile extends TestCase{
	@Test
	public void testReadFile(){
		String html="/home/hz/workspace/java/HttpGet/out/page1.html";
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(html)));
			
			BlockExtractor she=new BlockExtractor();
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
