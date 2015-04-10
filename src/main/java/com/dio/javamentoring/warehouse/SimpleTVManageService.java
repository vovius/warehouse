package com.dio.javamentoring.warehouse;

import java.util.ArrayList;
import java.util.List;

public class SimpleTVManageService implements TVManageService {
	
	private TVStorageFactoryImpl factory;

	public TVStorageFactory createStorageFactory(String factoryFolder) {
		if (factory == null)
			factory = new TVStorageFactoryImpl(factoryFolder);
		return factory;
	}

	public void saveStorageFactory(String factoryFolder) throws Exception {
		if (factory == null)
			throw new Exception("Factory is not initialized!");
		
		factory.saveAllStorages();
	}

	public boolean initByStorageType(StorageType storageType) throws Exception {
		if (factory == null)
			throw new Exception("Factory is not initialized!");

		return factory.getStorage(storageType) != null;
	}

	public void printStorage(StorageType storageType)  throws Exception {
		if (factory == null)
			throw new Exception("Factory is not initialized!");
		
		factory.printStorage(storageType);

	}

	public void editStorages() {
		factory.editConsole();
	}

	public TVStorage getStorage(StorageType storageType) throws Exception {
		return factory.getStorage(storageType);
	}
	
	public List<TVStorage> getStorages() throws Exception {
		List<TVStorage> storageList = new ArrayList<TVStorage>();
		for (StorageType storageType : StorageType.values()) {
			if (factory.isStorageInitialized(storageType))
				storageList.add(factory.getStorage(storageType));
		}
		
		return storageList;
	}

}
