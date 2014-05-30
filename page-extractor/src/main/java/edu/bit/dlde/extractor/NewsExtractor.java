package edu.bit.dlde.extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import edu.bit.dlde.extractor.skeleton.PreciseExtractor;
import edu.bit.dlde.extractor.xpathcfg.Rule;

/**
 * Subclass of {@link PreciseExtractor}. And this class is dedicated to news.
 * Strategy pattern is used here. Method
 * {@link PreciseExtractor#extract(String file, String charset)} should be
 * override in this class.
 * 
 * @author linss
 * @version 1.0
 * @see PreciseExtractor
 * @since 1.6
 */
public class NewsExtractor extends PreciseExtractor {
	static Logger logger = Logger.getLogger(PreciseExtractor.class);

	public LinkedHashMap<String, String> extract() {
		if (_reader == null)
			return null;

		// get suitable rule;
		Rule rule = findFitRule();
		if (rule == null || !rule.isEnabled()){//在哪儿设定的isEnable？？
			System.out.println("解析模板为空or解析状态为不可用！");
			return null;
		}

		LinkedHashMap<String, String> c2v = new LinkedHashMap<String, String>();
		try {
			CleanerProperties props = cleaner.getProperties();
			props.setOmitComments(true);
			props.setNamespacesAware(false);
			TagNode root = cleaner.clean(_reader);
			for(TagNode tmpp : root.getAllElements(true)){
				String nameTmp = tmpp.getName();
				if(
					nameTmp.equalsIgnoreCase("script") ||
					nameTmp.equalsIgnoreCase("style") ||
					nameTmp.equalsIgnoreCase("iframe")
					
				){
					tmpp.removeFromTree();
				}
			}
//			for(TagNode tmpp : root.getAllElements(true)){
//				String nameTmp = tmpp.getName();
////				System.out.println(nameTmp );//+"-------" +tmpp.getText().toString().substring(0, 10));
//			}
			for (int i = 0; i < rule.getExprsSize(); i++) {
				String name = rule.getExprName(i);
				String value = rule.getExprValue(i);
				String anti = rule.getAnti(i);
				System.out.println(name);
				System.out.println(value);
				System.out.println(anti);
				Object[] nodes = root.evaluateXPath(value);
				Object[] nodesAnti = null;
				if(anti != null && anti.length() > 0){
					nodesAnti = root.evaluateXPath(anti);
				}
				for (Object node : nodes) {
					TagNode n = (TagNode) node;
					TagNode[] arrayTmp = n.getChildTags();
					if(nodesAnti != null && nodesAnti.length > 0){
						ArrayList<Object> result = removeBFromA(arrayTmp, nodesAnti);
						StringBuffer sb = new StringBuffer();
						for(Object nodeTmp : result){
							TagNode nnn = (TagNode) nodeTmp;
							String content = nnn.getText().toString().trim();
							sb.append(content);
						}
						c2v.put(name, sb.toString());
					}else{
						String content = n.getText().toString().trim();
						c2v.put(name, content);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPatherException e) {
			System.out.println("解析xpath错误！");
		}finally{
			try {
				_reader.close();
			} catch (IOException e) {
				System.out.println("关闭解析文件错误！");
			}
		}
		return c2v;
	}

	private ArrayList<Object> removeBFromA(TagNode[] A, Object[] B){
		ArrayList<Object> array = new ArrayList<Object>();
		for(Object a : A){
			for(Object b : B){
				TagNode bb = (TagNode)b;
				if(a.equals(bb)){
					
				}else{
					array.add(a);
				}
			}
		}
		return array;
	}
	
	@Override
	public void addRules(ArrayList<Rule> rules) {
		for (Rule r : rules) {
			if (r._siteType.equals("news"))
				_rules.add(r);
		}
	}

	@Override
	public void addRule(Rule rule) {
		if ("news".equals(rule._siteType))
			_rules.add(rule);
	}
}
