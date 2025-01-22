package com.java.project;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name="q2")
@ViewScoped
public class Question2Dropdown {

	private List<SelectItem> questions2;

	public List<SelectItem> getQuestions2() {
		return questions2;
	}

	public void setQuestions2(List<SelectItem> questions2) {
		this.questions2 = questions2;
	}
	
	public Question2Dropdown()
	{
		questions2 = Arrays.stream(Questions2.values()).map(c -> new SelectItem(c, c.name())).collect(Collectors.toList());

	}
	
}
