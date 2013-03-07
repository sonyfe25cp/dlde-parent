package edu.bit.dlde.recommender.adapter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

/**
 * @author ChenJie
 * 
 * 词重计算
 */
public class TermWeightAdapter {
	
	
	public static Map<String, Double> generateTermWeightMap(String text,boolean chineseOnly){
		Map<String, Double> tw = new HashMap<String, Double>();
		if(text==null||text.length()==0){
			return tw;
		}
        Bag words = analyze(text,chineseOnly);		
		if (!words.isEmpty()) {
            Iterator<?> ix = words.uniqueSet().iterator();
            while (ix.hasNext()) {
                String term = (String)ix.next();
//                System.out.println(term);
                tw.put(term, new Double(calculateTermWeight(words, term)));
            }
        }
        return tw;
	}

	public static double calculateTermWeight(Bag terms, String term) {
        return (double)terms.getCount(term);
    }
	private static String reg="\\w+";
	private static Pattern pattern = Pattern.compile(reg);
	
	protected static Bag analyze(String text,boolean chineseOnly) {
        Bag words = new HashBag();
        Reader textReader = new StringReader(text);
        IKSegmentation seg=new IKSegmentation(textReader,true);
        Lexeme lex=null;
		try {
			lex = seg.next();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        String word=null;
        Matcher matcher=null;
		while(lex!=null){
			try {
				word=lex.getLexemeText();
//				System.out.println(word);
				if(chineseOnly){
					//remove English
			        matcher = pattern.matcher(word);
					boolean flag=matcher.find();
//					System.out.println(flag);
					if(!flag){
						words.add(word);
					}
				}else{
					words.add(word);
				}
				lex=seg.next();
			} catch (Exception e) {
				break;
			}	
		}
        return words;
    }
}
