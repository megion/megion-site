<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${contactForm.hasTitle}">
	<h3>${contactForm.title}</h3>
</c:if>

<c:if test="${errorMessage!=null}">
	<div class="errorMessage">${errorMessage}</div>
</c:if>

<div class="contactForm">
	<form:form commandName="contactFormNotification" method="POST"
		action="?">
		<blossom:pecid-input />
		<span class="form-input-name">Имя, фамилия</span>
		<span class="required-marker">*</span>
		<br />
		<form:input path="name" cssClass="textinput" />&nbsp;<span
			class="form-input-error"><form:errors path="name" /></span>
		<br />
		<span class="form-input-name">Ваш адрес E-mail</span>
		<span class="required-marker">*</span>
		<br />
		<form:input path="email" cssClass="textinput" />&nbsp;<span
			class="form-input-error"><form:errors path="email" /></span>
		<br />
		<span class="form-input-name">Тема запроса</span>
		<br />
		<form:input path="messageTitle" cssClass="textinput" />&nbsp;<span
			class="form-input-error"><form:errors path="messageTitle" /></span>
		<br />
		<span class="form-input-name">Текст запроса</span>
		<span class="required-marker">*</span>&nbsp;<span
			class="form-input-error"><form:errors path="message" /></span>
		<br />
		<form:textarea path="message" cols="60" rows="5" cssClass="textinput" />
		<br />
		<br />

		<div class="captcha-label">Введите символы:</div>
		<div style="margin: 0 0 0.5em">
			<span class="form-input-error"><form:errors path="captchaText" />&nbsp;</span>
		</div>
		<div class="captcha-content">
			<div class="captcha-element">
				<c:if test="${getCaptchaResult!=null}">
					<form:input path="captchaKey" type="hidden"
						value="${getCaptchaResult.captcha}" />
					<img src="${getCaptchaResult.url}" />
				</c:if>
			</div>
			<div class="captcha-element" style="width: 10px;">
			</div>
			<div class="captcha-element">
				<form:input path="captchaText" cssClass="captcha-input" autocomplete="off" />
				<c:if test="${checkSpamResult!=null}">
					<form:input path="checkSpamId" type="hidden"
						value="${checkSpamResult.id}" />
				</c:if>
			</div>
			
		</div>



		<input type="submit" value="Отправить" />

	</form:form>
</div>
