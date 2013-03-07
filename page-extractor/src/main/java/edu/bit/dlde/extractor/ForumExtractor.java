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

		long id = 0;
		LinkedHashMap<String, String> c2v = new LinkedHashMap<String, String>();
		try {
			CleanerProperties props = cleaner.getProperties();
			props.setNamespacesAware(false);
			TagNode root = cleaner.clean(_reader);

			Object[] title = root.evaluateXPath(rule.getExprValue(0));
			int j = 0;
			for (Object t : title) {
				TagNode n = (TagNode) t;
				String content = n.getText().toString();
				c2v.put(j + "-" + rule.getExprName(0), content);
				j++;
			}
			// get frame nodes
			String frameExpr = rule.getExprValue(1);
			Object[] frames = root.evaluateXPath(frameExpr);

			// iterate into each frame node
			for (Object frame : frames) {
				// System.out.println("12819281937");
				TagNode fNode = (TagNode) frame;
				for (int i = 2; i < rule.getExprsSize(); i++) {
					String name = rule.getExprName(i);
					String value = rule.getExprValue(i);
					Object[] nodes = fNode.evaluateXPath(value);

					for (Object node : nodes) {
						TagNode n = (TagNode) node;
						String content = n.getText().toString().trim();
						c2v.put(id + "-" + name, content);
//						logger.info("Extract result: " + name + ":" + content);
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
