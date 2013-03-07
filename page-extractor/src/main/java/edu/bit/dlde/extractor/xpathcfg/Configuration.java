package edu.bit.dlde.extractor.xpathcfg;

import java.io.File;
import java.util.List;

/**
 * Interface to parse configuration. {@link XmlConfigurationImpl} is its impl
 * with xml as cofigure file.
 */
public interface Configuration {
	public List<Rule> getConfigs(File f);
	public List<Rule> getConfigs(String xml);
	public Rule getConfig(String xml);
}
