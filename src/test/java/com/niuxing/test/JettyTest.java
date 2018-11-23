package com.niuxing.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyTest {

	public static void test2() {
		Server server = new Server(8080);
		// 关联一个已经存在的上下文
//		WebAppContext context = new WebAppContext();
//		// 设置描述符位置
//		context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
//		// 设置Web内容上下文路径
//		context.setResourceBase("./src/main/webapp");
//		// 设置上下文路径
//		context.setContextPath("/springmvc");
		
		WebAppContext context = new WebAppContext("src/main/webapp", "/erp");
		context.setDefaultsDescriptor("./src/test/resources/webdefault.xml");
		context.setParentLoaderPriority(true);
		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		test2();
	}
}
