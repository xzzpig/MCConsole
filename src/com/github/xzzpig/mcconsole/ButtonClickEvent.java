package com.github.xzzpig.mcconsole;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.*;
import android.app.*;
import android.location.*;
import android.text.*;

public class ButtonClickEvent
{
	public static void login(Activity a,View view){
		Vars.ip = ((TextView)a.findViewById(R.id.EditText_ip)).getText().toString();
		Vars.port = Integer.valueOf("0"+((TextView)a.findViewById(R.id.EditText_port)).getText().toString());
		Vars.password = ((TextView)a.findViewById(R.id.EditText_psw)).getText().toString();
		if(((CheckBox)a.findViewById(R.id.CheckBox_spw)).isChecked()){
			SharedPreferences data = a.getSharedPreferences("DATA",a.MODE_PRIVATE);
			data.edit().putString("ip",Vars.ip).apply();
			data.edit().putInt("port",Vars.port).apply();
			data.edit().putString("password",Vars.password).apply();
		}
		final Result result = new Result();
		final Thread log = new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						RCON.connect(Vars.ip,Vars.port);
					}
					catch(Exception e){
						System.out.println("Error");
						result.finish(e.getMessage());
						return;
					}
					try{
						RCON.login(Vars.password);
					}
					catch(Exception e){
						result.finish(e.getMessage());
						return;
					}
					result.finish(true);
				}
			});
		log.start();
		new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						Thread.sleep(2000);
					}
					catch(InterruptedException e){}
					if(!result.finish){
						try{log.stop();}
						catch(Exception e){}
						result.finish("RCON服务器连接失败");
					}
				}
			}).start();
		while(!result.finish){}
		if(result.result){
			Toast.makeText(a,"RCON连接并登录成功",Toast.LENGTH_SHORT).show();
			a.setContentView(R.layout.choice);
			Vars.page = R.layout.choice;}
		else
			Toast.makeText(a,result.errorMessage,Toast.LENGTH_SHORT).show();
	}
	public static void kzt(Activity a,View view){
		a.setContentView(R.layout.console);
		Vars.page = R.layout.console;
	}
	public static void send(Activity a,View view){
		final String command = ((TextView)a.findViewById(R.id.EditText_Command)).getText().toString();
		((TextView)a.findViewById(R.id.EditText_Command)).setText("");
		final Result result = new Result();
		new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						//RCON.sendcommand("");
						result.message = RCON.sendcommand(command);
						result.finish(true);
					}
					catch(Exception e){
						result.finish(e.getMessage());
						return;
					}
				}
			}).start();
		while(!result.finish){}
		if(result.result){
			TextView print = (TextView)a.findViewById(R.id.EditText_print);
			String before = Vars.before;
			String message = Voids.match(result.message)+">/"+command+"\n"+before;
			Vars.before = message;
			System.out.println(result.message);
			print.setText(Html.fromHtml(message.replaceAll("\n","<br>")));
			print.computeScroll();
		}
		else
			Toast.makeText(a,result.errorMessage,Toast.LENGTH_SHORT).show();
	}
}
class Result
{
	boolean finish;
	boolean result;
	String message,errorMessage;

	void finish(boolean result){
		this.result = result;
		this.finish = true;
	}
	void finish(String errormessage){
		this.result = false;
		this.errorMessage = errormessage;
		this.finish = true;
	}
}
