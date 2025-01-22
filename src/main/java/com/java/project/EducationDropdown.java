package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="educationDrop")
@SessionScoped
public class EducationDropdown {

	private List<SelectItem> education;

	public List<SelectItem> getEducation() {
		return education;
	}

	public void setEducation(List<SelectItem> education) {
		this.education = education;
	}

	public EducationDropdown() {

		education = Arrays.stream(Education.values()).map(i -> new SelectItem(i, i.name()))
				.collect(Collectors.toList());

	}
}
