<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String localUuid = "" + System.currentTimeMillis();
%>

<img class="overlay-min-img" src="${imgOverlay.minImage.link}"
	id="overlay-min-<%=localUuid%>" rel="#overlay-<%=localUuid%>" />

<div class="simple_overlay" id="overlay-<%=localUuid%>">
	<img class="overlay-main-img" src="${imgOverlay.image.link}" />

	<div class="details">
	</div>
</div>

<script type="text/javascript">
	var containerId = "#overlay-min-" + "<%=localUuid%>";
	console.warn("test: " + containerId);
	//var im = jQuery(containerId);
	//console.warn("img: " + im);
	jQuery(containerId).overlay();
	//console.warn("img: " + im);
</script>