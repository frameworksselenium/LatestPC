/**
 * @ClassPurpose This Class is to create the instance of properties file and can be used across the classes
 * @Scriptor Banu Pradeep
 * @ReviewedBy
 * @ModifiedBy 
 * @LastDateModified 3/17/2017
 */
package com.pc.utilities;

import java.util.HashMap;

public class  ThreadCacheManager {
	
	
	HashMap<String,String> mh = new HashMap<String,String>(); 

	 public void setObject(String key, String value)
		{
		 	mh.put(key, value);
		}
    public String getObject(String ff)
	{
		String retuValue = null;
		if(mh.containsKey(ff))
		{
		  retuValue = mh.get(ff);
		}
		return retuValue;
	}
    
}
