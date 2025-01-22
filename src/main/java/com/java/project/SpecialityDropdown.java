package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="specialityDrop")
@ViewScoped
public class SpecialityDropdown {

	private List<SelectItem> speciality;

	public List<SelectItem> getSpeciality() {
		return speciality;
	}

	public void setSpeciality(List<SelectItem> speciality) {
		this.speciality = speciality;
	}
	
	public SpecialityDropdown(){
		speciality= Arrays.stream(Speciality.values()).map(i -> new SelectItem(i, i.name())).collect(Collectors.toList());

	}
}
