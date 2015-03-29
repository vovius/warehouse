package com.dio.javamentoring.warehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CommonTVStorage implements TVStorageInterface {
	private StorageType storageType;
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	protected List<TV> goodsList = new ArrayList<TV>();	
	public CommonTVStorage() {}
	
	public CommonTVStorage(String fileName, StorageType storageType) throws Exception {
		this.storageType = storageType;
		fillFromFile(fileName);
	}

	public TV addItem(TV item) {
		if (goodsList.add(item))
			return item;
		return null;
	}

	public void print() {
		System.out.println("Storage="+storageType.name());
		for (Iterator<TV> iterator = goodsList.iterator() ; iterator.hasNext() ; ) {
			TV item = iterator.next();
			System.out.println(item.toString());
		}
	}
	
	public List<TV> getStorageList() {
		return goodsList;
	}
	
	public StorageType getType() {
		return storageType;
	}
	
	public int getNewId() {
		int maxId = 0;
		for (Iterator<TV> iterator = goodsList.iterator() ; iterator.hasNext() ; ) {
			TV item = iterator.next();
			int id = item.getId(); 
			if (id > maxId)
				maxId = id;
		}
		
		return maxId+1;
	}

}
