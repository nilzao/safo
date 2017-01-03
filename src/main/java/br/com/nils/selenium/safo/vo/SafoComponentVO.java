package br.com.nils.selenium.safo.vo;

public class SafoComponentVO {

	private String id;
	private String xpath;
	private String className;
	private int resultPosition;
	private Object valueToPut;

	public Object getValueToPut() {
		return valueToPut;
	}

	public void setValueToPut(Object valueToPut) {
		this.valueToPut = valueToPut;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getResultPosition() {
		return resultPosition;
	}

	public void setResultPosition(int resultPosition) {
		this.resultPosition = resultPosition;
	}

	public String toString() {
		return "id: [" + id + "], xpath: [" + xpath + "], className: [" + className + "], resultPosition: [" + resultPosition + "] valueToPut: [" + valueToPut
				+ "]";
	}

}
