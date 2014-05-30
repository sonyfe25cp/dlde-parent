import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class TestJsoupParse extends TestCase{

    
    public void testParse001(){
        InputStream is = TestJsoupParse.class.getClassLoader().getResourceAsStream("chinahr001.html");
        List<String> lines = null;
        try {
            lines = IOUtils.readLines(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            sb.append(line+"\n");
        }
        String html = sb.toString();
        Document doc = Jsoup.parse(html);
        String body = doc.text();
        System.out.println(body);
    }
    
}
