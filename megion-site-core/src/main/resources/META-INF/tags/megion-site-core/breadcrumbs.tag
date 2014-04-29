<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="breadcrumbs" required="true" rtexprvalue="true"
	type="com.megion.site.core.model.navigation.BreadcrumbsNavigation"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${breadcrumbs != null}">
	<div class="breadcrumbs-navigation">
		<c:forEach var="nav" items="${breadcrumbs.navigations}"
			varStatus="status">
			<c:if test="${!status.last}">
				<span class="nav-link">
                    <c:choose>
                        <c:when test="${nav.notLink}">
                            <span>${nav.title}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="${nav.URL}"/>">${nav.title}</a>
                        </c:otherwise>
                    </c:choose>
				</span>
				<span class="nav-spacer">|</span>
			</c:if>
			<c:if test="${status.last}">
				<span class="nav-current"> ${nav.title} </span>
			</c:if>
		</c:forEach>
	</div>
</c:if>

