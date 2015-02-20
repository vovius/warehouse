package com.dio.javamentoring.warehouse;

public class SimpleTVManageService implements TVManageService {
	
	private TVStorageFactoryImpl factory;

	public void createStorageFactory(String factoryFolder) {
		if (factory == null)
			factory = new TVStorageFactoryImpl(factoryFolder);
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

}
