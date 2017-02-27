package iunet.util;

public class TimeSpan {
	
	private long _millisecond;
	
	public TimeSpan(long millisecond){
		_millisecond = millisecond;
	}
	
	//相距的天数
	public double getDays(){
		return _millisecond/1000.0/60/60/24;
	}
	
	//相距的小时
	public double getHours(){
		return _millisecond/1000.0/60/60;
	}
	
	//相距的分钟
	public double getMinutes(){
		return _millisecond/1000.0/60;
	}
	
	//相跑的毫秒数
	public long getMillisecond(){
		return this._millisecond;
	}
}