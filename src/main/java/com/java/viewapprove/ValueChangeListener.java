package com.java.viewapprove;

	import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
	
	@ManagedBean(name="select")
	@SessionScoped
	public class ValueChangeListener
	
	{
		private String selectType;

		public String getSelectType() {
			return selectType;
		}

		public void setSelectType(String selectType) {
			this.selectType = selectType;
		}
         


	}
	
