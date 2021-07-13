package wordCloud;

import java.util.*;


public class ValueCompare implements Comparator{
	private Map<String,Integer> wordMap;

	public ValueCompare(Map map){
		wordMap=map;
	}
	
	@Override
	public int compare(Object key1, Object key2) {
		Comparable value1 = (Comparable) wordMap.get(key1);
		Comparable value2 = (Comparable) wordMap.get(key2);
		return value1.compareTo(value2)*-1;
	}
	
}

