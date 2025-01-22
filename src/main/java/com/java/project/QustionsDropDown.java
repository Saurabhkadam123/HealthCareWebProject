package com.java.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="qDrop")
@ViewScoped
public class QustionsDropDown {

	private List<SelectItem> questions;
	

	public List<SelectItem> getQuestions() {
		return questions;
	}

	public void setQuestions(List<SelectItem> questions) {
		this.questions = questions;
	}

	//DROP DOWN VALUE FOR QUESTION NO 1
	public QustionsDropDown() {
		this.questions = Arrays.stream(Questions.values()).map(q -> new SelectItem(q, q.name())).collect(Collectors.toList());

	}
	
}
