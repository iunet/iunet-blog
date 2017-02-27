package iunet.util;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

public class SerializerUtil {
	private static final Logger log = LoggerFactory.getLogger(SerializerUtil.class);
	
	private Set<String> propertySet;
	private Set<String> valueSet;
	private Boolean encryption;
	private HttpSession session;
	
	private PropertyFilter propertyfilter = new PropertyFilter(){
		@Override
		public boolean apply(Object object, String name, Object value) {
			if(propertySet.contains(name)) {
				log.info("[跳过序列化] {}", name);
				return false;
			}
			return true;
		}
	};
	
	private ValueFilter valuefilter = new ValueFilter(){
		@Override
		public Object process(Object object, String name, Object value) {
			if(value == null) {
				log.info("[过滤] {}-->{}", value, "''");
				return "";
			}
			if(valueSet.contains(name)) {
				log.info("SerializerUtil 加密解密敏感数据：{}", name);
				if(encryption) {
					String randomStr = StringUtil.getRandom(32);
					session.setAttribute(randomStr, value);
					log.info("[加密] value:{}-->{}", value, randomStr);
					return randomStr;
				} else {
					Object realValue = session.getAttribute((String) value);
					log.info("[解密] value:{}-->{}", value, realValue);
					return realValue;
				}
			}
			return value;
		}
	};
	
	public SerializerUtil(HttpSession session, Set<String> propertySet, Set<String> valueSet, Boolean encryption) {
		this.propertySet = propertySet;
		this.valueSet = valueSet;
		this.session = session;
		this.encryption = encryption;
	}
	
	public static JSONObject skipByName(Object data, Set<String> propertySet) {
		JSONObject req = (JSONObject) JSON.toJSON(data);
		SerializerUtil serializer = new SerializerUtil(null, propertySet, null, null);
		if(null != propertySet) {
			return (JSONObject) req.parse(req.toJSONString(req, serializer.propertyfilter));
		}
		return req;
	}
	
	public static JSONObject encryptionByName(HttpSession session, Object data, Set<String> valueSet, boolean encryption) {
		JSONObject req = (JSONObject) JSON.toJSON(data);
		SerializerUtil serializer = new SerializerUtil(session, null, valueSet, encryption);
		if(null != valueSet) {
			return (JSONObject) req.parse(req.toJSONString(req, serializer.valuefilter));
		}
		return req;
	}
	
}
