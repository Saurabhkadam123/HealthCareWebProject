package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="pTypes")
@ViewScoped
public class ProviderTypesDropdown {

	private List<SelectItem> providerTypes;

	public List<SelectItem> getProviderTypes() {
		return providerTypes;
	}

	public void setProviderTypes(List<SelectItem> providerTypes) {
		this.providerTypes = providerTypes;
	}
	
	//LISTS THE ENUM VALUE OF PROVIDER TYPES TO DROP DOWN
	public ProviderTypesDropdown()
	{
		providerTypes = Arrays.stream(ProviderTypes.values()).map(i -> new SelectItem(i, i.name())).collect(Collectors.toList());

	}
}
