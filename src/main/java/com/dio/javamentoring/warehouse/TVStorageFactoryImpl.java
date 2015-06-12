package com.dio.javamentoring.warehouse;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class TVStorageFactoryImpl implements TVStorageFactory {
	
	private Map<StorageType,TVStorage> storage = new HashMap<StorageType,TVStorage>();;
	private final String storagePath;
	private Properties properties = null;
	
	public TVStorageFactoryImpl(String storagePath) {
		this.storagePath = storagePath; 
		try {
			getProperties();
		} catch (Exception e) {
			System.out.println("Cannot read the properties, exception:");
			e.printStackTrace();
		}
		
	}

	public boolean saveStorage(StorageType storageType) {
		if (isStorageInitialized(storageType)) {
			TVStorage curStorage = storage.get(storageType);
			try {
				curStorage.saveLastLoaded();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return true;
	}
	
	public boolean saveAllStorages() {
		
		Iterator<StorageType> itr = storage.keySet().iterator();
		while (itr.hasNext()) {
			saveStorage(itr.next());
		}
		
		return true;
	}
	
	private Properties getProperties() throws Exception {
		if (properties == null) {
			properties = new Properties();
			String propFileName = "config.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			 
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}	
		}
		
		return properties;
	}

	public TVStorage getStorage(StorageType storageType) throws Exception {
		if (storage.containsKey(storageType))
			return storage.get(storageType);
		
		String fileName = storageType.addExt(storagePath + "tvlist");
		TVStorage tvStorage;
		switch (storageType) {
		case TXT :
		default :
			tvStorage = new TxtTVStorage(fileName);
			break;
		
		case CSV :
			tvStorage = new CsvTVStorage(fileName); 
			break;
			
		case XLS:
			tvStorage = new XlsTVStorage(fileName); 
			break;
			
		case DB:
		case DBPREP: 
			String dbInitString = properties.getProperty("dbstorage");
			if (dbInitString != null)
				tvStorage = storageType == StorageType.DB ? new DbTVStorage(dbInitString) : new DbPrepTVStorage(dbInitString);
			else
				tvStorage = null;
			break;

		case HIBERNATE:
			String dbInitStringHib = properties.getProperty("hibernate_conf");
			tvStorage = new HibernateTVStorage(dbInitStringHib);
			break;
		}
		
		
		if (tvStorage != null)
			storage.put(storageType, tvStorage);
		
		return tvStorage;
		
	}
	
	public boolean printStorage(StorageType storageType) {
		if (!storage.containsKey(storageType))
			return false;
		
		TVStorage tvStorage = storage.get(storageType);
		tvStorage.print();
		
		return true;
	}
	
	public boolean isStorageInitialized(StorageType storageType) {
		if (!storage.containsKey(storageType))
			return false;
		
		return true;
	}
	
	public String toString() {
		StringBuilder storageString = new StringBuilder();
		
		for (Iterator<StorageType> itr = storage.keySet().iterator() ; itr.hasNext() ; ) {
			StorageType storageType = itr.next();
			storageString = storageString.append(storageType.toString());
			if (itr.hasNext())
				storageString = storageString.append(", ");
		}
		
		return storageString.toString();
	}
	
	public void editConsole() {
		TVConsoleEditWizard wizard = new TVConsoleEditWizard(this);
		wizard.start(wizard);
	}
		
}
