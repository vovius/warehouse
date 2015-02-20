package com.dio.javamentoring.warehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CommonTVStorage implements TVStorageInterface {
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	protected List<TV> goodsList = new ArrayList<TV>();	
	public CommonTVStorage() {}
	
	public CommonTVStorage(String fileName) throws Exception {
		fillFromFile(fileName);
	}

	public TV addItem(TV item) {
		if (goodsList.add(item))
			return item;
		return null;
	}

	public void print() {
		TV item;
		Iterator<TV> iterator;
		for (iterator = goodsList.iterator() ; iterator.hasNext() ; ) {
			item = iterator.next();
			System.out.println(item.toString());
		}
	}

}
