package com.puhua.crm.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具
 * Created by Nereo on 2015/4/8.
 */
public class TimeUtils {

    public static String timeFormat(long timeMillis, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time){
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path){
        File file = new File(path);
        if(file.exists()){
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }
    
    /**
	 *  获取系统当前时间
	 * @return
	 */
	public static String getdate(){
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd");       
		String    date    =    sDateFormat.format(new    java.util.Date()); 
			return date;
		
	}
	
	/**
	 * 获取系统当前时间的前30天
	 * @return
	 */
	public static String getbefordate(){
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd");       
		String    date    =    sDateFormat.format(new    java.util.Date()); 
		String mouth=date.substring(5,7);
		String year=date.substring(0,4);
		String mdate=date.substring(8,date.length());
		
		int m=Integer.parseInt(mouth);
		if (m>1) {
			m=m-1;
			if (m==2) {
				int d=Integer.parseInt(mdate);
				if (d>28) {
					d=28;
					String show=year+"-"+"0"+m+"-"+d;
					return show;
				}else{
					String show=year+"-"+"0"+m+"-"+mdate;
					return show;
				}
				
			}else if (m==4||m==6||m==9) {
				
				int d=Integer.parseInt(mdate);
				if (d>30) {
					d=30;
					String show=year+"-"+"0"+m+"-"+d;
					return show;
				}else{
					String show=year+"-"+"0"+m+"-"+mdate;
					return show;
				}
				
			}
			else if (m==11) {
				int d=Integer.parseInt(mdate);
				if (d>30) {
					d=30;
					String show=year+"-"+m+"-"+d;
					return show;
				}else{
					String show=year+"-"+m+"-"+mdate;
					return show;
				}
			}
			else if (m<10) {
				String show=year+"-"+"0"+m+"-"+mdate;
				return show;
			}else{
				String show=year+"-"+m+"-"+mdate;
				return show;
			}
		}else{
			int y=Integer.parseInt(year);
			y=y-1;
			String show=y+"-"+12+"-"+mdate;
			return show;
		}
		
	}
}
