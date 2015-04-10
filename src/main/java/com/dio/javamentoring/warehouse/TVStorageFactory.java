package com.dio.javamentoring.warehouse;


public interface TVStorageFactory {
	public boolean saveStorage(StorageType storageType);
	public TVStorage getStorage(StorageType storageType) throws Exception;
	public boolean saveAllStorages();
}
