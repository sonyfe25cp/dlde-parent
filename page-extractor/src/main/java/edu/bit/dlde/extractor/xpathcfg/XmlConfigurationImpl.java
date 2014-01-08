package edu.bit.dlde.extractor.xpathcfg;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlConfigurationImpl implements Configuration {
	static Logger logger = Logger.getLogger(XmlConfigurationImpl.class);

	XPath xpath = XPathFactory.newInstance().newXPath();

	public XmlConfigurationImpl() {
	}

	public List<Rule> getConfigs(Document doc) {
		List<Rule> rules = new ArrayList<Rule>();

		try {
			XPathExpression xPathExpr1 = xpath.compile("rules/rule");
			XPathExpression xPathExpr2 = xpath.compile("*/text()");
			NodeList pnodes = (NodeList) xPathExpr1.evaluate(doc,
					XPathConstants.NODESET);

			for (int i = 0; i < pnodes.getLength(); i++) {
				NodeList cnodes = (NodeList) xPathExpr2.evaluate(
						pnodes.item(i), XPathConstants.NODESET);
				Rule r = new Rule();
				for (int j = 0; j < cnodes.getLength(); j++) {
					String tag = cnodes.item(j).getParentNode().getNodeName();
					String value = cnodes.item(j).getNodeValue();
					logger.debug("Xpath: " + tag + " - " + value);
					if (Rule.unExpr.contains(tag)){
						r.addUnExpr(tag, value);
					}
					else{
						Node nodeCurrent = cnodes.item(j).getParentNode();
						NamedNodeMap nnm = nodeCurrent.getAttributes();
						int size = nnm.getLength();
						String anti = null;
						for(int index = 0 ; index < size ; index++){
							Node tmp = nnm.item(i);
							if(tmp.getNodeName().equals("anti")){
								anti = tmp.getNodeValue();
							}
						}
						if(anti == null){
							r.addExpr(cnodes.item(j).getParentNode().getNodeName(), cnodes.item(j).getNodeValue());
						}else{
							r.addExpr(nodeCurrent.getNodeName(), cnodes.item(j).getNodeValue(), anti);
						}
					}
				}
				rules.add(r);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} 
		return rules;
	}
	
//	public List<Rule> getConfigs(File f) {
//		List<Rule> rules = new ArrayList<Rule>();
//
//		if (!f.exists()) {
//			logger.info("Can't find file " + f.getName());
//			throw new ConfigureFileNotFoundException(f.getName());
//		}
//
//		try {
//			DocumentBuilderFactory docFactory = DocumentBuilderFactory
//					.newInstance();
//			docFactory.setNamespaceAware(true); // never forget this!
//			Document doc = docFactory.newDocumentBuilder().parse(f);
//			XPathExpression xPathExpr1 = xpath.compile("rules/rule");
//			XPathExpression xPathExpr2 = xpath.compile("*/text()");
//
//			NodeList pnodes = (NodeList) xPathExpr1.evaluate(doc,
//					XPathConstants.NODESET);
//
//			for (int i = 0; i < pnodes.getLength(); i++) {
//				NodeList cnodes = (NodeList) xPathExpr2.evaluate(
//						pnodes.item(i), XPathConstants.NODESET);
//				Rule r = new Rule();
//				for (int j = 0; j < cnodes.getLength(); j++) {
//					Node currentNode = cnodes.item(j).getParentNode();
//					String tag = currentNode.getNodeName();
//					String value = cnodes.item(j).getNodeValue();
//					logger.info("Xpath: " + tag + " - " + value);
//					if (Rule.unExpr.contains(tag))
//						r.addUnExpr(tag, value);
//					else{
//						r.addExpr(tag, value);
//					}
//				}
//				rules.add(r);
//			}
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//
//		return rules;
//	}
	
	
	public List<Rule> getConfigs(File f) {
		List<Rule> rules = null;

		if (!f.exists()) {
			logger.error("Can't find file " + f.getName());
			throw new ConfigureFileNotFoundException(f.getName());
		}
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			docFactory.setNamespaceAware(true); // never forget this!
			Document doc = docFactory.newDocumentBuilder().parse(f);
			rules = getConfigs(doc);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return rules;
	}

	public List<Rule> getConfigs(String xml) {
		List<Rule> rules = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			docFactory.setNamespaceAware(true); // never forget this!
			Document doc = docFactory.newDocumentBuilder().parse(xml);
			rules = getConfigs(doc);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return rules;
	}

	public Rule getConfig(String xml) {
		Rule rule = new Rule();

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			docFactory.setNamespaceAware(true); // never forget this!
			Document doc = docFactory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			XPathExpression xPathExpr1 = xpath.compile("rule/*/text()");

			NodeList nodes = (NodeList) xPathExpr1.evaluate(doc,
					XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {

				String tag = nodes.item(i).getParentNode().getNodeName();
				String value = nodes.item(i).getNodeValue();
				logger.debug("Xpath: " + tag + " - " + value);
				if (Rule.unExpr.contains(tag))
					rule.addUnExpr(tag, value);
				else
					rule.addExpr(nodes.item(i).getParentNode().getNodeName(),
							nodes.item(i).getNodeValue());
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return rule;
	}

	public static void main(String[] args) {
		XmlConfigurationImpl cfg = new XmlConfigurationImpl();
		cfg.getConfig("<rule><title>.//*[@id='artibodyTitle']</title><content>.//*[@id='artibody']/p</content></rule> ");
	}
}
