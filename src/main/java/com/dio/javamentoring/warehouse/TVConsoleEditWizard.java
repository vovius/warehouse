package com.dio.javamentoring.warehouse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	}
	
	public void start(Object obj) {
		WizardAction result;
		
		ListIterator<Action> itr = actionList.listIterator();
		Action action = itr.next();

		do {
			Class<? extends Object> cls = obj.getClass();
			
			try {
				Class<?>[] parms = new Class<?>[0];

				Method method = cls.getDeclaredMethod(action.getMethod(), null);
				result = (WizardAction) method.invoke(obj, null);
				
				switch (result) {
				case BACK :
					action = itr.hasPrevious() ? itr.previous() : null;
					break;
					
				case FORWARD :
					action = itr.hasNext() ? itr.next() : null;
					break;
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
			
			if (action == null)
				result = WizardAction.EXIT;
			
		} while (result != WizardAction.EXIT);
		
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

}
