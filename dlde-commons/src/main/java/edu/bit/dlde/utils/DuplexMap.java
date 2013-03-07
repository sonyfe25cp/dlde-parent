package edu.bit.dlde.utils;

import java.util.HashMap;

/**
 * 双向hashmap...用于一一映射
 * @author ChenJie
 *
 * @param <K>
 * @param <V>
 */
public class DuplexMap<K,V> {
	class Entry{
		K k;
		V v;
		public Entry(K k,V v){
			this.k=k;
			this.v=v;
		}
		public K getK() {
			return k;
		}
		public V getV() {
			return v;
		}
		public void setK(K k) {
			this.k = k;
		}
		public void setV(V v) {
			this.v = v;
		}
	}
	private HashMap<K,Entry> kEntyMap=new HashMap<K,Entry>();
	private HashMap<V,Entry> vEntyMap=new HashMap<V,Entry>();
	public boolean contains(K k){
		return kEntyMap.containsKey(k);
	}
	public boolean containsValue(V v){
		return vEntyMap.containsKey(v);
	}
	public V getByKey(K k){
		Entry e=kEntyMap.get(k);
		if(e==null){
			return null;
		}
		return e.getV();
	}
	public K getbyValue(V v){
		Entry e=vEntyMap.get(v);
		if(e==null){
			return null;
		}
		return e.getK();
	}
	public boolean put(K k,V v){
		if(k==null||v==null){
			return false;
		}
		Entry e=new Entry(k, v);
		if(contains(k)){
			remove(k);
		}
		if(containsValue(v)){
			removeByValue(v);
		}
		kEntyMap.put(k, e);
		vEntyMap.put(v, e);
		return true;
	}
	public V remove(K k){
		Entry e=kEntyMap.remove(k);
		if(e==null){
			return null;
		}
		vEntyMap.remove(e.getV());
		return e.getV();
	}
	public K removeByValue(V v){
		Entry e=vEntyMap.remove(v);
		if(e==null){
			return null;
		}
		kEntyMap.remove(e.getK());
		return e.getK();
	}
}
