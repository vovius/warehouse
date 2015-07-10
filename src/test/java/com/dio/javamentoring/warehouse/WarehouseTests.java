package com.dio.javamentoring.warehouse;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

public class WarehouseTests {
	
	private Warehouse warehouse;
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyy");

	@Before
	public void initTests() throws Exception {
		warehouse = new Warehouse();
		warehouse.initTVManageService("C:\\111\\");
	}

	
	@Test // all the storages initialized
	public void testStoragesCount() throws Exception {
		
		assertEquals(StorageType.values().length, warehouse.getStorages().size());
	}

	@Test // adding item to the storage
	public void testAddItemToStorage() throws Exception {
		TVStorage storage = warehouse.getStorage(StorageType.TXT);
		int id = storage.getNewId();
		TV item = new TV.Builder()
						.id(id)
						.brand("I hate Justin Beaber")
						.diagonal(32)
						.dateMade(DATE_FORMAT.parse("1/1/2000"))
						.matrixType(MatrixType.LCD)
						.description("I hate Britney Spears")
						.build();
		storage.addItem(item);
		
		assertEquals(storage.getItemById(id).equals(item),true);
	}

}
