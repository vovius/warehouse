package com.dio.javamentoring.warehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class CommonTVStorage implements TVStorage {

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
	
	public void setItem(TV item) {		
		boolean found = false;
		for (ListIterator<TV> iterator = goodsList.listIterator() ; iterator.hasNext() ; ) {
			TV curItem = iterator.next();
			if (curItem.getId() == item.getId()) {
				found = true;
				iterator.set(item);
			}
		}
		
		if (!found) {
			item.setId(getNewId());
			addItem(item);
		}
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

	public void deleteItem(int id) {
		
		for (ListIterator<TV> itr = goodsList.listIterator(); itr.hasNext(); ) {
			TV item = itr.next();
			if (item.getId() == id) {
				itr.remove();
				break;
			}
		}
		
	}
	
	public TV getItemById(int id) {
		for (Iterator<TV> itr = goodsList.iterator(); itr.hasNext(); ) {
			TV item = itr.next();
			if (item.getId() == id) {
				return item;
			}
		}
		
		return null;
	}
	

}
