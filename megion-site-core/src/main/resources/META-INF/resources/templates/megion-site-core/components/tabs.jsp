<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="materials-tabs-menu">
	<ul>
		<%
		int counter = 1;
		%>
		<c:forEach var="item" items="${items}" varStatus="status">
			<%
				String tabCssClass = "";
				
				if (counter%2==0) {
					tabCssClass = "even";
				} else {
					tabCssClass = "odd";
				}
			%>
			<c:if test="${item.selected}">
				<%
					tabCssClass = tabCssClass + " materials-tabs-active";
				%>
			</c:if>
			<li>
				<a class="<%=tabCssClass %>" href="<c:url value="${item.URL}" />"><span>${item.title}</span></a>
			</li>
			<%
				counter++;
			%>
		</c:forEach>
	</ul>
	<div class="clear"></div>
</div>
