package com.github.xzzpig.mcconsole;

import java.util.*;
import android.text.*;

public class Voids
{
	public static byte[] connectbytes(byte[] b1,byte[] b2)
	{
		byte[] b = (new String(b1) + new String(b2)).getBytes();
		return b;
	}
	public static byte[] connectbytes(byte[] b1,byte[] b2,byte[] b3)
	{
		byte[] b = (new String(b1) + new String(b2) + new String(b3)).getBytes();
		return b;
	}
	
	public static Spanned matchColor(String arg){
		List<Character> s = new ArrayList<Character>();
		boolean skip = false;
		for(char c:arg.toCharArray()){
			if(c == '§'){
				skip = true;
				continue;
			}
			if(skip){
				skip = false;
				for(char cc:("</font><font color='"+BukkitColor.get(c).get()+"'>").toCharArray())
					s.add(cc);
				continue;
			}
			s.add(c);
		}
		char[] cs = new char[s.size()];
		for(int i=0;i<s.size();i++)
			cs[i] = s.get(i);
		return Html.fromHtml(new String(cs).replaceFirst("</font>","")+"</font>");
	}
	public static String match(String arg){
		List<Character> s = new ArrayList<Character>();
		boolean skip = false;
		for(char c:arg.toCharArray()){
			if(c == '§'){
				skip = true;
				continue;
			}
			if(skip){
				skip = false;
				for(char cc:("</font><font color='"+BukkitColor.get(c).get()+"'>").toCharArray())
					s.add(cc);
				continue;
			}
			s.add(c);
		}
		char[] cs = new char[s.size()];
		for(int i=0;i<s.size();i++)
			cs[i] = s.get(i);
		return (new String(cs).replaceFirst("</font>","")+"</font>");
		
	}
}
