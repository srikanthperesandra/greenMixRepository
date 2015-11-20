/**
 * 
 */
package com.msc.stuttgart.iot.greenmix.beans.service;

/**
 * @author srikanth
 *
 */
public class ItemService {

	private static ItemService itemService;
	private ItemService(){
		
	}
	public static ItemService getInstance(){
		if(itemService == null)
			itemService = new ItemService();
		return itemService;
	}
	
	public Object getService(Class<?> serviceClass){
		
	/*	System.out.println(serviceClass.getInterfaces());
		if(serviceClass.isInterface()&&serviceClass.getInterfaces()[0].getName().equals(IBeanService.class.getName())){
		*/
			if(serviceClass.getName().equals(IPlantService.class.getName()))
				return fetchInstance(PlantServiceImpl.class);
			else {
				return new Object();
			}
		/*}else
			return new Exception(serviceClass.getName()+" is not proper Bean interface");
			*/
	}
	private Object fetchInstance(Class<?> implClass) {
		// TODO Auto-generated method stub
		try {
			return implClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e;
		}
		
	}
}
