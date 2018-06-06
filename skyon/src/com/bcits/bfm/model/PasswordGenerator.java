package com.bcits.bfm.model;

import java.util.Random;

public class PasswordGenerator 
{
	Random rr=new Random();
	public String  getKeys()
	{		
		String str="";
		str=String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10));
		return str;
	}
	public static void main(String args[])
	{
		PasswordGenerator Generator = new PasswordGenerator();
	    System.out.println(Generator.getKeys());
	}
}
