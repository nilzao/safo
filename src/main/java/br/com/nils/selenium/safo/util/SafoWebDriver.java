package br.com.nils.selenium.safo.util;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import br.com.nils.selenium.safo.reflection.FieldsToFill;
import br.com.nils.selenium.safo.vo.SafoComponentVO;

public class SafoWebDriver {

	private RemoteWebDriver remoteWebDriver;
	private IAjaxWait ajaxWait;
	private IScrollAuto scrollAuto;

	public SafoWebDriver(RemoteWebDriver remoteWebDriver, IAjaxWait ajaxWait, IScrollAuto scrollAuto) {
		this.remoteWebDriver = remoteWebDriver;
		this.ajaxWait = ajaxWait;
		this.scrollAuto = scrollAuto;
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
			try {
				webElement = remoteWebDriver.findElementById(id);
			} catch (Exception e) {
				System.out.println("can't findElementById(" + id + ") trying findElement(By.name(" + id + ")");
				System.out.println("-----");
				webElement = remoteWebDriver.findElement(By.name(id));
			}
		}
		return webElement;
	}

	public WebElement findElementBySafoCompWaiting(SafoComponentVO safoComponentVO) {
		remoteWebDriver.manage().timeouts().implicitlyWait(100L, TimeUnit.MILLISECONDS);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(remoteWebDriver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(100, TimeUnit.MILLISECONDS)
				.ignoring(StaleElementReferenceException.class);
		try {
			wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					WebElement findElementBySafoComp = findElementBySafoComp(safoComponentVO);
					if (findElementBySafoComp != null) {
						throw new WebDriverException("found it");
					}
					return findElementBySafoComp;
				}
			});
		} catch (WebDriverException e) {
			// wait ended
		}
		remoteWebDriver.manage().timeouts().implicitlyWait(5000L, TimeUnit.MILLISECONDS);
		return findElementBySafoComp(safoComponentVO);
	}

	public void fillWithSafoComp(SafoComponentVO safoComponentVO) {
		// FIXME avoid StaleElementReferenceException with components reloading page
		if (isSafoValueNull(safoComponentVO)) {
			return;
		}
		ajaxWait(safoComponentVO);
		WebElement webElement = findElementBySafoCompWaiting(safoComponentVO);
		if (!webElement.isEnabled() || !webElement.isDisplayed()) {
			return;
		}
		String tagName = webElement.getTagName();
		String valueToPut = (String) safoComponentVO.getValueToPut();
		String attributeType = webElement.getAttribute("type");
		scrollAuto.scrollAuto(remoteWebDriver, webElement);
		webElement.click();
		ajaxWait(safoComponentVO);
		try {
			if (clearBefore(webElement, safoComponentVO)) {
				return;
			}
			if (safoComponentVO.isForceMouseUp()) {
				runOnMouseUp(webElement);
				ajaxWait(safoComponentVO);
			}
			if ("select".equals(tagName)) {
				clickSelectOption(webElement, valueToPut);
			} else if (!"submit".equals(attributeType)) {
				webElement.sendKeys(valueToPut);
			}
			ajaxWait(safoComponentVO);
			if (safoComponentVO.isForceLostFocus()) {
				runOnChange(webElement);
				ajaxWait(safoComponentVO);
			}
			if (safoComponentVO.isForceBlur()) {
				runOnBlur(webElement);
				ajaxWait(safoComponentVO);
			}
			ajaxWait(safoComponentVO);
		} catch (StaleElementReferenceException e) {
			System.out.println("StaleElementReferenceException\n" + safoComponentVO.toString() + "\n");
		} catch (InvalidElementStateException e) {
			System.out.println("InvalidElementStateException\n" + safoComponentVO.toString() + "\n");
		} catch (JavascriptException e) {
			System.out.println("JavascriptException\n" + safoComponentVO.toString() + "\n");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			// e.printStackTrace()
		}

	}

	private boolean clearBefore(WebElement webElement, SafoComponentVO safoComponentVO) {
		try {
			if (safoComponentVO.isClearBefore()) {
				if ("select".equals(webElement.getTagName())) {
					Select select = new Select(webElement);
					select.selectByIndex(0);
				} else {
					webElement.clear();
				}
				ajaxWait(safoComponentVO);
				clearBefore(safoComponentVO);
				return true;
			}
		} catch (StaleElementReferenceException e) {
			// ignore
		}
		return false;
	}

	private void clearBefore(SafoComponentVO safoComponentVO) {
		ajaxWait(safoComponentVO);
		safoComponentVO.setClearBefore(false);
		fillWithSafoComp(safoComponentVO);
	}

	private void ajaxWait(SafoComponentVO safoComponentVO) {
		if (safoComponentVO.isAjaxWait()) {
			ajaxWait.ajaxWait(remoteWebDriver);
			ajaxWait.reloadWait(this, safoComponentVO);
		}
	}

	private boolean isSafoValueNull(SafoComponentVO safoComponentVO) {
		if (safoComponentVO.getValueToPut() == null) {
			return true;
		}
		return false;
	}

	public List<SafoComponentVO> getSafoList(Serializable object) {
		FieldsToFill fieldsToFill = new FieldsToFill(object);
		return fieldsToFill.getSafoComponentList();
	}

	public void fillWithSafoList(List<SafoComponentVO> safoList) {
		for (SafoComponentVO componentToFindVO : safoList) {
			ISafoIntercept safoIntercept = componentToFindVO.getSafoIntecept();
			if (safoIntercept.fill()) {
				waitForLoad();
				safoIntercept.before();
				fillWithSafoComp(componentToFindVO);
				safoIntercept.after();
			}
		}
	}

	public void fillWithSerializable(Serializable object) {
		List<SafoComponentVO> safoList = getSafoList(object);
		fillWithSafoList(safoList);
	}

	public void runOnChange(WebElement webElement) {
		WebElement parent = webElement.findElement(By.xpath("./.."));
		parent.click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) remoteWebDriver;
		jsExecutor.executeScript("arguments[0].onchange();", webElement);
	}

	public void runOnBlur(WebElement webElement) {
		WebElement parent = webElement.findElement(By.xpath("./.."));
		parent.click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) remoteWebDriver;
		jsExecutor.executeScript("$(arguments[0]).blur();", webElement);
	}

	private void runOnMouseUp(WebElement webElement) {
		WebElement parent = webElement.findElement(By.xpath("./.."));
		parent.click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) remoteWebDriver;
		jsExecutor.executeScript("$(arguments[0]).mousedown();", webElement);
		jsExecutor.executeScript("$(arguments[0]).mouseup();", webElement);
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

	public RemoteWebDriver getRemoteWebDriver() {
		return remoteWebDriver;
	}

	public void scrollAuto(WebElement webElement) {
		scrollAuto.scrollAuto(remoteWebDriver, webElement);
	}

	public void waitAjax() {
		ajaxWait.ajaxWait(remoteWebDriver);
	}

	public void waitReload(SafoComponentVO safoComponentVO) {
		ajaxWait.reloadWait(this, safoComponentVO);
	}

	public void waitForLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(remoteWebDriver, 30);
		wait.until(pageLoadCondition);
	}
}
