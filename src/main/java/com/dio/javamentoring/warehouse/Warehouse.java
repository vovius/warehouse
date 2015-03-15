package com.dio.javamentoring.warehouse;

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
	
	public static void main(String[] args) throws Exception {
		Warehouse warehouse = new Warehouse();
		warehouse.initTVManageService("C:\\111\\");
		warehouse.printStorages();
		
		warehouse.editStorages();
	}
}
