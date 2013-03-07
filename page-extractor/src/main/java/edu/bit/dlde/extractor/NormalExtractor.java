package edu.bit.dlde.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 普通的抽取
 * @author ChenJie
 *
 */
public class NormalExtractor {

	String regexLink="<a.*</a>";
	String regexScript="<.*?>";
	private Pattern pattern=null;
	private Matcher matcher=null;
	
	public NormalExtractor(){
	}
	/**
	 * 抽取所有的链接
	 * @param html
	 * @return
	 * Aug 1, 2012
	 */
	public List<String> getLinksFromHtml(String html){
		pattern=Pattern.compile(regexLink);
		matcher=pattern.matcher(html);
		List<String> links=new ArrayList<String>();
		String link="";
		while(matcher.find()){
			link=matcher.group();
			links.add(link);
		}
		return links;
	}
	/**
	 * 抽取所有的锚文本，以空格间隔
	 * @param html
	 * @return
	 * Aug 1, 2012
	 */
	public String getAnchorFromHtml(String html){
		List<String> links=getLinksFromHtml(html);
		StringBuilder sb=new StringBuilder();
		String anchor="";
		for(String link:links){
			anchor=link.replaceAll(regexScript, "");//去掉各种脚本，标签
			if(anchor.length()>0)
				sb.append(anchor+" ");
		}
		return sb.toString();
	}
}
