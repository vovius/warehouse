package com.dio.javamentoring.warehouse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

public class TVConsoleEditWizard {
	
	private Map<String,Object> paramsMap = new HashMap<String,Object>();
	private TVStorageFactory factory;
	private List<Action> actionList = new LinkedList<Action>();
	Scanner scanner = new Scanner(System.in);
	
	public TVConsoleEditWizard(TVStorageFactory factory) {
		this.factory = factory;
		
		fillActionList();
	}
	
	private void fillActionList() {
		actionList.add(new Action("actionToStorageSelect","actionToStorageSelect"));
		actionList.add(new Action("actionStorageSelect","actionStorageSelect"));
		actionList.add(new Action("actionStorageEdit","actionStorageEdit"));		
		actionList.add(new Action("actionStorageEditItem","actionStorageEditItem"));
	}
	
	public void start(Object obj) {
		WizardAction result;
		
		int currentItemIndex = 0;

		Class<? extends Object> cls = obj.getClass();
		
		do {
			
			try {
				Action action = actionList.get(currentItemIndex);
				Method method = cls.getDeclaredMethod(action.getMethod(), null);
				result = (WizardAction) method.invoke(obj, null);
				
				if (result != null) {
					switch (result) {
					case BACK :
						currentItemIndex = currentItemIndex > 0 ? currentItemIndex-1 : -1; 
						break;
						
					case FORWARD :
						currentItemIndex = currentItemIndex < actionList.size()-1 ? currentItemIndex+1 : -1; 
						break;
					}
				}
				
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				result = WizardAction.EXIT;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				result = WizardAction.EXIT;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				result = WizardAction.EXIT;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				result = WizardAction.EXIT;
			}
			
			if (currentItemIndex == -1) {
				result = WizardAction.EXIT;
			}
			
		} while (result != null && result != WizardAction.EXIT);
		
		System.out.println("Terminated");
	}
	
	@SuppressWarnings("unused")
	private WizardAction actionToStorageSelect() {
		WizardAction result = null;
		
		scanner.reset();

		do {
			System.out.println("1 - select the storage");
			System.out.println("2 - exit");
			System.out.println("Please, make your choice: ");
			
			int choice = scanner.nextInt();
			
			switch (choice) {
			case 2 :
				result = WizardAction.EXIT;
				break;
				
			case 1 :
				result = WizardAction.FORWARD;
				break;
	
			default :
				System.out.println("Incorrect choice, please make the right one");
				break;
			}
			
		} while (result == null);
		
		return result;
	}
	
	
	@SuppressWarnings("unused")
	private WizardAction actionStorageSelect() {
		WizardAction result = null;
		
		scanner.reset();
		
		do {
			System.out.println("Currently the following storages are initialized: " + factory.toString());
			System.out.println("Enter the storage code, 'back' to return to prior choice or 'exit' to quit");
			System.out.println("Please, make your choice: ");
			
			String choice = scanner.next();
			
			if (choice.equals("exit"))
				result = WizardAction.EXIT;
			else if (choice.equals("back")) {
				result = WizardAction.BACK;
				if (paramsMap.containsKey("storageType"))
					paramsMap.remove("storageType");
			}
			else {
				StorageType storageType = StorageType.valueOf(choice);
				if (storageType == null)
					System.out.println("Incorrect choice, please make the right one");
				else {
					paramsMap.put("storageType", storageType);
					result = WizardAction.FORWARD;
				}
			}
			
		} while (result == null);
		
		return result;
		
	}
	
	@SuppressWarnings("unused")
	private WizardAction actionStorageEdit() throws Exception {
		WizardAction result = null;
		StorageType storageType = (StorageType)paramsMap.get("storageType");
		TVStorageInterface storage = factory.getStorage(storageType);

		scanner.reset();
		if (paramsMap.containsKey("storageItem"))
			paramsMap.remove("storageItem");

		System.out.println("Current " + storageType.name() + " storage content:");
		storage.print();
		do{
			System.out.println("Please, enter the record id to start editing or 'back' or 'exit' if you know what I mean: ");

			String choice = scanner.next();
			if (choice.equals("back")) 
				result = WizardAction.BACK;
			else if (choice.equals("exit"))
				result = WizardAction.EXIT;
			else {
				try {
					
					int id = Integer.valueOf(choice);
					
					List<TV> storageList = storage.getStorageList();
					TV resultItem = null;
					for (Iterator<TV> itr = storageList.iterator() ; itr.hasNext() ; ) {
						TV item = itr.next();
						if (item.getId() == id) {
							resultItem = item;
							break;
						}
					}
					
					if (resultItem == null) {
						throw new Exception();
					}
					else {
						paramsMap.put("storageItem", resultItem);
						result = WizardAction.FORWARD;
					}
					
				} catch (Exception e) {
					System.out.println("Incorrect id!");
					e.printStackTrace();
				}
			}
			
		} while (result == null);
		
		return result;
	}

	@SuppressWarnings("unused")
	private WizardAction actionStorageEditItem() throws Exception {
		WizardAction result = null;
		TV item = (TV)paramsMap.get("storageItem");
		System.out.println("Item to edit: " + item.toString());

		scanner.reset();
		
		do{
			System.out.println("Please, enter the pairs 'key=value' separated by comma or 'back' or 'exit' traditionally");
		
			String choice = scanner.next();
			if (choice.equals("back")) 
				result = WizardAction.BACK;
			else if (choice.equals("exit"))
				result = WizardAction.EXIT;
			else {
				
				try {
					List<String> values = Arrays.asList(choice.split(","));
					for (String pair : values) {
						String[] tokens = pair.trim().split("=");
						if (tokens.length == 2) {
							String key = tokens[0].trim();
							String value = tokens[1].trim();
							item.setByKeyValue(key, value);
						}
					}
					System.out.println("Updated item: " + item.toString());
					
					
				} catch (Exception e) {
					System.out.println("Incorrect string, please try one more time");
					e.printStackTrace();
				}
				
				
				
			}
		} while (result == null);
		
		return result;
	}
	
}
