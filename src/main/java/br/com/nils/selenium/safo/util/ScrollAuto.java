package br.com.nils.selenium.safo.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScrollAuto implements IScrollAuto {

	@Override
	public void scrollAuto(RemoteWebDriver remoteWebDriver, WebElement webElement) {
		((JavascriptExecutor) remoteWebDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
		// ((JavascriptExecutor) remoteWebDriver).executeScript("window.scrollBy(0,-80)", "");
	}

}
