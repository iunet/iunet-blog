package iunet.model.um;

import java.sql.Timestamp;

public class Person {
	
	private Integer id;
	private String name;
	private Integer sex;
	private String duty_title;
	private String address;
	private String email;
	private String office_phone;
	private String home_phone;
	private String mobile_phone;
	private Timestamp join_date;
	private Timestamp create_time;
	private Timestamp birthday;
	private String description;
	private String photo_path;
	private Integer right_level;
	private String user_card;
	private String user_code;
	private Integer state;
	private String field_1;
	private String field_2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getDuty_title() {
		return duty_title;
	}

	public void setDuty_title(String duty_title) {
		this.duty_title = duty_title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getHome_phone() {
		return home_phone;
	}

	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public Timestamp getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Timestamp join_date) {
		this.join_date = join_date;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	public Integer getRight_level() {
		return right_level;
	}

	public void setRight_level(Integer right_level) {
		this.right_level = right_level;
	}

	public String getUser_card() {
		return user_card;
	}

	public void setUser_card(String user_card) {
		this.user_card = user_card;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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