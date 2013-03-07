package edu.bit.dlde.recommender;

import edu.bit.dlde.recommender.model.Xnews;

public interface iRecommenderEngine {	
	/**
	 * predict
	 * @throws Exception 
	 */
	public void predict(Xnews source) throws Exception;
}