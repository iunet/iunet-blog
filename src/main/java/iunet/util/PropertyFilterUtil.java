package iunet.util;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.serializer.PropertyFilter;

public class PropertyFilterUtil implements PropertyFilter {
	private static final Logger log = LoggerFactory.getLogger(PropertyFilterUtil.class);
	
	private Set<String> encryptionSet;
	private Set<String> removeSet;
	private Boolean encryption;
	private HttpSession session;

	@Override
	public boolean apply(Object object, String name, Object value) {
		log.info("fastjson serializer apply object{} name：{},value：{}", object, name, value);
		if(removeSet.contains(name)) {
			log.info("fastjson serializer apply [remove] name：{},value：{}", name, value);
			return false;
		}
		if(encryptionSet.contains(name)) {
			log.info("fastjson serializer apply encryption:{}", encryption);
			if(encryption) {
				log.info("[encryption:加密前] name：{},value：{}", name, value);
				String randomStr = StringUtil.getRandom(32);
				session.setAttribute(randomStr, value);
				value = randomStr;
				log.info("[encryption:加密后] name：{},value：{}", name, value);
			} else {
				log.info("[encryption:加密前] name：{},value：{}", name, value);
				Object realValue = session.getAttribute((String) value);
				value = realValue;
				log.info("[encryption:加密后] name：{},value：{}", name, value);
			}
		}
		return true;
	}
	
	public PropertyFilterUtil(HttpServletRequest request, Set<String> encryptionSet, Set<String> removeSet, boolean encryption) {
		this.encryptionSet = encryptionSet == null? new HashSet<String>() : encryptionSet;
		this.removeSet = removeSet == null? new HashSet<String>() : removeSet;
		this.encryption = encryption;
		this.session = request.getSession();
	}
}
