package iunet.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class transformUtil {
	private static final Logger log = LoggerFactory.getLogger(transformUtil.class);

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;
		Object obj = beanClass.newInstance();
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}

	public static Map<?, ?> objectToMap(Object obj) {
		if (obj == null)
			return null;
		return new org.apache.commons.beanutils.BeanMap(obj);
	}
	
	public static Set<String> arrayToSet(String[] arr) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
		return set;
	}
}
