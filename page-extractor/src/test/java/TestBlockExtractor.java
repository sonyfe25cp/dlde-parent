import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.extractor.BlockExtractor;

public class TestBlockExtractor extends TestCase {

	@Test
	public void testIt() throws FileNotFoundException {
		BlockExtractor be = new BlockExtractor();
		String filePath = "src/test/resources/russellbailyn.html";
		be.setReader(new FileReader(new File(filePath)));
		be.extract();
		System.out.println(be.getTitle());
		System.out.println(be.getContent());
	}
}
