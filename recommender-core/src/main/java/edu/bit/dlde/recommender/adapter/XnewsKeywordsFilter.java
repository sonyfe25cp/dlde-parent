package edu.bit.dlde.recommender.adapter;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.bit.dlde.recommender.model.Xnews;

/**
 * @author ChenJie
 * 
 * 关键词过滤器实现
 *
 */
public class XnewsKeywordsFilter implements IKeywordsAdapter{
	private Log log = LogFactory.getLog(XnewsKeywordsFilter.class);
	
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	
	@SuppressWarnings("unused")
	private double defaultWeight=0.03;
	
	public double getSimilarity(List<String> keywordsList, Xnews item) {
		if(keywordsList == null ||keywordsList.size() <=0)
		{
			//System.out.println("keywordsList = 0 or null;sim:0\n");
			return 0;
		}
			
			Map<String, Double> title = TermWeightAdapter.generateTermWeightMap(item.getTitle(), true);
			Map<String, Double> content=TermWeightAdapter.generateTermWeightMap(item.getContent(), true);
			
			double tcount = 0.0;
			double ccount = 0.0;
			
			for(String key:title.keySet())
			{
				tcount+=title.get(key);
			}
			for(String key:content.keySet())
			{
				ccount+=content.get(key);
			}
			
			double tvalue = 0;
			double cvalue = 0;
			if(title.size()==0){
				tvalue = 0;
			}
			else
			{
				tvalue = DLDESimilarityAdapter.getJaccardCoefficient(keywordsList,title,tcount);
			}
			if(content.size()==0||content.size()==0){
				cvalue = 0;
			}
			else
			{
				cvalue = DLDESimilarityAdapter.getJaccardCoefficient(keywordsList,content,ccount);
			}
			
			if(tvalue + cvalue <=0)
			{
				//System.out.println("tvalue + cvalue <=0;sim:0\n");
				return 0;
			}
			else
			{
				//System.out.println("tvalue:"+tvalue+"; cvalue:"+cvalue+"; sim:"+(cvalue+tvalue-cvalue*tvalue)+"\n");
				return cvalue+tvalue-cvalue*tvalue;
			}
			
		
	}
	public void setDefaultWeight(double defaultWeight) {
		this.defaultWeight = defaultWeight;
	}

}
