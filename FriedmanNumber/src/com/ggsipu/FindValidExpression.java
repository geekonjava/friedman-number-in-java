package com.ggsipu;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class FindValidExpression {
	
	public static void main(String[] args) throws ScriptException {
		String str = "(Math.pow(11,2))";
//		System.out.println(isValidExpression(str));
		System.out.println(getExpressionValue(str));
		 	
	}
	
	
	public static double eval(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        double parse() {
	            nextChar();
	            double x = parseExpression();
	            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor

	        double parseExpression() {
	            double x = parseTerm();
	            for (;;) {
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('-')) x -= parseTerm(); // subtraction
	                else return x;
	            }
	        }

	        double parseTerm() {
	            double x = parseFactor();
	            for (;;) {
	                if      (eat('*')) x *= parseFactor(); // multiplication
	                else if (eat('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }

	        double parseFactor() {
	            if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus

	            double x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            } else if (ch >= 'a' && ch <= 'z') { // functions
	                while (ch >= 'a' && ch <= 'z') nextChar();
	                String func = str.substring(startPos, this.pos);
	                x = parseFactor();
	                if (func.equals("sqrt")) x = Math.sqrt(x);
	                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
	                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
	                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
	                else throw new RuntimeException("Unknown function: " + func);
	            } else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

	            return x;
	        }
	    }.parse();
	}
	
	
	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	
	public static Object getExpressionValue(String str) throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
//	    System.out.println();
	    return engine.eval(str);
	}
	public static boolean isAnOperator(char c) {
	    switch (c) {
	        case '*':
	        case '/':
	        case '+':
	        case '-':
	        case '^':
	            return true;
	        default:
	            return false;
	    }
	}
	public static boolean isANumber(char c){
	    return ((int)c) >= 48 && ((int)c) <= 57;
	}

	public static boolean isValidExpression(String expression) {
	    // TEST 1
	    if (isAnOperator(expression.charAt(0)) || isAnOperator(expression.charAt(expression.length() - 1))) {
	        return false;
	    }

	    int openParenthCount = 0;
	    boolean lastWasOp = false;
	    boolean lastWasOpen = false;

	    for (char c : expression.toCharArray()) {
	        if(c == ' ') continue;
	        if (c == '(') {
	            openParenthCount++;
	            lastWasOpen = true;
	            continue;
	        } else if (c == ')') {
	            if (openParenthCount <= 0 || lastWasOp) {
	                return false;
	            }
	            openParenthCount--;
	        }else if (isAnOperator(c)){
	            if (lastWasOp || lastWasOpen) return false;
	            lastWasOp = true;
	            continue;
	        }else if(!isANumber(c)){
	            return false;
	        }
	        lastWasOp = false;
	        lastWasOpen = false;
	    }
	    if(openParenthCount != 0) return false;
	    if(lastWasOp || lastWasOpen) return false;
	    return true;
	}

}
