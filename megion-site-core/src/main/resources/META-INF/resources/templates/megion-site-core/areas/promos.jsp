<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="promos">
    <c:forEach items="${components}" var="component" >
    	<div class="banner-block">
        	<cms:component content="${component}" />
        </div>
    </c:forEach>
</div>
