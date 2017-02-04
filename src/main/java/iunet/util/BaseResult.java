package iunet.util;

import com.alibaba.fastjson.JSONObject;

public class BaseResult {

	private int errorCode;

	private JSONObject res;

	private String errorMessage;

	public BaseResult(int errorCode, JSONObject res, String errorMessage) {
		this.errorCode = errorCode;
		this.res = res;
		this.errorMessage = errorMessage;
	}

	public static BaseResult returnErrorMessage(JSONObject res, String errorMessage) {
		return new BaseResult(99, res, errorMessage);
	}
	
	public static BaseResult returnErrorMessage(int errorCode, String errorMessage) {
		return new BaseResult(errorCode, new JSONObject(), errorMessage);
	}

	public static BaseResult returnErrorMessage(String errorMessage) {
		return new BaseResult(99, new JSONObject(), errorMessage);
	}

	public static BaseResult returnSuccessMessage(JSONObject res, String errorMessage) {
		return new BaseResult(0, res, errorMessage);
	}
	
	public static BaseResult returnSuccessMessage(int errorCode, String errorMessage) {
		return new BaseResult(errorCode, new JSONObject(), errorMessage);
	}

	public static BaseResult returnSuccessMessage(String errorMessage) {
		return new BaseResult(0, new JSONObject(), errorMessage);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public JSONObject getRes() {
		return res;
	}

	public void setRes(JSONObject res) {
		this.res = res;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "{success:" + errorCode + ", res:" + res + ", error:" + errorMessage + "}";
	}
}
