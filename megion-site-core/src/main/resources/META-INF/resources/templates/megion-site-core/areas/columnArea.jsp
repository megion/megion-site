<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${columnArea!=null}">
	<div class="columnArea-container">
		<c:if test="${columnArea.hasColumns}">
			<c:forEach items="${columnArea.columns}" var="column">
				<div class="column-cells" style="${column.htmlStyle}">
					<c:forEach items="${column.cells}" var="cell">
						<div class="columnCell" ${cell.attributesAsString}><cms:component
								content="${cell.component}" /></div>
					</c:forEach>
				</div>
			</c:forEach>
		</c:if>
	</div>
</c:if>
