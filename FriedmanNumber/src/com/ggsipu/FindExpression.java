package com.ggsipu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptException;

public class FindExpression {
	
	static String symb[] = {"+","-","*","/","^"};
	
	public static void main(String[] args) throws ScriptException {
		HashMap<String, Object> ls = findExpression("112");
//		for (Entry<String, Object> entry : ls.entrySet()) {
//	        String k = entry.getKey();
//	        Object v = entry.getValue();
//	        System.out.println("Key: " + k + ", Value: " + v);
//	    }
//		for(int x=0; x<symb.length; x++) {
//			System.out.println(symb[x]);
//		}
		
//		System.out.println(findObjecType(abc));
		
	}
	
	public static String findObjecType(Object o)
	{
	    if(o instanceof Integer)
	    	return "Integer";
	    else if(o instanceof Double)
	    	return "Double";
	    return "None";
	}
	
	public static HashMap<String, Object> findExpression(String str) throws ScriptException {
		HashMap<String, Object> ls = new HashMap<String, Object>();
		ls.put(str, Integer.parseInt(str));
		for(int i =1; i < str.length(); i++) {
			HashMap<String, Object> e11 = findExpression(str.substring(0,i));
			for (Map.Entry<String, Object> e1 : e11.entrySet()) {
				HashMap<String, Object> e22 = findExpression(str.substring(i));
				for (Map.Entry<String, Object> e2 : e22.entrySet()) {
					for(int x=0; x<symb.length; x++) {
//						System.out.println(e1.getKey()+"---"+s+"---"+e2.getKey());
						String d = "("+e1.getKey()+symb[x]+e2.getKey()+")";
						if(FindValidExpression.isValidExpression(d)) {
							ls.put(d, FindValidExpression.eval(d));
						}
						
					}
				}
			}
		}
		return ls;
	}

}
