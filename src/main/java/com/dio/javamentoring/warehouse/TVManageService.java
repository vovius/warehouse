package com.dio.javamentoring.warehouse;

public interface TVManageService {
	public void createStorageFactory(String factoryFolder);
	public void saveStorageFactory(String factoryFolder) throws Exception;
	public boolean initByStorageType(StorageType storageType) throws Exception;
	public void printStorage(StorageType storageType) throws Exception;
	public void editStorages();
}
