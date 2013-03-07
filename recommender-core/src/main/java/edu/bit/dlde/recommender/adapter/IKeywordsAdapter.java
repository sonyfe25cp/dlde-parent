package edu.bit.dlde.recommender.adapter;

import java.util.List;

import edu.bit.dlde.recommender.model.Xnews;

/**
 * @author ChenJie
 * 
 * 关键词跟物品相似度的接口
 * 
 */
public interface IKeywordsAdapter {

	public double getSimilarity(List<String> keywordsList,Xnews item);
	
}
