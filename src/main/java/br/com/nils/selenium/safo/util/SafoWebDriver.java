package br.com.nils.selenium.safo.util;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.nils.selenium.safo.reflection.FieldsToFill;
import br.com.nils.selenium.safo.vo.SafoComponentVO;

public class SafoWebDriver {

	private RemoteWebDriver remoteWebDriver;
	private IAjaxWait ajaxWait;

	public SafoWebDriver(RemoteWebDriver remoteWebDriver, IAjaxWait ajaxWait) {
		this.remoteWebDriver = remoteWebDriver;
		this.ajaxWait = ajaxWait;
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
		if ("select".equals(webElement.getTagName())) {
			clickSelectOption(webElement, valueToPut);
		} else {
			webElement.sendKeys(valueToPut);
		}
		if (safoComponentVO.isForceLostFocus()) {
			runOnChange(webElement);
		}
		if (safoComponentVO.isAjaxWait()) {
			ajaxWait.ajaxWait(remoteWebDriver);
		}
	}

	public void fillWithSerializable(Serializable object) {
		FieldsToFill fieldsToFill = new FieldsToFill(object);
		List<SafoComponentVO> compToFind = fieldsToFill.getSafoComponentList();
		for (SafoComponentVO componentToFindVO : compToFind) {
			fillWithSafoComp(componentToFindVO);
		}
	}

	public void runOnChange(WebElement webElement) {
		WebElement parent = webElement.findElement(By.xpath("./.."));
		parent.click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) remoteWebDriver;
		jsExecutor.executeScript("arguments[0].onchange();", webElement);
	}

	public void clickSelectOption(WebElement select, String text) {
		if (!clickSelectOptionByValue(select, text)) {
			clickSelectOptionByText(select, text);
		}
	}

	public void clickSelectOptionByText(WebElement select, String text) {
		select.click();
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (text.equals(option.getText())) {
				option.click();
				break;
			}
		}
	}

	public boolean clickSelectOptionByValue(WebElement select, String value) {
		select.click();
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (value.equals(option.getAttribute("value"))) {
				option.click();
				return true;
			}
		}
		return false;
	}

}
