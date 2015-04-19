package com.dio.javamentoring.warehouse;

import java.util.List;

public interface TVStorage {
	public TVStorage fillFromFile(String fileName) throws Exception;
	public boolean saveToFile(String fileName) throws Exception;
	public void saveLastLoaded() throws Exception;
	public TV addItem(TV item);
	public void print();
	public List<TV> getStorageList();
	public StorageType getType();
	public int getNewId();
	public void deleteItem(int id);
	public TV getItemById(int id);
	public void setItem(TV item);
}
