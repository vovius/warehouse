package com.dio.javamentoring.warehouse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TVStorageFactoryImpl implements TVStorageFactory {
	
	private Map<StorageType,TVStorageInterface> storage = new HashMap<StorageType,TVStorageInterface>();;
	private final String storagePath;
	
	public TVStorageFactoryImpl(String storagePath) {
		this.storagePath = storagePath; 
	}

	public boolean saveStorage(StorageType storageType) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean saveAllStorages() {
		
		Iterator<StorageType> itr = storage.keySet().iterator();
		while (itr.hasNext()) {
			saveStorage(itr.next());
		}
		
		return true;
	}

	public TVStorageInterface getStorage(StorageType storageType) throws Exception {
		if (storage.containsKey(storageType))
			return storage.get(storageType);
		
		String fileName = storageType.addExt(storagePath + "tvlist");
		TVStorageInterface tvStorage;
		switch (storageType) {
		case TXT :
		default :
			tvStorage = new TxtTVStorage(fileName);
			break;
		
		case CSV :
			tvStorage = new CsvTVStorage(fileName); 
			break;
		}
		
		storage.put(storageType, tvStorage);
		return tvStorage;
		
	}
	
	public boolean printStorage(StorageType storageType) {
		if (!storage.containsKey(storageType))
			return false;
		
		TVStorageInterface tvStorage = storage.get(storageType);
		tvStorage.print();
		
		return true;
	}
	
	
	
	public static void main(String args[]) throws Exception {
		TVStorageFactory factory = new TVStorageFactoryImpl("c:\\111\\");
		TVStorageInterface storage = factory.getStorage(StorageType.CSV);
		storage.print();
		storage = factory.getStorage(StorageType.TXT);
		storage.print();
	}


}
