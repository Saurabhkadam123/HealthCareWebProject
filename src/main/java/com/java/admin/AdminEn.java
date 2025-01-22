package com.java.admin;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="referencedata")
@SessionScoped
@ManagedBean(name="refpojo")
public class AdminEn {

	@Id
	@Column(name="id")
	private int id;
	@Column(name="module")
	private String module;
	@Column(name="type")
	private String type;
	@Column(name="value")
	private String value;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "AdminEn [id=" + id + ", module=" + module + ", type=" + type + ", value=" + value + "]";
	}
	
	
}
