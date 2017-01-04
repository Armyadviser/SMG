package com.ge.util.db;

import java.lang.reflect.Field;

/**
 * Created by Storm_Falcon on 2016/12/21.
 *
 */
public interface ObjectMapper<E> {
	String map(E e);

	default String allMap(E e) {
		Class<?> cls = e.getClass();
		String tableName = cls.getSimpleName();
		Field[] fields = cls.getFields();
		String string = "";
		for (Field field : fields) {
			try {
				Object value = field.get(e);
				string += field.getName() + "=" + value + ",";
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}

		string = string.substring(0, string.length());

		return "insert into " + tableName + " values (" + string + ")";
	}
}
