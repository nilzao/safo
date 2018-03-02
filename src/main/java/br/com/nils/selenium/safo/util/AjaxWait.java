package br.com.nils.selenium.safo.util;

import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.nils.selenium.safo.vo.SafoComponentVO;

public class AjaxWait implements IAjaxWait {

	@Override
	public void ajaxWait(RemoteWebDriver remoteWebDriver) {
		try {
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadWait(SafoWebDriver safoWebDriver, SafoComponentVO safoComponentVO) {
		try {
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
