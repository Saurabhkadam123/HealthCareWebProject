package com.java.patienthistory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name="patHisListener")
@SessionScoped
public class PatientHistoryListeners {

	
	private String valueSearch;
	private String valueType;
	private boolean showErrorBox;
	private boolean showTable=false;
	private boolean search;
	private boolean reset;
	
	
	
	public String getValueSearch() {
		return valueSearch;
	}
	public void setValueSearch(String valueSearch) {
		this.valueSearch = valueSearch;
	}
	
	public boolean isSearch() {
		return search;
	}
	public void setSearch(boolean search) {
		this.search = search;
	}
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
	public boolean isShowTable() {
		return showTable;
	}
	public void setShowTable(boolean showTable) {
		this.showTable = showTable;
	}
	public boolean isShowErrorBox() {
		return showErrorBox;
	}
	public void setShowErrorBox(boolean showErrorBox) {
		this.showErrorBox = showErrorBox;
	}

	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	
	public void valueChange(ValueChangeEvent e){	
		this.valueSearch = e.getNewValue().toString();
	    this.valueType = e.getNewValue().toString();	
	}
	
	
	public void clearInputFunc(){ // reset button
		
		valueSearch="";
		showErrorBox=false;
		showTable=false;
	}

	
	public void handleSearch(){ // search button
	
	    if(valueSearch.trim().length()==0)
	    {
	    	showErrorBox = true;
	    	showTable = false;
	    }
	    else
	    {
	    	showErrorBox = true;
	    	showTable = true;
	    }
    }

	
	
	public void handelChange() // radio button : - Displaying Input Text
	{
		if(valueType=="")
		{
			search = true;
			reset = true;
		}
		else
		{
			search = true;
			reset = true;
			showTable = false;
			showErrorBox=false;
		}
	}
}