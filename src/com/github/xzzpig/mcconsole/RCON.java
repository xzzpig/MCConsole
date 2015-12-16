package com.github.xzzpig.mcconsole;

import java.io.*;
import java.net.*;
import java.util.*;

public class RCON
{
	public static Socket s;
	public static boolean login;

	public static boolean connect(String ip,int port) throws Exception{
		InetAddress add;
		try{
			add = InetAddress.getByName(ip);
		}
		catch(Exception e){
			throw new Exception("无法获取IP");
		}
		try{
			s = new Socket(add,port);
		}
		catch(Exception e){
			throw new Exception("RCON服务器连接失败");
		}
		login = true;
		return true;
	}

	public static boolean login(String password) throws Exception{
		if(s==null||s.isClosed())
			throw new Exception("RCON服务器为连接");
		byte[] pre = {((Integer)(password.length()+9)).byteValue(),0,0,0,0,0,0,0,3,0,0,0};
		byte[] suf ={0};
		byte[] success = {10, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0};
		try{
			s.getOutputStream().write(Voids.connectbytes(pre,password.getBytes("utf-8"),suf));
		}
		catch(IOException e){
			throw new Exception("服务器RCON数据发送失败");
		}
		byte[] buf = new byte[1024];
		int length = 0;
		length = s.getInputStream().read(buf);
		byte[] data = new String(buf).substring(0,length).getBytes();
		if(!Arrays.equals(data,success)){
			throw new Exception("RCON密码错误");
		}
		return true;
	}

	public static String sendcommand(String command) throws Exception{
		if(s==null||s.isClosed())
			throw new Exception("RCON服务器为连接");
		if(!login)
			throw new Exception("未登录");
		try{
			s.getOutputStream().write(getCommanddata(command));
		}
		catch(IOException e){
			throw new Exception("发送命令失败");
		}
		byte[] buf = new byte[1024];
		int length = 0;
		try{
			length = s.getInputStream().read(buf);
		}
		catch(IOException e){}
		byte[] data = new String(buf).substring(0,length).getBytes();
		return new String(data).substring(1);
	}

	public static byte[] getCommanddata(String command){
		byte[] pre = {((Integer)(command.length()+9)).byteValue(),0,0,0,0,0,0,0,2,0,0,0};
		byte[] suf ={0};
		try{
			return Voids. connectbytes(pre,command.getBytes("utf-8"),suf);
		}
		catch(UnsupportedEncodingException e){}
		return Voids. connectbytes(pre,new byte[]{0},suf);
	}
	
	public static String delColor(String arg){
		List<Character> s = new ArrayList<Character>();
		boolean skip = false;
		for(char c:arg.toCharArray()){
			if(c == '§'){
				skip = true;
				continue;
			}
			if(skip){
				skip = false;
				continue;
			}
			s.add(c);
		}
		char[] cs = new char[s.size()];
		for(int i=0;i<s.size();i++)
			cs[i] = s.get(i);
		return new String(cs);
	}
}
