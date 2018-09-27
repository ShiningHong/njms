package com.utils.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @Title:数组工具类
 * @Description:
 *
 * @author 池文杉
 * @createDate 2015-5-6
 * <p>
 * 修改历史 ：(修改人，修改时间，修改原因/内容)
 * </p>
 */
public class ArrayUtil {

	/**
	 * 
	 * 功能描述：检查一个数组是否包含一个字符窜或者数字,包含返回true 否则返回false
	 * @author 
	 * @createDate 2015-5-6
	 * @param arr   需要检查的数组,不支持int等基本类型数组，可以转换为Interge数组后再进行检查
	 * @param flag  要检查是否包含的字符或者数字
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static boolean isContain(Object[] arr, Object flag) {
		boolean isContain = false;
		String flagStr = flag + "";// 先转化为String类型
		if (arr != null&&arr.length>0) {
			for (Object object : arr) {
				String value = object + "";
				if (value != null && value.trim().equals(flagStr.trim())) {
					isContain = true;
					break;
				}
			}
		}
		return isContain;
	}
	
	/**
	 * 
	 * 功能描述： 获取arr数组中flag的第一个位置，如果不包含有flag则返回-1，包含则返回第一格flag位置
	 * @author
	 * @createDate 2015-5-6
	 * @param arr     需要检查的数组,不支持int等基本类型数组，可以转换为相应的java.lang包下相应对象数组后再进行检查
	 * @param flag    要检查是否包含的字符或者数字
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static long getFirstIndexOf(Object[] arr, Object flag) {
		long index = -1;// 默认坐标负一
		String flagStr = flag + "";
		if (arr != null&&arr.length>0) {
			int i = 0;
			for (Object object : arr) {
				String value = object + "";
				if (value.equals(flagStr)) {
					index = i;
					return index;
				}
				i++;
			}
		} 
		return index;
	}
	
	/**
	 * 将map根据值的大小进行排序
	 * @param map
	 * @param rule ture为从小到大false为从大到小
	 * @return
	 */
	public static <K, V extends Comparable<V>> Map<K, V> sortMapByValue(Map<K, V> map,final boolean rule) {
		List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<K, V>>() {

			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				Comparable<V> v1 = o1.getValue();
				V v2 = o2.getValue();
				if (v1 == null) {
					if (v2 == null) {
						return 0;
					} else {
						return rule?-1:1;
					}
				} else {
					if (v2 == null) {
						return rule?1:-1;
					} else {
						return rule?v1.compareTo(v2):v1.compareTo(v2)*-1;
					}
				}
			}
		});
		Map<K, V> result = new LinkedHashMap<K, V>();
		Iterator<Entry<K, V>> it = list.iterator();
		while (it.hasNext()) {
		   Entry<K, V> entry = it.next();
		   result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 
	 * 功能描述：往数组里添加一个新的对象
	 * @author 池文杉
	 * @createDate 2015-5-6
	 * @param arr 数组
	 * @param newObj 添加的对象
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String[] addArray(String[] arr, String newObj){
		
		if(arr==null){
			arr = new String[]{};
		}
		String[] temp = new String[arr.length + 1];
		for(int i = 0;i<arr.length;i++){
			temp[i] = arr[i];
		}
		temp[temp.length-1]=newObj;
		
		return temp;
	}
	public static List<String> convertToArrayList(String[] arr){
		
		if(arr==null){
			arr = new String[]{};
		}
		List<String> list = new ArrayList<String>(arr.length);
		for(int i = 0;i<arr.length;i++){
			list.add(arr[i]);
		}
		return list;
	}
	
	/**
	 * 
	 * 功能描述：将List<String>转换为String[]
	 * @author 池文杉
	 * @createDate 2015-5-18
	 * @param paramsList 
	 * @return String[]
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String[] convertToArr(List<String> paramsList){
		if(paramsList==null)return null;
		int size = paramsList.size();
		String[] paramsArray = new String[size];
		if(size!=0){
			for(int i=0;i<paramsList.size();++i){
				paramsArray[i] = paramsList.get(i).toString();
			}
		}
		return paramsArray;
	}
	
}
