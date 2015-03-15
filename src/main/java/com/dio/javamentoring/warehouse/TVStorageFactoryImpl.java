package com.dio.javamentoring.warehouse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

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
	
	public boolean isStorageInitialized(StorageType storageType) {
		if (!storage.containsKey(storageType))
			return false;
		
		return true;
	}
	
	public String toString() {
		StringBuilder storageString = new StringBuilder();
		
		for (Iterator<StorageType> itr = storage.keySet().iterator() ; itr.hasNext() ; ) {
			StorageType storageType = itr.next();
			storageString = storageString.append(storageType.toString());
			if (itr.hasNext())
				storageString = storageString.append(", ");
		}
		
		return storageString.toString();
	}
	
	public void editConsole() {
		TVConsoleEditWizard wizard = new TVConsoleEditWizard(this);
		wizard.start(wizard);
	}
		
}
