package com.dio.javamentoring.warehouse;

import java.util.List;

public interface TVManageService {
	public TVStorageFactory createStorageFactory(String factoryFolder);
	public void saveStorageFactory(String factoryFolder) throws Exception;
	public boolean initByStorageType(StorageType storageType) throws Exception;
	public void printStorage(StorageType storageType) throws Exception;
	public void editStorages();
	public TVStorage getStorage(StorageType storageType) throws Exception;
	public List<TVStorage> getStorages() throws Exception;
}
