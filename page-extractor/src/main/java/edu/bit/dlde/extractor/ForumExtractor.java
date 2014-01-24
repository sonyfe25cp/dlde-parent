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
 * Subclass of {@link PreciseExtractor}. And this class is dedicated to forum.
 * Strategy pattern is used here. Method
 * {@link PreciseExtractor#extract(String file, String charset)} should be
 * override in this class.
 * 
 * @author linss
 * @version 1.0
 * @see PreciseExtractor
 * @since 1.6
 */
public class ForumExtractor extends PreciseExtractor {
	static Logger logger = Logger.getLogger(ForumExtractor.class);

	public LinkedHashMap<String, String> extract() {
		if (_reader == null)
			return null;

		// get suitable rule;
		Rule rule = findFitRule();
		if (rule == null || !rule.isEnabled())
			return null;

		LinkedHashMap<String, String> c2v = new LinkedHashMap<String, String>();
		try {
			CleanerProperties props = cleaner.getProperties();
			props.setNamespacesAware(false);
			props.setOmitComments(true);
			TagNode root = cleaner.clean(_reader);
			
			// get frame nodes
			String frameExpr = rule.getExprByName(Rule.MAINFRAME);
			Object[] frames = root.evaluateXPath(frameExpr);
			logger.info("frame expr: "+ frameExpr +" -- frames.length:"+ frames.length);
			// iterate into each frame node
			int id = 0;
			for (Object frame : frames) {
				TagNode fNode = (TagNode) frame;
				for (int i = 0; i < rule.getExprsSize(); i++) {
					String name = rule.getExprName(i);
					if(name.equalsIgnoreCase(Rule.MAINFRAME)){//不需要存frame
						continue;
					}
					String value = rule.getExprValue(i);
					Object[] nodes = fNode.evaluateXPath(value);
					System.out.println("nodes.length:"+nodes.length);
					for (Object node : nodes) {
						TagNode n = (TagNode) node;
						String content = n.getText().toString().trim();
						c2v.put(id + "-" + name, content);
						logger.info("Extract result: " + name + ":" + content);
					}
				}
				id++;
			}
			_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return c2v;
	}

	@Override
	public void addRules(ArrayList<Rule> rules) {
		for (Rule r : rules) {
			if (r._siteType.equals("forum"))
				_rules.add(r);
		}
	}

	@Override
	public void addRule(Rule rule) {
		if ("forum".equals(rule._siteType))
			_rules.add(rule);
	}
}
