package com.dio.javamentoring.warehouse;


public interface TVStorageFactory {
	public boolean saveStorage(StorageType storageType);
	public TVStorageInterface getStorage(StorageType storageType) throws Exception;
	public boolean saveAllStorages();
}
