package com.dio.javamentoring.warehouse;

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

	public TVStorageInterface getStorage(StorageType storageType) throws Exception {
		return factory.getStorage(storageType);
	}
	
	

}
