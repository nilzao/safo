package br.com.nils.selenium.safo.util;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AjaxWait implements IAjaxWait {

	@Override
	public void ajaxWait(RemoteWebDriver remoteWebDriver) {
		try {
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
