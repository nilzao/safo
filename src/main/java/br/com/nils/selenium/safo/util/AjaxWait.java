package br.com.nils.selenium.safo.util;

public class AjaxWait implements IAjaxWait {

	@Override
	public void ajaxWait() {
		try {
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
