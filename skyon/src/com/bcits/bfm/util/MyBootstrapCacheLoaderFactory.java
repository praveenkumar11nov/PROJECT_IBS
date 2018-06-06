package com.bcits.bfm.util;
import java.util.List;
import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.ldap.ModuleDetails;
import com.bcits.bfm.model.Ldap;
import com.bcits.bfm.model.TreeViewItem;
import com.googlecode.ehcache.annotations.CacheException;

@SuppressWarnings("rawtypes")
public class MyBootstrapCacheLoaderFactory extends BootstrapCacheLoaderFactory implements BootstrapCacheLoader{
	
	@Autowired
	LdapBusinessModel ldapbussinessmodel;		
	@Autowired
	CacheManager cacheManager;
	
	static Logger logger = LoggerFactory.getLogger(MyBootstrapCacheLoaderFactory.class);		
	
	private List<JSONObject> memberWithDesciption;
	private List<ModuleDetails> memberWithDesciptionMenu;	

	public List<JSONObject> getMemberWithDesciption() {
		return memberWithDesciption;
	}

	public void setMemberWithDesciption(List<JSONObject> memberWithDesciption) {
		this.memberWithDesciption = memberWithDesciption;
	}
	
	public List<ModuleDetails> getMemberWithDesciptionMenu() {
		return memberWithDesciptionMenu;
	}

	public void setMemberWithDesciptionMenu(List<ModuleDetails> memberWithDesciptionMenu2) {
		this.memberWithDesciptionMenu = memberWithDesciptionMenu2;
	}	
	
	public LdapBusinessModel getLdapbussinessmodel() {
		return ldapbussinessmodel;
	}

	public void setLdapbussinessmodel(LdapBusinessModel ldapbussinessmodel) {
		this.ldapbussinessmodel = ldapbussinessmodel;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void load(Ehcache myCache) throws CacheException { 
		logger.info("load your cache with whatever you want...."); 		

		try { 
			logger.info("I am in Ehcache Try Loading LDAP Configurations.........Please Wait.....");		
		
			setMemberWithDesciption(ldapbussinessmodel.getMemberAttributes());			
			setMemberWithDesciptionMenu(ldapbussinessmodel.getMemberAttributesForMenu());
			
			
			
		} catch (Exception e) { 
			logger.info("I am in Ehcache Catch...Loading Failed !");
			e.printStackTrace(); 
		} 
		logger.info("load complete!"); 
	
	}

	@Override
	public boolean isAsynchronous() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BootstrapCacheLoader createBootstrapCacheLoader(Properties arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public Object clone() throws CloneNotSupportedException {
 		return null;
 	}
	
} 
	
	
