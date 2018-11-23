package com.niuxing.auc.dao;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author ds
 *
 */
public class DaoUtil {

	public DaoUtil(){}
	
	/**
	 * 根据条件修改一个实体对应的数据库记录 实体entity对象的属性设置了值会修改，未设置值的属性不会修改 id无论是否设置值都不会修改
	 * 
	 * @param entity
	 *            实体对象
	 * @param args
	 *            条件参数，例如：(entity,"skuId='a777b73bc4344cedbc1908393614ed06'",
	 *            "goodsId='a777b73bc4344cedbc1908393614ed06'")
	 *            修改goodsId='a777b73bc4344cedbc1908393614ed06' and
	 *            skuId='a777b73bc4344cedbc1908393614ed06'的记录
	 * @return hql
	 * @throws Exception
	 */
	public static String  updateEntityByCondition(Object entity, String... args)
			 {
		StringBuilder hql = new StringBuilder("update ");
		Class<? extends Object> clazz = entity.getClass();
		hql.append(clazz.getSimpleName());
		hql.append(" set ");
		Method[] methods = clazz.getMethods();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = 0;
		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("get") && !"getClass".equals(name)) {
				Object value=null;
				try {
					value = method.invoke(entity);
				} catch (Exception e) {
					e.printStackTrace();
					
				}
				if (value != null) {
					String fieldName = name.substring(3);
					if (StringUtils.isNotBlank(fieldName)
							&& fieldName.length() > 1) {
						char c = fieldName.charAt(0);
						fieldName = String.valueOf(c).toLowerCase()
								+ fieldName.substring(1);
					}
					if (!"id".equals(fieldName)) {
						count++;
						if (value instanceof Date) {
							hql.append(fieldName)
									.append("='" + sdf.format(value))
									.append("',");
						} else if (value instanceof Boolean) {
							boolean val = Boolean
									.parseBoolean(value.toString());
							hql.append(fieldName).append("=" + (val ? 1 : 0))
									.append(",");
						} else {
							hql.append(fieldName).append("='" + value)
									.append("',");
						}
					}
				}
			}
		}
		if (count == 0) {
			return "";
		}
		String substring = hql.substring(0, hql.length() - 1);
		hql = new StringBuilder(substring);
		hql.append(" where 1=1 ");
		for (String arg : args) {
			hql.append(" and ").append(arg);
		}
		return hql.toString();
	}
}
