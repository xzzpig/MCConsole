package com.github.xzzpig.mcconsole;
import android.graphics.*;
import android.text.style.*;
/*
 &4=大红 &c=浅红 &6=土黄 &e=金黄 &2=绿 &a=浅绿 &b=蓝绿
 &3=天蓝 &1=深蓝 &9=蓝紫 &d=粉红 &5=品红 &f=白 &7=灰 &8=深灰 &0=黑
 <font color='red'>红色字</font>的格式";
 */
public enum BukkitColor
{
	BLACK('0',"black"),
    DARK_BLUE('1',"blue"),
	GREEN('2',"green"),
	BLUE('3',"blue"),
    RED('4',"red"),
	PURPLE('5',"blue"),
    GOLD('6',"#FFD700"),
    GRAY('7',"gray"),
	DARK_GRAY('8',"gray"),
	LIGHT_GREEN('a',"green"),
    DARK_GREEN('b',"green"),
    Dark_RED('c',"red"),
	PINK('d',"pink"),
    YELLOW('e',"yellow"),
    RESET('f',"black");

	char id;
	String color;

	BukkitColor(char id,String color){
		this.id = id;
		this.color = color;
	}

	public static BukkitColor get(char c){
		for(BukkitColor bc:values()){
			if(bc.id == c)
				return bc;
		}
		return RESET;
	}

	public String get(){
		return color;
	}
}
