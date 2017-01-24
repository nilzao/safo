package br.com.nils.selenium.safo.reflection;

import java.lang.reflect.Field;

public class FillEmpty {

	private Object objectTmp;

	public FillEmpty(Object objectTmp) {
		this.objectTmp = objectTmp;
		Field[] allFields = AllFieldsReflection.getAllFields(objectTmp.getClass());
		fillObject(allFields);
	}

	private void fillObject(Field[] fields) {
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

	public Object getObjectFilled() {
		return objectTmp;
	}

}
