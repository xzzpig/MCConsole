package com.github.xzzpig.mcconsole;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.net.*;
import android.net.*;
import java.io.*;
import java.nio.charset.*;
import java.lang.reflect.*;
import java.util.*;
import android.view.View.*;
import android.content.*;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Vars.page = R.layout.main;
		SharedPreferences data = this.getSharedPreferences("DATA",MODE_PRIVATE);
		boolean autosave = data.getBoolean("autosave",false);
		System.out.println(autosave);
		if(autosave){
			((CheckBox)this.findViewById(R.id.CheckBox_spw)).setChecked(true);
			((TextView)this.findViewById(R.id.EditText_ip)).setText(data.getString("ip",""));
			((TextView)this.findViewById(R.id.EditText_psw)).setText(data.getString("password",""));
			((TextView)this.findViewById(R.id.EditText_port)).setText(data.getInt("port",0)+"");
		}
    }
	public void onBLClick(View view){
		ButtonClickEvent.login(this,view);
	}
	public void onBKClick(View view){
		ButtonClickEvent.kzt(this,view);
	}
	public void onBSClick(View view){
		ButtonClickEvent.send(this,view);
	}
	public void onCKClick(View view){
		SharedPreferences data = this.getSharedPreferences("DATA",MODE_PRIVATE);
		data.edit().putBoolean("autosave",((CheckBox)this.findViewById(R.id.CheckBox_spw)).isChecked()).apply();
	}
	@Override
	public void onBackPressed(){
		switch(Vars.page){
			case R.layout.choice :
				super.onBackPressed();
				break;
			case R.layout.console :
				setContentView(R.layout.choice);
				Vars.page = R.layout.choice;
				break;
			case R.layout.main :
				super.onBackPressed();
				break;
		}
	}
}
