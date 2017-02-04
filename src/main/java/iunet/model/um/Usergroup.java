package iunet.model.um;

public class Usergroup {
	
	private Integer id;
	private Integer parent_id;
	private String name;
	private Integer type;
	private String group_code;
	private String layer;
	private String description;
	private String folder_path;
	private Integer quota_size;
	private Integer quota_used;
	private String app_param;
	private String field_1;
	private String field_2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFolder_path() {
		return folder_path;
	}

	public void setFolder_path(String folder_path) {
		this.folder_path = folder_path;
	}

	public Integer getQuota_size() {
		return quota_size;
	}

	public void setQuota_size(Integer quota_size) {
		this.quota_size = quota_size;
	}

	public Integer getQuota_used() {
		return quota_used;
	}

	public void setQuota_used(Integer quota_used) {
		this.quota_used = quota_used;
	}

	public String getApp_param() {
		return app_param;
	}

	public void setApp_param(String app_param) {
		this.app_param = app_param;
	}

	public String getField_1() {
		return field_1;
	}

	public void setField_1(String field_1) {
		this.field_1 = field_1;
	}

	public String getField_2() {
		return field_2;
	}

	public void setField_2(String field_2) {
		this.field_2 = field_2;
	}
}