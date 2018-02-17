package com.caculator.main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Caculator {

	// 中缀表达式字符串
	private String source;
	// 保存处理后的中缀表达式的集合
	private List<String> polan = new ArrayList<String>();
	// 保存临时运算符的栈
	private Stack<String> s1 = new Stack<String>();
	// 保存后缀表达式的栈
	private Stack<String> s2 = new Stack<String>();
	// 运算符映射集合
	private static Map<String, Integer> map = new HashMap<String, Integer>();

	static {
		map.put("+", 0);
		map.put("-", 0);
		map.put("×", 1);
		map.put("÷", 1);
		map.put("(", -1);
		map.put(")", -1);
	}
	
	/**
	 * 预处理，匹配数字字符串
	 * "((1+2)*3-2)/2#"
	 */
	public void preProcess() {
		char[] cs = source.toCharArray();
		boolean isFaterNumber = false;
		boolean isBracket = false;
		boolean isHasE = false;
		StringBuilder sb = new StringBuilder();
		
		for(char c : cs) {
			if(c >= '0' && c <= '9' || c == '.') {
				sb.append(c);
				isFaterNumber = true;
			}else if(c == 'E') {
				sb.append(c);
				isHasE = true;
			} else {
				if(isFaterNumber) {
					if(isHasE) {
						sb.append(c);
						isHasE = false;
						continue;
					}
					polan.add(sb.toString());
					sb.delete(0, sb.length());
					isFaterNumber= false;
				}
				if(c == '(') {
					isBracket = true;
					continue;
				} 
				if(isBracket) {
					if(c == '-') {
						sb.append(c);
						continue;
					}else if( c == ')') {
						isBracket = false;
						continue;
					} else {
						polan.add("(");
						isBracket = false;
					}
				}
				polan.add(c+"");
			}
			
		}
		System.out.println("处理后的中缀表达式：" + polan);
	}
	
	/**
	 * 中缀表达式转后缀表达式
	 *
	 */
	public void process() {
		for (String s : polan) {
			if ("+".equals(s) || "-".equals(s) || "×".equals(s)
					|| "÷".equals(s)) {
				while (!s1.isEmpty() && map.get(s1.lastElement()) >= map.get(s)) {
					String c = s1.pop();
					s2.push(c);

				}
				s1.push(s);

			} else if ("(".equals(s)) {
				s1.push(s);
			} else if (")".equals(s)) {
				while (!"(".equals(s1.lastElement())) {
					String cc = s1.pop();
					s2.push(cc);
				}
				s1.pop();
			} else if ("#".equals(s)) {
				while (!s1.isEmpty()) {
					String cc = s1.pop();
					s2.push(cc);
				}
			}else {
				s2.push(s);
			}
		}
		System.out.println("后缀表达式： " + s2);
	}

	/**
	 * 反转后缀表达式栈
	 */
	public void reverse() {
		Queue<String> rev = new LinkedList<String>();

		while (s2.size() > 0) {
			rev.offer(s2.pop());
		}

		while (rev.size() > 0) {
			s2.push(rev.poll());
		}

	}

	/**
	 * 计算结果
	 */
	public String caculate(String source) {
		this.source = source;
		System.out.println("中缀表达式为：" + source);
		preProcess();
		process();
		reverse();
		Stack<BigDecimal> s3 = new Stack<BigDecimal>();
		while (!s2.isEmpty()) {
			String c = s2.pop();
			if ("+".equals(c) || "-".equals(c) || "×".equals(c) || "÷".equals(c)) {
				if(s3.size() < 2) {
					return "0";
				}
				BigDecimal n1 = s3.pop();
				BigDecimal n2 = s3.pop();
				if ("+".equals(c)) {
					n1 = n1.add(n2);
				} else if ("-".equals(c)) {
					n1 = n2.subtract(n1);
				} else if ("×".equals(c)) {
					n1 = n1.multiply(n2);
				} else if ("÷".equals(c)) {
					if(n1.doubleValue() == 0) {
						return "error";
					}
					n1 = n2.divide(n1, 10, BigDecimal.ROUND_HALF_UP);
				}
				s3.push(n1);
			} else {
				s3.push(new BigDecimal(c));
			}
			if (s2.isEmpty()) {
				BigDecimal bd = s3.pop().divide(new BigDecimal(1), 9, BigDecimal.ROUND_HALF_UP);
				s3.push(new BigDecimal(bd.toString(), new MathContext(13, RoundingMode.HALF_UP)));
				break;
			}
		}
		return s3.isEmpty() ? "error" : s3.pop().stripTrailingZeros().toString();
	}
	public static void main(String[] args) {
		String s = new Caculator().caculate("1-1"+"#");
		System.out.println(s);
	}
}