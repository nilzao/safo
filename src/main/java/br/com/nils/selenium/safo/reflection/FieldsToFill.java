package br.com.nils.selenium.safo.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.nils.selenium.safo.annotations.SafoComponent;
import br.com.nils.selenium.safo.vo.SafoComponentVO;

public class FieldsToFill {

	private List<SafoComponentVO> compToFind = new ArrayList<>();

	public List<SafoComponentVO> getSafoComponentList() {
		return compToFind;
	}

	public FieldsToFill(Object objectTmp) {
		Field[] allFields = AllFieldsReflection.getAllFields(objectTmp.getClass());
		addCompsToFind(allFields, objectTmp);
	}

	private void addCompsToFind(Field[] fields, Object objectTmp) {
		Field.setAccessible(fields, Boolean.TRUE);
		for (Field field : fields) {
			SafoComponentVO componentToFindVO = new SafoComponentVO();
			componentToFindVO.setId(field.getName());
			Object valueToPut = null;
			boolean ignoreComponent = false;
			try {
				valueToPut = field.get(objectTmp);
				componentToFindVO.setValueToPut(valueToPut);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (field.isAnnotationPresent(SafoComponent.class)) {
				SafoComponent compToFindAnnotation = field.getAnnotation(SafoComponent.class);
				ignoreComponent = compToFindAnnotation.ignore();
				componentToFindVO.setClassName(compToFindAnnotation.className());
				String id = compToFindAnnotation.id();
				if (id != null && !id.isEmpty()) {
					componentToFindVO.setId(id);
				}
				componentToFindVO.setResultPosition(compToFindAnnotation.resultPosition());
				componentToFindVO.setXpath(compToFindAnnotation.xpath());
				componentToFindVO.setOrder(compToFindAnnotation.order());
			}
			if (!ignoreComponent) {
				compToFind.add(componentToFindVO);
			}
		}
		Collections.sort(compToFind);
	}
}
