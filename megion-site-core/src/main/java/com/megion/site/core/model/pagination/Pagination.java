package com.megion.site.core.model.pagination;

import java.io.Serializable;
import java.util.List;

import com.megion.site.core.enums.PagingPositionType;

public class Pagination implements Serializable {

	private static final long serialVersionUID = -2644456388429921731L;

	private String path;
	private Step prevLink;
	private Step currentLink;
	private Step nextLink;
	private PagingPositionType pagingPosition;

	private List<Step> stepLinks;

	public Step getPrevLink() {
		return prevLink;
	}

	public void setPrevLink(Step prevLink) {
		this.prevLink = prevLink;
	}

	public Step getCurrentLink() {
		return currentLink;
	}

	public void setCurrentLink(Step currentLink) {
		this.currentLink = currentLink;
	}

	public Step getNextLink() {
		return nextLink;
	}

	public void setNextLink(Step nextLink) {
		this.nextLink = nextLink;
	}

	public List<Step> getStepLinks() {
		return stepLinks;
	}

	public void setStepLinks(List<Step> stepLinks) {
		this.stepLinks = stepLinks;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public PagingPositionType getPagingPosition() {
		return pagingPosition;
	}

	public void setPagingPosition(PagingPositionType pagingPosition) {
		this.pagingPosition = pagingPosition;
	}
	
	public boolean isCanShowTopPaging() {
		return pagingPosition!=null && (PagingPositionType.TOP.equals(pagingPosition) || PagingPositionType.TOP_AND_BOTTOM.equals(pagingPosition));
	}
	
	public boolean isCanShowBottomPaging() {
		return pagingPosition==null || (PagingPositionType.BOTTOM.equals(pagingPosition) || PagingPositionType.TOP_AND_BOTTOM.equals(pagingPosition));
	}

}
