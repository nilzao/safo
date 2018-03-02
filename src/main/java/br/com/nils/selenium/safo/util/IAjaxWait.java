package br.com.nils.selenium.safo.util;

import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.nils.selenium.safo.vo.SafoComponentVO;

public interface IAjaxWait {

	public void ajaxWait(RemoteWebDriver remoteWebDriver);

	public void reloadWait(SafoWebDriver safoWebDriver, SafoComponentVO safoComponentVO);

}
