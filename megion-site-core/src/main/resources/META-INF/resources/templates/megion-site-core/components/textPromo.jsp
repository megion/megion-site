<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:choose>
	<c:when test="${promo.useExternalURL}">
		<a class="menu-right-center" href="${promo.externalURL}">
			${promo.text} </a>
	</c:when>
	<c:otherwise>
		<a class="menu-right-center" href="<c:url value="${promo.URL}" />">
			${promo.text} </a>
	</c:otherwise>
</c:choose>
