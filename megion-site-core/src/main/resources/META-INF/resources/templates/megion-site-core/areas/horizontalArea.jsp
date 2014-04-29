<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="mainHorizontal" style="${horizontalArea.htmlStyle}">
	<c:forEach items="${horizontalBlocks}" var="block">
		<div class="horizontalItem" style="${block.htmlStyle}">
			<cms:component content="${block.component}" />
		</div>
	</c:forEach>
</div>
