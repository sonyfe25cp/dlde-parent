/**
 * 
 */
package edu.bit.dlde.recommender.utils.clusters;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author horizon
 *
 */
public class ClusterCenterMethod {

	/**
	 * 
	 */
	private  Map<String,Double> SSimStore =null;
	private  Distance SDistance = null;
	private  List<Item> SSources = null;
	private  List<Cluster> Clusters = null;
	public ClusterCenterMethod() {
		// TODO Auto-generated constructor stub
	}
	private  double getDistance(Integer i,Integer j)
	{
		
		String key = null;
		if(i>j)
		{
			key = j.toString()+"&"+i.toString();
		}
		else
		{
			key = i.toString()+"&"+j.toString();
		}
		Double value = SSimStore.get(key);
		if(value == null)
		{
			value = SDistance.getDistance(SSources.get(i), SSources.get(j));
			SSimStore.put(key, value);
		}
		return value;
	}

	public  List<Item> getKMedoidsCenter(List<Item> sources,Distance distance,int centerNum)
	{
		if(centerNum<=0)
		{
			return null;
		}
		if(sources == null||centerNum >=sources.size())
		{
			return sources;
		}
		
		SSimStore = new HashMap<String, Double>();
		SDistance = distance;
		SSources = sources;
		Clusters = new ArrayList<Cluster>();
		
		//init center
		for(int i=0;i<centerNum;i++)
		{
			Clusters.add(new Cluster(i));
		}
		double lastE = Dispatch();
		Double MinE = lastE;
		Double temp;
		int tarI = -1;
		int tarH = -1;
		List<Integer> notCenters = new ArrayList<Integer>();
		boolean isCenter = false;
		int count = 0;
		while(count<20)
		{
			MinE = null;
			tarI = -1;
			tarH = -1;
			notCenters.clear();
			for(int i =0;i<SSources.size();i++)
			{
				isCenter = false;
				for(Cluster cluster:Clusters)
				{
					if(i == cluster.getCenter())
					{
						isCenter = true;
						break;
					}
				}
				if(!isCenter)
				{
					notCenters.add(i);
				}
			}
			//System.out.println("not centers:"+notCenters);
			for(Integer h:notCenters)
			{
				for(int i = 0;i<Clusters.size();i++)
				{
					temp = ReplaceCenter(h, i);
					if(temp!=null)
					{
						if(MinE== null)
						{
							MinE = temp;
							tarH = h;
							tarI = i;
						}
						else if(MinE > temp)
						{
							MinE = temp;
							tarH = h;
							tarI = i;
						}
					}
				}
			}
			System.out.println("MinE="+MinE+"lastE="+lastE);
			count++;
			if(MinE!=null&&MinE < lastE)
			{
				Clusters.get(tarI).setCenter(tarH);
				lastE = Dispatch();
				System.out.println("new E : "+MinE+" /"+MinE.equals(lastE));
			}
			else
			{
				Dispatch();
				System.out.println("success");
				break;
			}			
		}

		System.out.println("times:"+count);
		List<Item> result = new ArrayList<Item>();
		for(Cluster cluster:Clusters)
		{
			result.add(SSources.get(cluster.getCenter()));
//			System.out.print("Center:");
//			SSources.get(cluster.getCenter()).print();
//			System.out.print("	Member:");
//			for(Integer i:cluster.getClusterMembers()){
//				SSources.get(i).print();
//			}
//			System.out.println();
		}
		return  result;
	}
	public Double Dispatch()
	{
		Double sum = 0.0;
		//System.out.println("Dispatch step:");
		NumberFormat nmf=NumberFormat.getInstance();
        nmf.setMaximumFractionDigits(3);
		
        Double min = null;
		Double temp = new Double(-1);
		Cluster tar = null;
		//System.out.print("	\\");
		for(Cluster c:Clusters)
		{
			//SSources.get(c.getCenter()).print();
			c.getClusterMembers().clear();
		}
		//System.out.println();
		for(int i =0;i<SSources.size();i++)
		{
			min = null;
			tar = null;
			//SSources.get(i).print();
			for(Cluster j:Clusters)
			{
				temp = getDistance(i, j.getCenter());
				//System.out.print("	"+nmf.format(temp));
				if(min == null||min >temp)
				{
					min = temp;
					tar = j;
				}
			}
			if(tar==null)
			{
				try {
					throw new Exception("the tar value should not be null,somthing wrong here");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				tar.getClusterMembers().add(i);
				sum+=min;
				//System.out.println("	dispatch to "+tar.getCenter()+" with value "+nmf.format(min));
			}
		}
		//System.out.println("sum="+sum);
		return sum;
	}
	public Double ReplaceCenter(int h,int i)
	{
		int oldcenter = 0;
		if(i>Clusters.size()||h>SSources.size())
		{
			return null;
		}
		else
		{
			//System.out.println("ReplaceCenter swap:"+Clusters.get(i).getCenter()+" to "+h);
			oldcenter = Clusters.get(i).getCenter();
			Clusters.get(i).setCenter(h);
			double result = Dispatch();
			Clusters.get(i).setCenter(oldcenter);
			return result;
		}
	}
	
	public class Cluster{
		private int center;
		public int getCenter() {
			return center;
		}
		public void setCenter(int center) {
			this.center = center;
		}
		private List<Integer> clusterMembers = new ArrayList<Integer>();
		public Cluster(int center)
		{
			this.setCenter(center);
		}
		public void setClusterMembers(List<Integer> clusterMembers) {
			this.clusterMembers = clusterMembers;
		}
		public List<Integer> getClusterMembers() {
			return clusterMembers;
		}
		public void print()
		{
//			SSources.get(center).print();
//			for(Integer cm:clusterMembers)
//			{				
//				SSources.get(cm).print();
//			}
//			System.out.println();
		}		
	}
	public interface Item{
		public void print();
	}
	public interface Distance{
		public Double getDistance(Item i1,Item i2);
	}

}
