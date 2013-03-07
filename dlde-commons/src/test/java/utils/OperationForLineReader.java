package utils;

import java.util.Vector;

import edu.bit.dlde.utils.DLDELineReaderOperation;

public class OperationForLineReader extends DLDELineReaderOperation{

	@Override
	public void operation(Vector<String> lineList) {
		
		for(String tmp:lineList){
			System.out.println(tmp);
		}
		
	}

}
