package com.megion.site.core.model;


/**
 * 
 * Helper for calculate width and margin horizontal block into container
 * 
 */
public class HtmlHorizontalBlockSize {

	private final int columnCount;
	private final int separator;
	private final int containerWidth;

	// вычисляемые значения
	private int width = 0;
	private int marginRight = 0;

	public HtmlHorizontalBlockSize(int columnCount, int separator, int containerWidth) {
		this.columnCount = columnCount;
		this.separator = separator;
		this.containerWidth = containerWidth;
	}

	public void calculate(int counter) {
		width = (containerWidth + separator) / columnCount - separator;
		marginRight = separator;
		if ((counter+1)%columnCount==0) {
			marginRight = 0;

			// поправка к последнему блоку
			int correction = containerWidth
					- ((columnCount * width) + (columnCount - 1) * separator);
			width = width + correction;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getMarginRight() {
		return marginRight;
	}
	
	public String getStyle() {
		return "width: " + width + "px; margin-right: " + marginRight + "px;";
	}

}
