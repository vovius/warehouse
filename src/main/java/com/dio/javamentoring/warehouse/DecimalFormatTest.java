package com.dio.javamentoring.warehouse;

import java.util.Formatter;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class DecimalFormatTest {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int precision = scanner.nextInt();
		//String pattern = precision == 0 ? "#0;(#0)" : String.format("#,%s0", new StringBuilder().append(precision == 0 ? "" : ",").append(""));
		//String pattern = precision == 0 ? "#0;(#0)" : new Formatter().format("#,%1$s0;(#,%1$s0)",StringUtils.repeat("#", precision)).toString();
		String pattern = precision == 0 ? "#0;(#0)" : new StringBuilder("#,##0.").append(StringUtils.repeat("0", precision)).append(";(#,##0,").append(StringUtils.repeat("0", precision)).append(")").toString();
		System.out.println(pattern);
	}
}
