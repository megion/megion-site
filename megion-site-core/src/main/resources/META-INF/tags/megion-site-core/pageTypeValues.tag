<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="dataTypesPage" required="true" rtexprvalue="true"
	type="com.megion.site.core.model.page.DataTypesPage"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="megion-site-core" prefix="megioncore"%>

<c:forEach items="${dataTypesPage.valuesByType}" var="entry">
	<megioncore:typeValues label="${entry.key.label}"
		dataTypeValues="${entry.value}"></megioncore:typeValues>
</c:forEach>
