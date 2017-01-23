package br.com.nils.selenium.safo.example;

import java.io.Serializable;
import java.net.URL;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.nils.selenium.safo.util.AjaxWait;
import br.com.nils.selenium.safo.util.JaxbXml;
import br.com.nils.selenium.safo.util.SafoWebDriver;

public class MainExample {

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.gecko.driver", "/opt/selenium/geckodriver/geckodriver");
		FirefoxDriver firefoxDriver = new FirefoxDriver();
		firefoxDriver.get("https://mdn.mozillademos.org/en-US/docs/Web/Guide/HTML/Forms/My_first_HTML_form/Example$samples/A_simple_form?revision=1107395");
		AjaxWait ajaxWait = new AjaxWait();
		SafoWebDriver safoWebDriver = new SafoWebDriver(firefoxDriver, ajaxWait);
		Serializable loadObject = JaxbXml.loadObject("target/vai.xml");
		safoWebDriver.fillWithSerializable(loadObject);
	}

	private static void getFirefox() throws Exception {
		URL url = new URL("http://localhost:4444/wd/hub");
		DesiredCapabilities firefox = DesiredCapabilities.firefox();
		firefox.setCapability("version", "50.1.2");
		RemoteWebDriver remoteWebDriver = new RemoteWebDriver(url, firefox);
		remoteWebDriver.get("http://www.google.com");
		// remoteWebDriver.close();
		remoteWebDriver.quit();
	}

	private static void getIE() throws Exception {
		URL url = new URL("http://localhost:4444/wd/hub");
		DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
		RemoteWebDriver remoteWebDriver = new RemoteWebDriver(url, capability);
		remoteWebDriver.get("http://www.google.com");
		// remoteWebDriver.close();
		remoteWebDriver.quit();
	}

}
