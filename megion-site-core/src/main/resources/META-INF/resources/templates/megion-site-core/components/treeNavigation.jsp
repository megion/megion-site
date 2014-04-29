<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${treeNavigation != null}">
	<div class="tree-page-navigation">
		<c:if test="${treeNavigation.hasNextNav}">
			<div class="navigation-next">
				<a href="<c:url value="${treeNavigation.nextNav.URL}" />">Следующая</a>
				<c:if test="${!treeNavigation.hidePageTitle}">
					<span class="navigation-page-title">${treeNavigation.nextNav.title}</span>
				</c:if>
			</div>
		</c:if>

		<c:if test="${treeNavigation.hasHomeNav}">
			<div class="navigation-home">
				<a href="<c:url value="${treeNavigation.homeNav.URL}" />">Главная</a>
				<c:if test="${!treeNavigation.hidePageTitle}">
					<span class="navigation-page-title">${treeNavigation.homeNav.title}</span>
				</c:if>
			</div>
		</c:if>

		<c:if test="${treeNavigation.hasPrevNav}">
			<div class="navigation-prev">
				<c:if test="${!treeNavigation.hidePageTitle}">
					<span class="navigation-page-title">${treeNavigation.prevNav.title}</span>
				</c:if>
				<a href="<c:url value="${treeNavigation.prevNav.URL}" />">Предыдущая</a>
			</div>
		</c:if>
	</div>
</c:if>
