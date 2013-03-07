package edu.bit.dlde.recommender.adapter;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.bit.dlde.recommender.model.Xnews;

/**
 * @author ChenJie
 * 
 * 计算两个ITEM的相似度
 *
 */
public class DLDESimilarityAdapter{
	private Log log = LogFactory.getLog(DLDESimilarityAdapter.class);
	
	/**
	 * 计算两组单词、计数对的雅格系数
	 * @param map1 一组单词，计数对；可表示一篇文档的分词结果
	 * @param map2 另一组单词计数对；可表示一篇文档的分词结果
	 * @return 返回两组单词、计数对的雅格系数
	 */
	public static double getJaccardCoefficient(Map<String,Double> map1,Map<String,Double> map2){
		double cross =0;
		double sum = 0;
		for(String word:map1.keySet()){
			if(map2.containsKey(word)){
				Double c1 = map1.get(word);
				Double c2 = map2.get(word);
				cross+=c1 > c2?c2:c1;
			}
			sum+=map1.get(word);
		}
		for(String word:map2.keySet()){
			sum+=map2.get(word);
		}
		double union = sum-cross;
		//System.out.println("map1:"+map1);
		//System.out.println("map2:"+map2);
		//System.out.println("cross:"+cross+"; union:"+union);
		return cross/union;		
	}
	/**
	 * 
	 * @param keywords 关键词列表
	 * @param map 一组单词，计数对；可表示一篇文档的分词结果
	 * @param sum map的单词总数，即单词的计数之和
	 * @return 返回map与keywords的雅格系数，其中交集值为map中包含于keywords中的单词的计数之和，并集值为sum。
	 */
	public static double getJaccardCoefficient(List<String> keywords,Map<String,Double> map,double sum){
		double cross =0;
		for(String word:keywords){
			if(map.containsKey(word)){
				cross+=map.get(word);
			}
		}
		//System.out.println("map:"+map);
		//System.out.println("keywords:"+keywords);
		//System.out.println("cross(keyword number):"+cross+"; union(map count):"+sum);
		return cross/sum;		
	}
	public double getSimilarity(Xnews item, Xnews otherItem) {
			
			if(otherItem==null||item==null){
				//System.out.println("item or otherItem = null;sim:0\n");
				log.warn("item or otherItemId is null");
				return 0;
			}
			Map<String,Double> title1= TermWeightAdapter.generateTermWeightMap(item.getTitle(), true);
			Map<String,Double> title2= TermWeightAdapter.generateTermWeightMap(otherItem.getTitle(), true);
			Map<String,Double> content1=TermWeightAdapter.generateTermWeightMap(item.getContent(), true);
			Map<String,Double> content2=TermWeightAdapter.generateTermWeightMap(otherItem.getContent(), true);
			
			double tvalue = 0;
			double cvalue = 0;
			if(title1.size()==0||title2.size()==0){
				tvalue = 0;
			}
			else
			{
				tvalue = getJaccardCoefficient(title1,title2);
			}
			if(content1.size()==0||content2.size()==0){
				cvalue = 0;
			}
			else
			{
				cvalue = getJaccardCoefficient(content1,content2);
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
}
