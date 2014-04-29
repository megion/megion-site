package com.megion.site.core.model.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Step implements Serializable {

	private static final long serialVersionUID = 4626389126304160466L;

	private final int max;
	private final int offset;
	private int index;

	private final List<UrlParam> urlParams;
	
	public Step(int max, int offset) {
		this.max = max;
		this.offset = offset;
		urlParams = null;
	}

	public Step(int max, int offset, String paramPrefix, List<UrlParam> additionalUrlParams) {
		this.max = max;
		this.offset = offset;
		
		urlParams = new ArrayList<UrlParam>();
		urlParams.add(new UrlParam(paramPrefix + "max",
				String.valueOf(max)));
		urlParams.add(new UrlParam(paramPrefix + "offset",
				String.valueOf(offset)));
		if (additionalUrlParams != null) {
			urlParams.addAll(additionalUrlParams);
		}
	}

	public int getMax() {
		return max;
	}

	public int getOffset() {
		return offset;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public List<UrlParam> getUrlParams() {
		return urlParams;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + offset;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (max != other.max)
			return false;
		if (offset != other.offset)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Step [max=" + max + ", offset=" + offset + ", index=" + index
				+ "]";
	}

}
