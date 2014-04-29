<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="websitePage-list">
	<div class="fulltextSearchForm">
		<form method="GET" action="?" id="fulltextSearchForm"
			name="fulltextSearchForm">
			<table class="form-table">
				<tbody>
					<tr>
						<td><input name="query" type="text" class="fulltextSearch-input" value="${info.htmlEscapedFulltext}"/></td>
						<td style="padding-left: 10px;"><input type="button" value="Поиск"
							onclick="var searchStr=encodeURIComponent(document.fulltextSearchForm.query.value); location.search='?query=' + searchStr;" /></td>
					</tr>
			</tbody>
			</table>
		</form>
	</div>

	<c:if test="${items != null}">
		
		<div class="fulltextSearch-totalResultCount">Результатов: ${info.resultCount}</div>
		
		<c:if test="${pagination!=null && pagination.canShowTopPaging}">
			<megioncore:pagination pagination="${pagination}" />
		</c:if>

		<c:forEach var="item" items="${items}" varStatus="status">
			<div class="websitePage-item">
				<div class="item-title">
					<a href="<c:url value="${item.URL}" />">${item.title}</a>
				</div>
				<div class="item-body">
					<div class="item-body-block">
						<span class="block-text">${item.text}</span>
					</div>
				</div>
			</div>
		</c:forEach>

		<c:if test="${pagination!=null && pagination.canShowBottomPaging}">
			<megioncore:pagination pagination="${pagination}" />
		</c:if>
	</c:if>
</div>
