package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.sun.faces.taglib.jsf_core.SelectItemsTag;

@ManagedBean(name="categoryDrop")
@SessionScoped
public class CategoryDropdown {
	
	private List<SelectItem> category;

	public List<SelectItem> getCategory() {
		return category;
	}

	public void setCategory(List<SelectItem> category) {
		this.category = category;
	}
	
	public CategoryDropdown(){
		category= Arrays.stream(Category.values()).map(i -> new SelectItem(i, i.name())).collect(Collectors.toList());

	}
}
