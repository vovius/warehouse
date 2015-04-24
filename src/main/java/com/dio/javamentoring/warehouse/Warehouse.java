package com.dio.javamentoring.warehouse;

import java.util.List;

public class Warehouse {	
	private TVManageService manageService = new SimpleTVManageService();
	
	public void initTVManageService(String factoryFolder) throws Exception {
		manageService.createStorageFactory(factoryFolder);
		manageService.initByStorageType(StorageType.CSV);
		manageService.initByStorageType(StorageType.TXT);
		manageService.initByStorageType(StorageType.XLS);
		manageService.initByStorageType(StorageType.DB);
		manageService.initByStorageType(StorageType.DBPREP);
	}
	
	public void printStorages() throws Exception {
		manageService.printStorage(StorageType.CSV);
		manageService.printStorage(StorageType.TXT);			
		manageService.printStorage(StorageType.XLS);			
		manageService.printStorage(StorageType.DBPREP);			
	}
	
	public void editStorages() {
		manageService.editStorages();
	}
	
	public TVManageService getManageService() {
		return manageService;
	}
	
	public TVStorage getStorage(StorageType storageType) throws Exception {
		return manageService.getStorage(storageType);
	}
	
	public List<TVStorage> getStorages() throws Exception {
		return manageService.getStorages();
	}
	
	
	public static void main(String[] args) throws Exception {
		Warehouse warehouse = new Warehouse();
		warehouse.initTVManageService("C:\\111\\");
		warehouse.printStorages();
		
		warehouse.editStorages();
	}
}
