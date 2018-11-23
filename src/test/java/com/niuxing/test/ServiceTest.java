package com.niuxing.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.niuxing.auc.service.SysResourcesService;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {"classpath:/applicationContext.xml"} )
public class ServiceTest {

	@Autowired
	private SysResourcesService sysResourcesService;
	
	@Test
	public void test() {
		try {
			sysResourcesService.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
