<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="dataTypeValues" required="true" rtexprvalue="true"
	type="java.util.Collection"%>
<%@ attribute name="label" required="true" rtexprvalue="true"
	type="String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${not empty dataTypeValues}">
	<div class="tags-list">
		<span class="tag-name"><c:out value="${label}"></c:out>:</span>
		<c:forEach var="dataTypeValue" items="${dataTypeValues}"
			varStatus="status">
			<span class="tag-value"><a
				href="<c:url value="${dataTypeValue.URL}" />">${dataTypeValue.displayName}</a>
				<c:if test="${!status.last}">, </c:if></span>
		</c:forEach>
	</div>
</c:if>
