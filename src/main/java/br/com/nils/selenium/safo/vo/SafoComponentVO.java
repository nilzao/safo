package br.com.nils.selenium.safo.vo;

public class SafoComponentVO implements Comparable<SafoComponentVO> {

	private String id;
	private String xpath;
	private String className;
	private int resultPosition;
	private int order = 999;
	private Object valueToPut;
	private boolean forceLostFocus;
	private boolean forceBlur;
	private boolean ajaxWait;
	private boolean clearBefore;
	private boolean forceMouseUp;

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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isForceLostFocus() {
		return forceLostFocus;
	}

	public void setForceLostFocus(boolean forceLostFocus) {
		this.forceLostFocus = forceLostFocus;
	}

	public boolean isAjaxWait() {
		return ajaxWait;
	}

	public void setAjaxWait(boolean ajaxWait) {
		this.ajaxWait = ajaxWait;
	}

	public boolean isClearBefore() {
		return clearBefore;
	}

	public void setClearBefore(boolean clearBefore) {
		this.clearBefore = clearBefore;
	}

	public boolean isForceBlur() {
		return forceBlur;
	}

	public void setForceBlur(boolean forceBlur) {
		this.forceBlur = forceBlur;
	}

	public boolean isForceMouseUp() {
		return forceMouseUp;
	}

	public void setForceMouseUp(boolean forceMouseUp) {
		this.forceMouseUp = forceMouseUp;
	}

	@Override
	public String toString() {
		return "id: [" + id + "], xpath: [" + xpath + "], className: [" + className + "], resultPosition: [" + resultPosition + " order: " + order + "]" + "] valueToPut: ["
				+ valueToPut + "]";
	}

	@Override
	public int compareTo(SafoComponentVO o) {
		if (this.order >= o.getOrder()) {
			return 1;
		}
		return -1;
	}

}
