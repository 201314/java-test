package com.bjsxt.height.design016;

public final class Data {
	private String id;
	private String name;
	
	public Data(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString(){
		return "{id: " + id + ", name: " + name + "}";
	}
}
