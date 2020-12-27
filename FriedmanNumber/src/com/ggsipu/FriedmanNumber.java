package com.ggsipu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.script.ScriptException;

public class FriedmanNumber {
	
	public static void main(String[] args) throws ScriptException {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter number to find Friedman number:");

	    String str = myObj.nextLine();
	    findFriedmanNumber(str);
	    System.out.println("Finished");
		
	}
	
	
	public static void findFriedmanNumber(String str) throws ScriptException {
		Set<String> allData = FindPermutation.getPermutation(str);
		Iterator<String> value = allData.iterator(); 
		int m = 0;
		while(value.hasNext()) {
			HashMap<String, Object> ls = FindExpression.findExpression(value.next());
			for (Entry<String, Object> entry : ls.entrySet()) {
		        
				String k = entry.getKey();
				if(FindValidExpression.isNumber(k)) {
					continue;
				}
		        Double v = Double.valueOf(entry.getValue()+"");
		        if(Double.parseDouble(str) == v) {
		        	System.out.println("Expression: " + k + ", Data: " + v);
		        	m++;
		        	break;
		        }
		    }
			if(m>0) break;
		}
		
		if(m == 0) {
			System.out.println(str+" is not a Friedman number.");
		}
	}
	
	

}
