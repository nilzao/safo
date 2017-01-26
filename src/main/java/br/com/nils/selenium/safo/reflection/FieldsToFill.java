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
			try {
				valueToPut = field.get(objectTmp);
				componentToFindVO.setValueToPut(valueToPut);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (field.isAnnotationPresent(SafoComponent.class)) {
				fillWithAnnotation(componentToFindVO, field);
			}
		}
		Collections.sort(compToFind);
	}

	private void fillWithAnnotation(SafoComponentVO componentToFindVO, Field field) {
		SafoComponent compToFindAnnotation = field.getAnnotation(SafoComponent.class);
		if (!compToFindAnnotation.ignore()) {
			String className = compToFindAnnotation.className();
			String xpath = compToFindAnnotation.xpath();
			String id = compToFindAnnotation.id();
			componentToFindVO.setClassName(className);
			componentToFindVO.setXpath(xpath);
			if (id.isEmpty() && className.isEmpty() && xpath.isEmpty()) {
				id = field.getName();
			}
			componentToFindVO.setId(id);
			componentToFindVO.setResultPosition(compToFindAnnotation.resultPosition());
			componentToFindVO.setOrder(compToFindAnnotation.order());
			componentToFindVO.setForceLostFocus(compToFindAnnotation.forceLostFocus());
			componentToFindVO.setAjaxWait(compToFindAnnotation.ajaxWait());
			componentToFindVO.setClearBefore(compToFindAnnotation.clearBefore());
			compToFind.add(componentToFindVO);
		}
	}
}
