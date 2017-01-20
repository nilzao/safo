package br.com.nils.selenium.safo.util;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.nils.selenium.safo.reflection.FieldsToFill;
import br.com.nils.selenium.safo.vo.SafoComponentVO;

public class SafoWebDriver {

	private RemoteWebDriver remoteWebDriver;

	public SafoWebDriver(RemoteWebDriver remoteWebDriver) {
		this.remoteWebDriver = remoteWebDriver;
	}

	public WebElement findElementBySafoComp(SafoComponentVO safoComponentVO) {
		String xpath = safoComponentVO.getXpath();
		String className = safoComponentVO.getClassName();
		String id = safoComponentVO.getId();
		WebElement webElement = null;
		if (xpath != null && !xpath.isEmpty()) {
			webElement = remoteWebDriver.findElementByXPath(xpath);
		}
		if (className != null && !className.isEmpty()) {
			webElement = remoteWebDriver.findElementByClassName(className);
		}
		if (id != null && !id.isEmpty()) {
			webElement = remoteWebDriver.findElementById(id);
		}
		return webElement;
	}

	public void fillWithSafoComp(SafoComponentVO safoComponentVO) {
		WebElement webElement = findElementBySafoComp(safoComponentVO);
		String valueToPut = (String) safoComponentVO.getValueToPut();
		webElement.click();
		webElement.sendKeys(valueToPut);
	}

	public void fillWithSerializable(Serializable object) {
		FieldsToFill fieldsToFill = new FieldsToFill(object);
		List<SafoComponentVO> compToFind = fieldsToFill.getSafoComponentList();
		for (SafoComponentVO componentToFindVO : compToFind) {
			fillWithSafoComp(componentToFindVO);
		}
	}

}
