package com.gitee.linzl.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

public class GPSUtil {

	/**
	 * 
	 * 字段0：$GPGLL，语句ID，表明该语句为Geographic Position（GLL）地理定位信息
	 * 
	 * 字段1：纬度ddmm.mmmm，度分格式（前导位数不足则补0）
	 * 
	 * 字段2：纬度N（北纬）或S（南纬）
	 * 
	 * 字段3：经度dddmm.mmmm，度分格式（前导位数不足则补0）
	 * 
	 * 字段4：经度E（东经）或W（西经）
	 * 
	 * 字段5：UTC时间，hhmmss.sss格式
	 * 
	 * 字段6：状态，A=定位，V=未定位
	 * 
	 * 字段7：校验值
	 * 
	 * 解析GPS信号
	 * 
	 * @param gps
	 *            $GPGLL,4250.5589,S,14718.5084,E,092204.999,A*2D
	 */
	public static Map<String, String> parsingGPS(String gps) {
		if (!gps.startsWith("$GPGLL")) {
			try {
				throw new Exception("不符合GPS信号格式");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String[] gpsArr = gps.split(",");
		Map<String, String> map = new LinkedHashMap<>();
		map.put(gpsArr[2], gpsArr[1]);
		map.put(gpsArr[4], gpsArr[3]);
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss.sss");
		try {
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = sdf.parse(gpsArr[5]);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			Calendar cal2 = Calendar.getInstance();
			cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, cal2.get(Calendar.DAY_OF_MONTH));

			SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
			now.setTimeZone(TimeZone.getDefault());
			map.put("time", now.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		Map map = parsingGPS("$GPGLL,4250.5589,S,14718.5084,E,092204.999,A*2D");
		System.out.println(map);
	}
}
