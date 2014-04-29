<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="websitePage-list">

	<c:if test="${pagination!=null && pagination.canShowTopPaging}">
		<megioncore:pagination pagination="${pagination}" />
	</c:if>

	<c:forEach var="item" items="${items}" varStatus="status">
		<div class="websitePage-item">
			<div class="item-title">
				<a href="<c:url value="${item.URL}" />">${item.title}</a>
			</div>
			<div class="item-body">
				<c:if test="${item.hasWebsitePageSummary}">
					<div class="item-body-block">
						<span class="block-name">Описание: </span> <span
							class="block-text">${item.websitePageSummary}</span>
					</div>
				</c:if>
			</div>
		</div>
	</c:forEach>

	<c:if test="${pagination!=null && pagination.canShowBottomPaging}">
		<megioncore:pagination pagination="${pagination}" />
	</c:if>
</div>
