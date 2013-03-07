package edu.bit.dlde.recommender.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.bit.dlde.recommender.model.Xnews;

/**
 * @author ChenJie 计算两个ITEM的相似度
 * 
 */
public class DLDEItemSimilarityBuilder {

	private Log log = LogFactory.getLog(DLDEItemSimilarityBuilder.class);

	DLDESimilarityAdapter adapter = new DLDESimilarityAdapter();

	IKeywordsAdapter keywordsAdapter = new XnewsKeywordsFilter();

	private double treshold = 0.25;
	public DLDEItemSimilarityBuilder() {
		new HashMap<String, List<Xnews>>();
	}

	/**
	 * update scores of a user
	 */
	public Map<Xnews, Double> UpdateSimilarities(String username,
			List<Xnews> userCollections, List<String> userInterests,
			List<Xnews> sources) {

		log.info("processing user:" + username);
		Map<Xnews, Double> recommenderList = new HashMap<Xnews, Double>();
		int sMax = sources == null ? 0 : sources.size();
		for (int i = 0; i < sMax; i++) {
			Xnews  source = sources.get(i);
			double similarity = CalculateSimilarities(userCollections,userInterests,source);
			if (similarity > treshold) {
				recommenderList.put(source, similarity);
			}
		}
		log.info("end processing user:" + username);
		return recommenderList;
	}

	public Double CalculateSimilarities(List<Xnews> userCollections, List<String> userInterests,
			Xnews source) {
		int cMax = userCollections == null ? 0 : userCollections.size();
		double similarity = 0;
		double ks = keywordsAdapter.getSimilarity(userInterests, source);
		
		if (cMax > 0) {// 用户有收藏数据
			for (int j = 0; j < cMax; j++) {
				Xnews collect = userCollections.get(j);
				double sim = adapter.getSimilarity(source, collect);
				double kim = ks * 2;
				similarity = similarity >= sim + kim ? similarity : sim + kim;
			}
		} else {// 用户无收藏数据
			similarity = ks * (treshold * 20);
		}
		if (similarity > treshold) {
			return similarity;
		}
		return 0.0;
	}
	public double getTreshold() {
		return treshold;
	}

	public void setTreshold(double treshold) {
		this.treshold = treshold;
	}

	public void setKeywordsAdapter(IKeywordsAdapter keywordsAdapter) {
		this.keywordsAdapter = keywordsAdapter;
	}

}
