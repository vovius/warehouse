package com.dio.javamentoring.warehouse;

import java.util.List;

public class Warehouse {	
	private TVManageService manageService = new SimpleTVManageService();
	
	public void initTVManageService(String factoryFolder) throws Exception {
		manageService.createStorageFactory(factoryFolder);
		manageService.initByStorageType(StorageType.CSV);
		manageService.initByStorageType(StorageType.TXT);
	}
	
	public void printStorages() throws Exception {
		manageService.printStorage(StorageType.CSV);
		manageService.printStorage(StorageType.TXT);			
	}
	
	public void editStorages() {
		manageService.editStorages();
	}
	
	public TVManageService getManageService() {
		return manageService;
	}
	
	public TVStorageInterface getStorage(StorageType storageType) throws Exception {
		return manageService.getStorage(storageType);
	}
	
	public List<TVStorageInterface> getStorages() throws Exception {
		return manageService.getStorages();
	}
	
	
	public static void main(String[] args) throws Exception {
		Warehouse warehouse = new Warehouse();
		warehouse.initTVManageService("C:\\111\\");
		warehouse.printStorages();
		
		warehouse.editStorages();
	}
}
