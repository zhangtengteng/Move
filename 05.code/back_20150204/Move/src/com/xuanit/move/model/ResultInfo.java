package com.xuanit.move.model;

import java.util.ArrayList;

public class ResultInfo<T> {
	public String Code;
	public String Item2;
	private ArrayList<T> list = new ArrayList<T>();
	
	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}
}
