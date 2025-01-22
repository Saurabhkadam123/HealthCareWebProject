package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="gDrop")
@SessionScoped
public class GenderDropdown {

	private List<SelectItem> gender;

	public List<SelectItem> getGender() {
		return gender;
	}

	public void setGender(List<SelectItem> gender) {
		this.gender = gender;
	}

	public GenderDropdown() {
		gender = Arrays.stream(Gender.values()).map(i -> new SelectItem(i, i.name())).collect(Collectors.toList());
	}
}
