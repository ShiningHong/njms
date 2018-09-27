package com.utils.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class AIStringUtil  {

    /**
     * 验证字符串是否为空
     * @param string
     * @return
     */
//	public static boolean isEmpty(String string) {
//		boolean result = false;
//		if (string == null || "".equals(string.trim())) {
//			result = true;
//		}
//		return result;
//	}

    /**
     * 判断字符串集是否含有空串
     * @param strings
     * @return
     */
    public static boolean hasEmpty(String... strings) {
        boolean result = (strings == null || strings.length == 0) ? true : false;
        for (final String string : strings) {
            if (string==null||"".equals(string.trim())) {
                return true;
            }
        }
        return false;
    }

	/**
	 * 验证字符串数组是否都为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String... string) {
		boolean result = (string == null || string.length == 0) ? true : false;
		if (!result) {
			for (String s : string) {
				if (s!=null&&!"".equals(s.trim())) {
					return false;
				}
			}
			result = true;
		}
		return result;
	}
	
	/**
	 * 
	 * 功能描述：验证字符串/数组是否都不为空
	 * @author Anna
	 * @createDate 2015-7-1
	 * @param string
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static boolean isNotEmpty(String... string) {
		boolean result = (string == null || string.length == 0) ? true : false;
		if (!result) {
			for (String s : string) {
				if (isEmpty(s)) {
					return false;
				}
			}
			result = true;
		}
		return result;
	}

	/**
	 * 验证字符串内容(只要有一个相同)
	 * @param string 待验证字符
	 * @param equ_str 用于验证的内容字符(可传数组[],)
	 * @return
	 */
	public static boolean isEquals(String string, String... equ_str) {

		//		if(!isEmpty(string)&&!isEmpty(equ_str)){
		//			if(string.equals(equ_str))return true;
		//		}
		//		return false;

		if (!isEmpty(string) && !isEmpty(equ_str)) {
			for (String equ : equ_str) {
				if (!isEmpty(equ)) {
					if (string.equals(equ))
						return true;
				}
			}
		}else if(isEmpty(string)&&isEmpty(equ_str)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证字符串内容(忽略大小写)
	 * @param string 待验证字符
	 * @param equ_str 用于验证的内容字符(可传数组[],)
	 * @return
	 */
	public static boolean isEqualsIgnoreCase(String string, String... equ_str) {
		if (!isEmpty(string) && !isEmpty(equ_str)) {
			for (String equ : equ_str) {
				if (!isEmpty(equ)) {
					if (string.equalsIgnoreCase(equ))
						return true;
				}
			}
		}else if(isEmpty(string)&&isEmpty(equ_str)){
			return true;
		}
		return false;
	}

	/** 
	 * JSON字符串特殊字符处理，比如：“\A1;1300” 
	 * @param s 
	 * @return String 
	 */
	public String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(!isEmpty(str)){
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str).matches();
		}
		return false;
	}
	/**
	 * 判断是否为数字（包含小数，负数）
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		if(!isEmpty(str)){
			//Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]+)?$");//正数
			Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");//允许负数
			return pattern.matcher(str).matches();
		}
		return false;
	}
	

	/**
	 * 将数字字符串转化为long型
	 * @param srcInt
	 * @return
	 */
	public static long doNullLong(String srcInt) {
		if (srcInt == null || "".equals(srcInt))
			return 0;
		return Long.parseLong(srcInt);
	}
	
	/**
	 * 
	 * 功能描述： trim()重写，取出字符串前后空格
	 * @author Anna
	 * @createDate 2015-5-5
	 * @param sql
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String reTrimByString(String value){
		String reValue;
		if(value == null || value.equals("")){
			reValue = "";
		}else{
			reValue = value.trim();
		}
		return reValue;
	}
	
	/**
	 * 
	 * 功能描述：格式化sql,去除查询前缀1=1及其跟随的and/or （如果sql的查询条件有且只有一个无论是不是1=1，则不处理）
	 * @author Anna
	 * @createDate 2015-5-5
	 * @param sql
	 * @return sql
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String formatSql(String sql){
		if(isEmpty(reTrimByString(sql)))return sql;
		
		String where = "where";
		String and = "and";
		String or = "or";
		String sql_low = sql.toLowerCase();
		int whereIndex = sql_low.indexOf(where);
		int andIndex = sql_low.indexOf(and);
		int orIndex = sql_low.indexOf(or);
		boolean format = false;
		String connectSql = "";
		int index = -1;
					
		//and在or之前，且and存在
		if(andIndex<orIndex&&andIndex>-1){
			
			//截取第一个and之前
			String sql1 = whereIndex<0||whereIndex>andIndex?sql_low.substring(0, andIndex):sql_low.substring(whereIndex+where.length(), andIndex);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < sql1.length(); i++) {
				char c = sql1.charAt(i);
				switch (c) {
				case '\n':;
				case '\t':;
				case '\0':;
				default:
					sb.append(c);
				}
			}
			
			if(!isEmpty(sb.toString())&&sb.toString().indexOf("=")>-1){
				
				String[] ss = sb.toString().split("=");
				if(ss.length>1){
					if(isNumber(reTrimByString(ss[0]))&&isNumber(reTrimByString(ss[1]))){//是否为数字
						if(doNullLong(reTrimByString(ss[0]))==doNullLong(reTrimByString(ss[1]))){//是否为相等数字
							format = true;
							connectSql = and;
							index = andIndex;
						}
					}
				}
			}
		
		//or在and之前，且or存在
		}else if(orIndex>-1){
			
			//截取第一个or之前
			String sql1 = whereIndex<0||whereIndex>orIndex?sql_low.substring(0, orIndex):sql_low.substring(whereIndex+where.length(), orIndex);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < sql1.length(); i++) {
				char c = sql1.charAt(i);
				switch (c) {
				case '\n':;
				case '\t':;
				case '\0':;
				default:
					sb.append(c);
				}
			}
			
			if(!isEmpty(sb.toString())&&sb.toString().indexOf("=")>-1){
				
				String[] ss = sb.toString().split("=");
				if(ss.length>1){
					if(isNumber(reTrimByString(ss[0]))&&isNumber(reTrimByString(ss[1]))){//是否为数字
						if(doNullLong(reTrimByString(ss[0]))==doNullLong(reTrimByString(ss[1]))){//是否为相等数字
							format = true;
							connectSql = or;
							index = orIndex;
						}
					}
				}
			}			
			
		}else{//只有一个条件或者没有
			
		}
		
		if(format&&index>-1){
			if(whereIndex>-1&&whereIndex<index){
				sql = sql.substring(0, whereIndex+where.length())+sql.substring(index+and.length());
			}else{
				sql = sql.substring(index+and.length());
			}
		}
		
		return sql;
	}
	
	/**
	 * List<String> 转成 String
	 * @param l
	 * @param splitChar 分隔符
	 * @param addYh 是否给每个字符串加上单引号
	 * @return
	 */
	public static String convertionArrToString(List l,String splitChar,boolean addYh){
		StringBuffer result = new StringBuffer();
		if(l != null&&l.size()>0){
			for(int i=0;i<l.size();i++){
				String ll = String.valueOf(l.get(i));
				if(ll!=null){
					if(addYh){
						result.append("'"+ll+"'");
					}else{
						result.append(ll);
					}
					if(i!=l.size()-1&&!isEmpty(splitChar))result.append(splitChar);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * String 转成 List<String>
	 * @param str 转换的字符串
	 * @param splitChar 分隔符
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static List<String> convertionStrToList(String str,String splitChar){
		
		List<String> l = new ArrayList<String>();
		if(isEmpty(str)||isEmpty(splitChar))return l;
		String[] ss = str.split(splitChar);
		for(String s:ss){
			l.add(s);
		}
		return l;
		
	}
	
	
	/**
	 * 判断Map里指定key是否为空
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean keyIsEmpty(Map map,String key) {
	   if(map.containsKey(key)){
		   Object obj=map.get(key);
		   //List
		   if(obj instanceof java.util.List){
			   if(((java.util.List) obj).isEmpty()){
				   return true;
			   }
		   }
		   //Map
		   if(obj instanceof java.util.Map){
			   if(((java.util.Map) obj).isEmpty()){
				   return true;
			   }
		   }
		   //其它
		   String value=map.get(key).toString();
		   if(org.apache.commons.lang3.StringUtils.isBlank(value)){
			   return true;
		   }
		   return false;
	   }else{
		   return true;
	   }
	}

	/**
	 * 将字符串加上单引号
	 * @param strs 
	 * @param splitChar
	 * @return '','',''
	 */
	public static String addDyh(String strs,String splitChar){
		
		if(!isEmpty(strs)){
			if(isEmpty(splitChar))splitChar = ",";
			String[] ss = strs.split(splitChar);
			String reStrs = "";
			for(int i=0;i<ss.length;i++){
				if(!isEmpty(ss[i])){
					reStrs += splitChar+"'"+ss[i]+"'";
				}
			}
			if(!isEmpty(reStrs))reStrs = reStrs.substring(1);
			return reStrs;
		}
		return strs;
	}
	
	/**
	 * 字符串替换，将 source 中的 oldString 全部换成 newString
	 *
	 * @param source    源字符串
	 * @param oldString 老的字符串
	 * @param newString 新的字符串
	 * @return 替换后的字符串
	 */
	public static String Replace(String source, String oldString, String newString) {
		StringBuffer output = new StringBuffer();

		int lengthOfSource = source.length();   // 源字符串长度
		int lengthOfOld = oldString.length();   // 老字符串长度

		int posStart = 0;   // 开始搜索位置
		int pos;            // 搜索到老字符串的位置

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(newString);
			posStart = pos + lengthOfOld;
		}

		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}

		return output.toString();
	}
	
	/**
	 * 替代linde中的oldString为newString
	 * <p/>
	 * line 需要做替代的字符串
	 *
	 * @param oldString the String that should be replaced by newString
	 * @param newString the String that will replace all instances of oldString
	 * @param line      String
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}
	
	/**
	 * 
	 * 功能描述：验证字符串为空则返回默认值
	 * @param string 字符串
	 * @param def_str 默认值
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String emptyDefault(String string, String def_str){
		if(AIStringUtil.isEmpty(string)){
			return def_str;
		}
		return string;
	}
	public static String emptyDefault(Object strObj, String def_str){
		String string = strObj!=null?strObj.toString():null;
		if(AIStringUtil.isEmpty(string)){
			return def_str;
		}
		return string;
	}
	

	/**
	 * 驼峰转换为下划线(小写)
	 * 
	 * @param camelCaseName
	 * @return
	 */
	public static String underscoreNameLower(String camelCaseName) {
		StringBuilder result = new StringBuilder();
		if (camelCaseName != null && camelCaseName.length() > 0) {
			result.append(camelCaseName.substring(0, 1).toLowerCase());
			for (int i = 1; i < camelCaseName.length(); i++) {
				char ch = camelCaseName.charAt(i);
				if (Character.isUpperCase(ch)) {
					result.append("_");
					result.append(Character.toLowerCase(ch));
				} else {
					result.append(ch);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * 驼峰转换为下划线(大写)
	 * 
	 * @param camelCaseName
	 * @return
	 */
	public static String underscoreNameUpper(String camelCaseName) {
		
		return underscoreNameLower(camelCaseName).toUpperCase();
	}
	
	/**
	 * 下划线转换为驼峰
	 * 
	 * @param underscoreName
	 * @return
	 */
	public static String camelCaseName(String underscoreName) {
		StringBuilder result = new StringBuilder();
		if (underscoreName != null && underscoreName.length() > 0) {
			boolean flag = false;
			for (int i = 0; i < underscoreName.length(); i++) {
				char ch = underscoreName.charAt(i);
				if ("_".charAt(0) == ch) {
					flag = true;
				} else {
					if (flag) {
						result.append(Character.toUpperCase(ch));
						flag = false;
					} else {
						result.append(Character.toLowerCase(ch));
					}
				}
			}
		}
		return result.toString();
	}

	public static boolean isEmptyList(List list) {
		 if(list==null||list.size()==0||"null".equals(list.get(0))){
			 return true;
		 }
		 return false;
	}
	
	/**
	 * 
	 * 功能描述：判断字符串是否包含指定字符串（忽略分隔符）
	 * @author
	 * @param string 字符串
	 * @param splitChar 分隔符(默认为,)
	 * @param incl_str 包含字符串
	 * @return
	 */
	public static boolean include(String string,String splitChar,String incl_str){
		if(!isEmpty(string)){
			if(isEmpty(splitChar))splitChar = ",";
			String[] ss = string.split(splitChar);
			for(int i=0;i<ss.length;i++){
				if(AIStringUtil.isEquals(ss[i], incl_str)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 功能描述：判断字符串是否包含指定字符串-忽略大小写（忽略分隔符）
	 * @author
	 * @param string 字符串
	 * @param splitChar 分隔符(默认为,)
	 * @param incl_str 包含字符串
	 * @return
	 */
	public static boolean includeIgnoreCase(String string,String splitChar,String incl_str){
		if(!isEmpty(string)){
			if(isEmpty(splitChar))splitChar = ",";
			String[] ss = string.split(splitChar);
			for(int i=0;i<ss.length;i++){
				if(AIStringUtil.isEqualsIgnoreCase(ss[i], incl_str)){
					return true;
				}
			}
		}
		return false;
	}

    public static String stringArray2Strin(String[] str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append("'").append(str[i]).append("'").append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static void main(String[] args) {
        //System.out.println(AIStringUtil.stringArray2Strin(new String[]{"12","31"}));
    	/*int a = 10;
    	int t  = 90;
    	int totalPage = 0;
    	totalPage = t/a;
    	System.out.println(totalPage);
		if(t%a>0)totalPage +=1;
		System.out.println(totalPage);*/
    	System.out.println(AIStringUtil.isNumeric("22.1"));
    }
}
