<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="pagination" required="true" rtexprvalue="true"
	type="com.megion.site.core.model.pagination.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:if test="${pagination != null}">
	<div class="pagination">
		<span class="pagination-title">Страницы </span>
		
		<c:choose>
			<c:when test="${pagination.prevLink != null}">
				<c:url var="prevUrl" value="${pagination.path}">
					<c:forEach var="paramUrl" items="${pagination.prevLink.urlParams}">
						<c:param name="${paramUrl.name}" value="${paramUrl.value}" />	
					</c:forEach>
				</c:url>
				<a class="pagination-prev" href="${prevUrl}">Предыдущая</a>
			</c:when>
			<c:otherwise>
				<a class="pagination-prev pagination-hidden" href="">Предыдущая</a>
			</c:otherwise>
		</c:choose>

		<c:forEach var="step" items="${pagination.stepLinks}"
			varStatus="status">
			<c:url var="stepUrl" value="${pagination.path}">	
				<c:forEach var="paramUrl" items="${step.urlParams}">
					<c:param name="${paramUrl.name}" value="${paramUrl.value}" />	
				</c:forEach>
			</c:url>
			<a class="${pagination.currentLink==step?'pagination-active':''}"
				href="${stepUrl}">${step.index}</a>

		</c:forEach>

		<c:choose>
			<c:when test="${pagination.nextLink != null}">
				<c:url var="nextUrl" value="${pagination.path}">
					<c:forEach var="paramUrl" items="${pagination.nextLink.urlParams}">
						<c:param name="${paramUrl.name}" value="${paramUrl.value}" />	
					</c:forEach>
				</c:url>
				<a class="pagination-next" href="${nextUrl}">Следующая</a>
			</c:when>
			<c:otherwise>
				<a class="pagination-next pagination-hidden" href="">Следующая</a>
			</c:otherwise>
		</c:choose>

	</div>
</c:if>