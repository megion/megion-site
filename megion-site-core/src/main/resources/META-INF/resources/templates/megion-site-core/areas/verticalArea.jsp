<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="verticalArea">
	<c:forEach items="${components}" var="component">
	    <div class="componentRow">
		    <cms:component content="${component}" />
		</div>
	</c:forEach>
</div>
