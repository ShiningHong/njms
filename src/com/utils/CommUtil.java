package com.utils;

import java.io.IOException;


import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommUtil {
	private static Logger log =  (Logger)LogManager.getLogger(CommUtil.class);
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat ldf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final String NUMBER_FORMAT = "############0.00";
	private static final NumberFormat nf = new DecimalFormat(NUMBER_FORMAT);

	private static Map<String, Object> dataSrcMap = new HashMap<String, Object>();

	public static void addDataSource(String key, Object ds) {
		dataSrcMap.put(key, ds);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		Map<String, String> map = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String s[] = request.getParameterValues(name);
			try {

				if (s != null) {
					if (s.length > 1){
						String v = s.toString().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
						map.put(name, reductionSpecialChar(v));
					}else{
						String v = s[0].replaceAll("%(?![0-9a-fA-F]{2})", "%25");
						map.put(name, reductionSpecialChar(URLDecoder.decode(v, "UTF-8")));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static String reductionSpecialChar(String v){
		if(isNotEmpty(v)){
			v = v.replaceAll("\\[plus\\]", "+");
			v = v.replaceAll("\\[percent\\]", "%");
		}
		return v;
	}

	public static Object getRawProperty(Object bean, String propertyName)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return PropertyUtils.getProperty(bean, propertyName);
	}

	public static String converToString(Object value) {
		if (value == null)
			return "";
		if (value instanceof String)
			return (String) value;
		if (value instanceof Date)
			return formatDate((Date) value);
		if (value instanceof Double)
			return formatNumber(value);
		if (value instanceof Clob) {
			Clob v = (Clob) value;
			try {
				return v.getSubString(1L, (int) v.length());
			} catch (SQLException e) {
				log.debug(
						"\u8BFB\u53D6SQL\u7684Clob\u5B57\u6BB5\u65F6\u53D1\u751F\u9519\u8BEF",
						e);
				throw new RuntimeException(
						"\u8BFB\u53D6SQL\u7684Clob\u5B57\u6BB5\u65F6\u53D1\u751F\u9519\u8BEF",
						e);
			}
		} else {
			String result = String.valueOf(value);
			return result;
		}
	}

	public static String formatDate(Date date) {
		if (date == null)
			return null;
		String d = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		try {
			if (c.get(10) > 0 || c.get(12) > 0 || c.get(13) > 0)
				d = ldf.format(date);
			else
				d = sdf.format(date);
		} catch (Exception ex) {
			throw new RuntimeException(
					(new StringBuilder(
							"\u65e5\u671f\u65f6\u95f4\u683c\u5f0f\u5316\u51fa\u9519 : "))
							.append(date).toString());
		}
		return d;
	}

	public static String formatDateStyle(String date, String oldStyle,
			String newStyle) {
		SimpleDateFormat sdf = new SimpleDateFormat(oldStyle, Locale.CHINESE);
		SimpleDateFormat sdf2 = new SimpleDateFormat(newStyle, Locale.CHINESE);
		String newDate = "";
		try {
			Date d1 = sdf.parse(date);
			newDate = sdf2.format(d1);
		} catch (Exception e) {
		}
		return newDate;
	}

	public static String convertDate(String dateStr, String theFormat,
			int feildnum, int thenum) {
		SimpleDateFormat formatter = null;
		Calendar cldr = null;
		Date date_pre = null;
		Date date1 = null;
		try {
			formatter = new SimpleDateFormat(theFormat, Locale.CHINESE);
			cldr = new GregorianCalendar();
			date1 = formatter.parse(dateStr);
			cldr.setTime(date1);
			cldr.add(feildnum, thenum);
			date_pre = cldr.getTime();
		} catch (Exception e) {

		}
		if (date_pre == null)
			return formatter.format(date1);
		else
			return formatter.format(date_pre);
	}

	public static String[] betweenDates(String sdate, String edate) {
		String[] ss = new String[1];
		if (isNotEmpty(sdate) && isNotEmpty(edate)) {
			if (Double.parseDouble(sdate) > Double.parseDouble(edate)) {
				return null;
			}
			List<String> list = new ArrayList<String>();
			String currMonth = sdate;
			list.add(currMonth);
			if (sdate.length() == 6 && edate.length() == sdate.length()) {
				while (!currMonth.equals(edate)) {
					currMonth = convertDate(currMonth, "yyyyMM",
							GregorianCalendar.MONTH, 1);
					list.add(currMonth);
				}
			} else if (sdate.length() == 8 && edate.length() == sdate.length()) {
				while (!currMonth.equals(edate)) {
					currMonth = convertDate(currMonth, "yyyyMMdd",
							GregorianCalendar.DATE, 1);
					list.add(currMonth);
				}
			}
			return list.toArray(ss);
		}
		return null;
	}

	public static String formatNumber(Object number) {
		if (number != null || (number instanceof Number))
			return nf.format(number);
		else
			return "";
	}

	public static String fmtMicrometer(String text) {
		if (isEmpty(text) || !isNumberic(text)) {
			return text;
		}
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

	public static String join(Object array[], String separator) {
		return StringUtils.join(array, separator != null ? separator : ",");
	}

	/**
	 * 
	 * @desc check the input string is number
	 * 
	 * @anthor 池文杉
	 * @param str
	 * @return
	 */
	public static boolean isNumberic(String str) {
		// if(StringUtils.isEmpty(str)){
		// return false;
		// }
		// Pattern p = Pattern.compile("\\-?[0-9]+(\\.[0-9]+)?");
		// return p.matcher(str).matches();
		boolean flag = false;
		if (StringUtils.isNotEmpty(str)) {
			try {
				Double.parseDouble(str);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 
	 * @desc check the input string is int number
	 * 
	 * @anthor 池文杉
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile("\\-?[0-9]+");
		return p.matcher(str).matches();
	}

	/**
	 * 
	 * @desc check the input string is float number
	 * 
	 * @anthor池文杉
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		try{
		   Float.parseFloat(str);
		   return true;
	    }catch (Exception e) {
		   return false;
		}
	}

	public static boolean isPattern(String str) {
		return (str.indexOf('*') != -1 || str.indexOf('?') != -1);
	}

	public static String determineRootDir(String location) {
		int patternStart = location.length();
		int asteriskIndex = location.indexOf('*');
		int questionMarkIndex = location.indexOf('?');
		if (asteriskIndex != -1 || questionMarkIndex != -1) {
			patternStart = (asteriskIndex > questionMarkIndex ? asteriskIndex
					: questionMarkIndex);
		}
		int rootDirEnd = location.lastIndexOf('/', patternStart);
		return (rootDirEnd != -1 ? location.substring(0, rootDirEnd) : "");
	}

	/**
	 * @desc generate a class instance by classpath
	 * @param classPath
	 * @param c
	 * @return T
	 * @auth 池文杉
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getClass(String classPath, Class<T> c) {
		T obj = null;
		try {
			obj = (T) Class.forName(classPath).newInstance();
		} catch (ClassNotFoundException ce) {
			System.out.println("The class has not been found:" + classPath);
			log.error("The class has not been found:" + classPath);
		} catch (IllegalAccessException ie) {
			System.out.println("The class not alowed accessed:" + classPath);
			log.error("The class not alowed accessed:" + classPath);
		} catch (InstantiationException ie2) {
			System.out.println("The class instantiation failed:" + classPath);
			log.error("The class instantiation failed:" + classPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 
	 * @desc convert map object to json object
	 * @param map
	 * @return JSONObject
	 * @auth 池文杉
	 * @version 1.0
	 */
	/*public static JSONObject map2Json(Map<String, Object> map) {
		if (map != null && map.size() > 0) {
			JSONObject obj = new JSONObject();
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				obj.put(key, map.get(key));
			}
			return obj;
		}
		return null;
	}*/

	/**
	 * 
	 * @desc convert xml string to W3C DOM object
	 * @param xmlString
	 * @return
	 * @throws Exception
	 *             org.w3c.dom.Document
	 * @auth 池文杉
	 * @version 1.0
	 */
	public static org.w3c.dom.Document convertStringToW3c(String xmlString)
			throws Exception {
		StringReader reader = new StringReader(xmlString);
		InputSource source = new InputSource(reader);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		return (documentBuilder.parse(source));
	}

	/**
	 * 
	 * @desc convert xml string to DOM4J object
	 * @param xml
	 * @return
	 * @throws Exception
	 *             org.dom4j.Document
	 * @auth 池文杉
	 * @version 1.0
	 */
	public static Document convertXmlToDom4j(String xml) throws Exception {
		StringReader sr = new StringReader(xml);
		SAXReader saxr = new SAXReader();
		Document doc = saxr.read(sr);
		return doc;
	}

	/**
	 * 
	 * @desc check the input object is null or empty
	 * @param obj
	 * @return boolean
	 * @auth 池文杉
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return obj == null || obj.toString().length() == 0;
		} else if (obj instanceof Collection) {
			return obj == null || ((Collection) obj).isEmpty();
		} else if (obj instanceof Map) {
			return obj == null || ((Map) obj).isEmpty();
		} else {
			return obj == null;
		}
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> T map2Bean(Map<String, Object> map, Class<T> bean) {
		if (!isEmpty(map)) {
			T o = null;
			try {
				o = bean.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			Field[] fs = bean.getDeclaredFields();
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				String key2 = null;
				if (key.contains("_")) {
					key2 = key.toLowerCase().replaceAll("\\_", "");
				} else {
					key2 = key.toLowerCase();
				}
				Object value = map.get(key);
				for (Field f : fs) {
					if (f.getName().toLowerCase().equals(key2)) {
						try {
							Class sTypes1[] = { f.getType() };
							Method m = bean.getMethod(
									new StringBuffer("set")
											.append(String.valueOf(
													f.getName().charAt(0))
													.toUpperCase())
											.append(f.getName().substring(1))
											.toString(), sTypes1);
							if (m != null)
								m.invoke(o, new Object[] { value });
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
			return o;
		}
		return null;
	}

	public static String getUncode(String str) {
		if (str == null)
			return "";
		String hs = "";
		try {
			byte b[] = str.getBytes("UTF-16");
			for (int n = 0; n < b.length; n++) {
				str = (java.lang.Integer.toHexString(b[n] & 0XFF));
				if (str.length() == 1)
					hs = hs + "0" + str;
				else
					hs = hs + str;
				if (n < b.length - 1)
					hs = hs + "";
			}
			str = hs.toUpperCase().substring(4);
			char[] chs = str.toCharArray();
			str = "";
			for (int i = 0; i < chs.length; i = i + 4) {
				str += "\\u" + chs[i] + chs[i + 1] + chs[i + 2] + chs[i + 3];
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return str;
	}

	public static String convertJqSpecialChar(String srcStr, boolean ctype) {
		String fix = "jc";
		char[] chs = { '*', '*', '(', ')', '$', '&', '.', '/', '\\', '|', '<',
				'>', '+', '[', ']', '{', '}', ',', '~', '`', '@', '%', '^',
				'?', '#', '!', ':', ' ', ';' };
		Map<String, String> charMap = new HashMap<String, String>();
		for (char c : chs) {
			charMap.put(String.valueOf(c), fix + String.valueOf((int) c) + fix);
		}

		if (isNotEmpty(srcStr)) {
			if (ctype) {
				for (Iterator<String> it = charMap.keySet().iterator(); it
						.hasNext();) {
					String k = it.next();
					srcStr = srcStr.replaceAll("\\" + k, charMap.get(k));
				}
			} else {
				for (Iterator<String> it = charMap.keySet().iterator(); it
						.hasNext();) {
					String k = it.next();
					srcStr = srcStr.replaceAll(charMap.get(k),
							"\\".equals(k) ? "\\\\" : "\\" + k);
				}
			}
		}
		return srcStr;
	}

	public static String getString(Object obj, int precision, String defaultStr) {
		if (isNotEmpty(obj)) {
			StringBuilder sb = new StringBuilder("###########0");
			sb.append(precision > 0 ? "." : "");
			for (int i = 0; i < precision; i++) {
				sb.append("#");
			}
			DecimalFormat df = new DecimalFormat(sb.toString());
			if (obj instanceof String) {
				return obj.toString();
			} else if (obj instanceof BigDecimal) {
				return df.format(((BigDecimal) obj).doubleValue());
			} else if (obj instanceof Double) {
				return df.format((Double) obj);
			} else if (obj instanceof Clob) {
				Clob clob = (Clob) obj;
				try {
					return clob.getSubString(1l, (int) clob.length());
				} catch (Exception e) {
					e.printStackTrace();
					return obj.toString();
				}
			} else {
				return obj.toString();
			}
		} else {
			return defaultStr;
		}
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}
		return str1.equalsIgnoreCase(str2);
	}

	public static boolean endsWithIgnoreCase(String str, String endStr) {
		if (isEmpty(str) || isEmpty(endStr)) {
			return false;
		} else {
			return str.toLowerCase().endsWith(endStr.toLowerCase());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] reverse(T[] objs){
		T[] t = null;
		if(isNotEmpty(objs)){
			t = (T[])Array.newInstance(objs[0].getClass(), objs.length);
			List<T> list = new ArrayList<T>();
			for(int i = objs.length - 1; i >= 0 ; i --){
				list.add(objs[i]);
			}
			list.toArray(t);
		}
		return isEmpty(t) ? objs : t;
	}
	
	public static String geneSubSqlByTimest(String timest, int feild, int num, String dbType){
		dbType= "oracle";
		String theFormat = timest.length() > 6 ? "yyyyMMdd" : "yyyyMM";
		String str = convertDate(timest, theFormat, feild, num);
		String[] array = null;
		if(num > 0){
			array = betweenDates(timest, str);
		}else{
			array = betweenDates(str, timest);
		}
		
		StringBuilder sb = new StringBuilder();
		for(String s : array){
			sb.append("SELECT '").append(s).append("' TIMEST ").append("oracle".equalsIgnoreCase(dbType) ? " FROM DUAL" : "");
			sb.append(" UNION ");
		}
		return sb.substring(0,sb.length()- 7);
	}
	
	public static String formatXml(String str){
        StringWriter out = new StringWriter();
        SAXReader reader = new SAXReader();
        StringReader in = new StringReader(str);
        Document doc;
        try {
            doc = reader.read(in);
            OutputFormat formater = OutputFormat.createPrettyPrint();
            formater.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(out, formater);
            writer.setEscapeText(false);
            writer.write(doc);
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
	
	public static String formatPercent(Object obj){
		if(isEmpty(obj))
			return "";
		String s = null;
		try {
			if(obj instanceof BigDecimal){
				s = String.valueOf(((BigDecimal) obj).doubleValue());
			}else{
				s = obj.toString();
			}
			
			if(isEmpty(s))
				return "";
			
			boolean isp = false;
			if(s.endsWith("%")){
				isp = true;
				s = s.substring(0,s.length()-1);
			}
			if(isEmpty(s))
				return "";
			if("--".equals(s)){
				return s;
			}
			double d = Double.parseDouble(s);
			if(d > 0d){
				return new StringBuffer("<span style='color:red;'>").append(s).append(isp?"%":"").append("</span>").toString();
			}else if(d < 0d){
				return new StringBuffer("<span style='color:green;'>").append(s).append(isp?"%":"").append("</span>").toString();
			}else{
				return new StringBuffer(s).append(isp?"%":"").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	
	public static boolean inArray(String s, String[] array){
		if(array != null && array.length > 0 && isNotEmpty(s)){
			for(int i = 0; i < array.length; i ++){
				if(s.equals(array[i])){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 生成一个6-12位长度的随机密码串（包含字母、数字、字符）
	 * @return
	 */
	public static String generateRandomPassword(){
		StringBuffer password = new StringBuffer();
		char[] chs = {
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'!','#','$','%','&','.',',','-','=','*','@','|','_','~',':','?',';','^',
			'0','1','2','3','4','5','6','7','8','9'
		};
		for (int i = 0; i <= (int) (Math.random() * 7) + 6; i++) {
			Random rd = new Random();
			int ran = rd.nextInt(chs.length);
			char c = chs[ran];
			password.append(c);
		}
		return password.toString();
	}
	public static boolean isEmail(String str) { 
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	public static void main(String[] args) {
//		System.out.println(fmtMicrometer("999999999999.43546464"));
//		System.out.println(betweenDates("201000", "201501")[1]);
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><?mso-application progid=\"Excel.Sheet\"?><Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\"> <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">  <Author>gdbi</Author>  <LastAuthor>gdbi</LastAuthor>  <Created>Tue May 12 2015 14:37:16 GMT+0800 (中国标准时间)</Created>  <Version>15.00</Version> </DocumentProperties> <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">  <WindowHeight>7770</WindowHeight>  <WindowWidth>20490</WindowWidth>  <WindowTopX>0</WindowTopX>  <WindowTopY>0</WindowTopY>  <ProtectStructure>False</ProtectStructure>  <ProtectWindows>False</ProtectWindows> </ExcelWorkbook> <Styles>  <Style ss:ID=\"Default\" ss:Name=\"Normal\">   <Alignment ss:Vertical=\"Center\"/>   <Borders/>   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"11\" ss:Color=\"#000000\"/>   <Interior/>   <NumberFormat/>   <Protection/>  </Style>  <Style ss:ID=\"headercell\">   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>   <Borders>    <Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>    <Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>    <Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>    <Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>   </Borders>   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"11\" ss:Color=\"#000000\"    ss:Bold=\"1\"/>  </Style>  <Style ss:ID=\"datacell\">  <Alignment ss:Horizontal=\"Right\" ss:Vertical=\"Center\"/>   <Borders>    <Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>    <Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>    <Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>    <Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>   </Borders>  </Style> </Styles><Worksheet ss:Name=\"用户收入结构分析?-按渠道分析(20150510)\"><Table ss:StyleID=\"datacell\" x:FullRows=\"1\" x:FullColumns=\"1\" ss:ExpandedColumnCount=\"16\" ss:ExpandedRowCount=\"7\"><Row><Cell ss:StyleID=\"headercell\"ss:MergeDown=\"1\"><Data ss:Type=\"String\">渠道</Data></Cell><Cell ss:StyleID=\"headercell\"ss:MergeDown=\"1\"><Data ss:Type=\"String\">当月累计收入</Data></Cell><Cell ss:StyleID=\"headercell\"ss:MergeDown=\"1\"><Data ss:Type=\"String\">语音月租</Data></Cell><Cell ss:StyleID=\"headercell\"ss:MergeAcross=\"4\"><Data ss:Type=\"String\">当月累计语音收入</Data></Cell><Cell ss:StyleID=\"headercell\"ss:MergeAcross=\"3\"><Data ss:Type=\"String\">当月累计语音收入</Data></Cell></Row><Row><Cell ss:StyleID=\"headercell\"ss:Index=\"4\"><Data ss:Type=\"String\">合 计</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">本地通话收入</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">省内漫游</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">省际漫游</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">国际漫游</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">合 计</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">增值月租</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">流量费</Data></Cell><Cell ss:StyleID=\"headercell\"><Data ss:Type=\"String\">其它增值</Data></Cell></Row><Row><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">社会渠道</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">740251653.29</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">416751359.97</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">94469524.88</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">71207637.27</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">13680052.72</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">8520086.91</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">916594.5</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">220387407.03</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">162802872.77</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">26461373.51</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">29921535.08</Data></Cell></Row><Row><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">集团渠道</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">193022255.94</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">153121804.41</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">2990987.19</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">1523069.76</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">662397.06</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">522003.55</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">248652.74</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">35566830.19</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">19421442.87</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">6961483.97</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">9086710.37</Data></Cell></Row><Row><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">互联网渠道</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">54031146.01</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">48649811.63</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">844364.83</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">461122.61</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">160581.15</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">145223.81</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">57148.32</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">3586801.68</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">1178800.6</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">1539676.2</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">771645.43</Data></Cell></Row><Row><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">营业渠道</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">195057452.05</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">167045127.21</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">5550252.42</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">3777520.27</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">690002.86</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">582874.49</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">363647.41</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">18983232.64</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">9142781.55</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">6204551.35</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">3361476.36</Data></Cell></Row><Row><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">合计</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">1182362507.29</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">785568103.22</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">103855129.32</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">76969349.91</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">15193033.79</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">9770188.76</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">1586042.97</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">278524271.54</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">192545897.79</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">41167085.03</Data></Cell><Cell ss:StyleID=\"datacell\"><Data ss:Type=\"String\">43141367.24</Data></Cell></Row></Table><WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\"><PageSetup> <Header x:Margin=\"0.3\"/> <Footer x:Margin=\"0.3\"/> <PageMargins x:Bottom=\"0.75\" x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\"/></PageSetup><Print> <ValidPrinterInfo/> <PaperSizeIndex>9</PaperSizeIndex> <HorizontalResolution>600</HorizontalResolution> <VerticalResolution>600</VerticalResolution></Print><Panes> <Pane>  <Number>3</Number>  <ActiveRow>12</ActiveRow>  <ActiveCol>4</ActiveCol> </Pane></Panes><ProtectObjects>False</ProtectObjects><ProtectScenarios>False</ProtectScenarios></WorksheetOptions></Worksheet></Workbook>";
		System.out.println(formatXml(s));
	}
}
