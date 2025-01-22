package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "councilDrop")
@SessionScoped
public class CouncilDropdown {

	private List<SelectItem> council;

	public List<SelectItem> getCouncil() {
		return council;
	}

	public void setCouncil(List<SelectItem> council) {
		this.council = council;
	}

	public CouncilDropdown() {
		council = Arrays.stream(Council.values()).map(i -> new SelectItem(i, i.name())).collect(Collectors.toList());

	}
}
