package edu.bit.dlde.utils;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * {@code DLDELineReader} 的操作类
 * @author ChenJie
 *
 */
public abstract class DLDELineReaderOperation implements Observer{

	private Vector<String> lineList;
	public void update(Observable o, Object arg) {
		if(arg instanceof Vector){
			lineList=(Vector)arg;
			operation(lineList);
			DLDELineReader.clear();
		}
	}
	/**
	 * 具体操作
	 * @param lineList
	 * Mar 5, 2012
	 */
	public abstract void operation(Vector<String> lineList);
}
