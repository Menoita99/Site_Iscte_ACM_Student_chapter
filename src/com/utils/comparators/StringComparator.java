package com.utils.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StringComparator implements Comparator<String>{

	private boolean isAscending = true;
	
    public StringComparator(boolean asc) {
		isAscending = asc;
	}
	
	@Override
	public int compare(String o1, String o2) {
		String s1 = o1.toLowerCase();
		String s2 = o2.toLowerCase();
		
		int i = 0;
		int result = 0;
		do {
			result = s1.charAt(i)-s2.charAt(i);
			i++;
		}while(result == 0 && i<s1.length() && i<s2.length());
		
		if(result == 0) result = o2.length()>o1.length() ? -1 : 1;
		
		return isAscending ? result : -result;
	}
	
	
	
	public static void main(String[] args) {
		List<String> test = new ArrayList<>(); 
		test.add("cc");
		test.add("ccc");
		test.add("ab");
		test.add("aba");
		test.add("a");
		test.add("b");
		test.add("bb");
		test.add("a");

		
		System.out.println(test);
		Collections.sort(test,new StringComparator(false));
		System.out.println(test);
	}
}
