/**
 * @ClassPurpose This Class is to create the instance of properties file and can be used across the classes
 * @Scriptor Banu Pradeep
 * @ReviewedBy
 * @ModifiedBy 
 * @LastDateModified 3/17/2017
 */
package com.pc.utilities;


public class ThreadCache {
	
	private static ThreadCache instance = new ThreadCache();
	
	public static ThreadCache getInstance()
	{
		return instance;
	}
    ThreadLocal<ThreadCacheManager> tc = new ThreadLocal<ThreadCacheManager>();

			   
   	public ThreadCacheManager getThreadCacheManager() 
   	{
   		return tc.get(); 
   	}

    public void setThreadCacheManager(ThreadCacheManager tm)
    {
    	tc.set(tm);
    }

}
