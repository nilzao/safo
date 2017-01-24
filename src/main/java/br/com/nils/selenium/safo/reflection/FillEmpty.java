package br.com.nils.selenium.safo.reflection;

import java.lang.reflect.Field;

public class FillEmpty {

	public static void fillObject(Object objectTmp) {
		Field[] allFields = AllFieldsReflection.getAllFields(objectTmp.getClass());
		fillObject(allFields, objectTmp);
	}

	private static void fillObject(Field[] fields, Object objectTmp) {
		Field.setAccessible(fields, Boolean.TRUE);
		for (Field field : fields) {
			try {
				if (field.getType().isAssignableFrom(String.class)) {
					field.set(objectTmp, "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
