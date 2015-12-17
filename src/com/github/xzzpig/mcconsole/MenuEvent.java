package com.github.xzzpig.mcconsole;

import android.text.*;
import android.view.*;
import android.widget.*;
import com.github.xzzpig.mcconsole.*;

public class MenuEvent
{
	public static void onOptionsItemSelected(MenuItem item){
		if(item.getTitle().toString().equalsIgnoreCase("重新连接")){
			new Thread(new Runnable(){
					@Override
					public void run(){
						try{
							RCON.connect(Vars.ip,Vars.port);
							RCON.login(Vars.password);
							MainActivity.sendToast("重连成功");
						}
						catch(Exception e){
							MainActivity.sendToast("重新连接失败("+e.getMessage()+")");
							return;
						}

					}
				}).start();
		}
		else if(item.getTitle().toString().equalsIgnoreCase("清空")){
			TextView print = (TextView)MainActivity.main.findViewById(R.id.EditText_print);
			print.setText("");
			Vars.before = "";
		}
	}
	
	public static void reBuildMenu(){
		Menu menu = Vars.menu;
		menu.clear();
		switch(Vars.page){
			case R.layout.main:
				menu.add("无");
				break;
			case R.layout.console:
				menu.add("清空");
				menu.add("重新连接");
				break;
		}
	}
}
