package com.java.viewapprove;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;



@ManagedBean(name="val")
@SessionScoped
public class SearchByDate 
{
	public String valueChange;

	public String getValueChange() {
		return valueChange;
	}

	public void setValueChange(String valueChange) {
		this.valueChange = valueChange;
	}
	
	public void dateSelect(ValueChangeEvent e) 
	{
		this.valueChange = e.getNewValue().toString();
	}
}
