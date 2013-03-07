/**
 * 
 */
package edu.bit.dlde.recommender;

import edu.bit.dlde.recommender.adapter.DLDEItemSimilarityBuilder;
import edu.bit.dlde.recommender.model.Xnews;

/**
 * @author horizon
 *
 */
public class RecommenderEngine implements iRecommenderEngine{

	protected DLDEItemSimilarityBuilder dldeisb = null;
	//protected List<IRatableItem> sources = null;
	public RecommenderEngine() {
		// TODO Auto-generated constructor stub
		dldeisb = new DLDEItemSimilarityBuilder();
	}

	public void predict(Xnews source) throws Exception {
		// TODO Auto-generated method stub
	}
}
