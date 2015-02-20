package com.dio.javamentoring.warehouse;

public interface TVStorageInterface {
	public TVStorageInterface fillFromFile(String fileName) throws Exception;
	public boolean saveToFile(String fileName);
	public TV addItem(TV item);
	public void print();
}
