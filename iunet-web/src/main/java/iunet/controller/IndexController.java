package iunet.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import iunet.model.BaseResult;
import iunet.model.proj.UmUser;
import iunet.service.IndexService;
import iunet.service.UserService;
import iunet.util.DateUtil;
import iunet.util.WebUtil;
import iunet.util.XmlUtil;


@Controller
@RequestMapping("/index")
public class IndexController {
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	IndexService indexService;
	
	@Autowired
	UserService userService;
	
	/**
	 * 更换主题
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeSkin", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult changeSkin(@RequestBody JSONObject req, HttpServletRequest request) {
		log.info("/index/changeSkin.do start.. time：{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		try {
			
			String skin = req.getString("skin");
			if(skin == null) {
				return BaseResult.returnErrorMessage("请求参数为空！");
			}
			
			JSONObject res = (JSONObject) session.getAttribute("cache_user");
			BigDecimal user_id = (BigDecimal) session.getAttribute("cache_user_id");
			if (null == res || null == user_id) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage("用户信息已失效，请重新登录！");
			}
			
			UmUser user = new UmUser();
			user.setId(user_id);
			user.setAppParam(skin);
			userService.updateUser(user);
			log.info("更换主题成功！skin:{}", skin);
			return BaseResult.returnSuccessMessage("更换主题成功！");
		} catch (Exception e) {
			log.error("/index/changeSkin.do,{}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("更换主题失败！");
	}
	
	/**
	 * 获取用户权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserFunctionTree", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult getUserFunctionTree(HttpServletRequest request) {
		log.info("/index/getUserFunctionTree.do start.. time：{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		JSONObject req = new JSONObject();
		JSONArray functionArray = new JSONArray();
		try {
			JSONObject res = (JSONObject) session.getAttribute("cache_user");
			BigDecimal user_id = (BigDecimal) session.getAttribute("cache_user_id");
			if (null == res || null == user_id) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage("用户信息已失效，请重新登录！");
			}
			
			Document dsAllRole = XmlUtil.list2xml(indexService.queryFunctions(), "function");
			Document dsUserRole = XmlUtil.list2xml(indexService.queryUserRoleByUserId(user_id), "function");
			
			Element allRoleRoot = dsAllRole.getRootElement();
			List<Element> elmentsAllRole = allRoleRoot.elements();
			List<Element> elmentsUserRole = dsUserRole.getRootElement().elements();
			String functionType = null;
			String functinId = null;
			String functionParentId = null;
			String allFunctionId = null;
			/**
			 * 用户的权限可能是一个叶节点， 那么此时这个叶节点的上层应该都需要有权限
			 */
			for (Element itemUser : elmentsUserRole)
			{
				/** 类型为1和0的 */
				functionType = XmlUtil.tryGetItemText(itemUser, "type", null);
				if (functionType.equals("1") || functionType.equals("0"))
				{
					functinId = XmlUtil.tryGetItemText(itemUser, "id", null);
					functionParentId = XmlUtil.tryGetItemText(itemUser, "parentId", null);

					List<Element> functionRoles = allRoleRoot.selectNodes("function[id=" + functinId + " or id=" + functionParentId + "]");

					for (Element functionRole : functionRoles)
					{
						allFunctionId = XmlUtil.tryGetItemText(functionRole, "id", null);
						if ((functionType.equals("1") && allFunctionId.equals(functionParentId))
								|| (functionType.equals("0") && allFunctionId.equals(functinId)))
						{
							functionRole.selectSingleNode("flag").setText("1");
							break;
						}
					}
				}
			}

			String functionId = null;
			JSONObject obj = null;
			for (Element itemAll : elmentsAllRole)
			{
				functionId = XmlUtil.tryGetItemText(itemAll, "id", "");
				if (itemAll.selectSingleNode("flag").getText().equals("1") || hadSubItemRight(dsAllRole, Integer.parseInt(functionId)))
				{
					obj = new JSONObject();
					obj.put("id", functionId);
					obj.put("param", XmlUtil.tryGetItemText(itemAll, "param", ""));
					obj.put("name", XmlUtil.tryGetItemText(itemAll, "name", ""));
					obj.put("icon", XmlUtil.tryGetItemText(itemAll, "icon", ""));
					obj.put("parentId", XmlUtil.tryGetItemText(itemAll, "parentId", ""));
					functionArray.add(obj);
				}
			}
			req.put("functions", functionArray);
			log.info("获得用户的权限树成功！res:{}", req);
			return BaseResult.returnSuccessMessage(req, "获得用户的权限树成功！");
		} catch (Exception e) {
			log.error("/index/getUserFunctionTree.do,{}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("获得用户的权限树失败！");
	}

	/**
	 * 验证是否具有子功能权限
	 * 
	 * @param ds
	 * @param parentId
	 * @return
	 */
	private boolean hadSubItemRight(Document ds, int parentId)
	{
		List<Element> elments = ds.getRootElement().selectNodes("function[id!=0 and parentId=" + parentId + "]");
		String strId = null;

		for (Element item : elments)
		{
			strId = item.selectSingleNode("id").getText();

			if (item.selectSingleNode("flag").getText().equals("1") || hadSubItemRight(ds, Integer.parseInt(strId)))
			{
				return true;
			}
		}
		return false;
	}
}