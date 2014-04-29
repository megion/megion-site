package com.megion.site.core.model.navigation;

public class TreeNavigation {

	private NavNode prevNav;
	private final NavNode homeNav;
	private NavNode nextNav;

	private boolean isFound;

	private boolean isHidePageTitle;

	public TreeNavigation(NavNode homeNav) {
		this.homeNav = homeNav;
	}

	public NavNode getPrevNav() {
		return prevNav;
	}

	public void setPrevNav(NavNode prevNav) {
		this.prevNav = prevNav;
	}

	public NavNode getHomeNav() {
		return homeNav;
	}

	public NavNode getNextNav() {
		return nextNav;
	}

	public void setNextNav(NavNode nextNav) {
		this.nextNav = nextNav;
	}

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	public boolean isHasNextNav() {
		return (nextNav != null);
	}

	public boolean isHasPrevNav() {
		return (prevNav != null);
	}

	public boolean isHasHomeNav() {
		return (homeNav != null) && (prevNav != null);
	}

	public boolean isHidePageTitle() {
		return isHidePageTitle;
	}

	public void setHidePageTitle(boolean isHidePageTitle) {
		this.isHidePageTitle = isHidePageTitle;
	}

}
