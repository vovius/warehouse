package com.dio.javamentoring.warehouse;

public enum StorageType {
	TXT, CSV, XLS, DB, DBPREP;

	public String getExt() {
		return this.toString().toLowerCase();
	}
	
	public String addExt(String fileName) {
		String ext = getExt();
		return new StringBuilder().append(fileName).append('.').append(ext).toString();
	}
	
}
