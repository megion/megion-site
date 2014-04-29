<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${contactForm.hasSuccessTitle}">
	<div class="successMessage">${contactForm.successTitle}</div>
</c:if>

<c:if test="${contactForm.hasSuccessText}">
	${contactForm.successText}
</c:if>
