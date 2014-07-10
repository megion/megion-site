<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String localUuid = "" + System.currentTimeMillis();
%>
<div>

	<img class="overlay-min-img" src="${imgOverlay.minImage.link}" rel="#overlay-<%=localUuid%>" />

	<div class="apple_overlay black" id="overlay-<%=localUuid%>">
		<img class="overlay-main-img" src="${imgOverlay.image.link}" />

		<div class="details">
			<h2>Berlin Gustavohouse</h2>
			<p>The Gustavo House in Storkower Strasse. It was built in 1978
				and reconstructed in 1998 by the Spanish artist Gustavo.</p>
		</div>
	</div>

</div>
