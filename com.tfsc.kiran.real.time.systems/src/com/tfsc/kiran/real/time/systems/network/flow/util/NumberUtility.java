package com.tfsc.kiran.real.time.systems.network.flow.util;

public class NumberUtility {
	
	
	public static Integer gcd(Integer a, Integer b)
	{
	    while (b > 0)
	    {
	        Integer temp = b;
	        b = a % b; // % is remainder
	        a = temp;
	    }
	    return a;
	}

	public static Integer gcd(Integer[] input)
	{
	    Integer result = input[0];
	    for(Integer i = 1; i < input.length; i++) result = gcd(result, input[i]);
	    return result;
	}
	
	public static Integer lcm(Integer a, Integer b)
	{
	    return a * (b / gcd(a, b));
	}

	public static Integer lcm(Integer[] input)
	{
	    Integer result = input[0];
	    for(Integer i = 1; i < input.length; i++) result = lcm(result, input[i]);
	    return result;
	}

}
