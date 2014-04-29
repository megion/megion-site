<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	ymaps.ready(function () {
		var geoMapJson = ${geoMapJson};
		var geomap = geoMapJson.geomap;
		
		var myMap = new ymaps.Map(geomap.id, {
			center : [ geomap.centerLat, geomap.centerLon ],
			zoom : geomap.zoom,
		});
		myMap.controls.add(new ymaps.control.MapTools());
		myMap.controls.add('typeSelector');
		myMap.controls.add('zoomControl');
		myMap.controls.add('scaleLine');
		myMap.controls.add('miniMap');

		if (geomap.placemarks) {
			var placemarks = geomap.placemarks;
			for ( var i = 0; i < placemarks.length; i++) {
				var placemark = placemarks[i];
				var ymark = new ymaps.Placemark(
						[ placemark.lat, placemark.lon ], {
							hintContent : placemark.hintContent,
							balloonContent : placemark.balloonContent
						});
				myMap.geoObjects.add(ymark);
			}
		}

	});
</script>

<div class="geoInformation">
	<c:if test="${geoMap.hasTitle}">
		<h2>${geoMap.title}</h2>
	</c:if>
	<%
		String mapAreaCss = "geoMap";
		String textAreaCss = "geoText";
	%>
	<c:if test="${geoMap.canShowTextOnLeft}">
		<%
		mapAreaCss = mapAreaCss + " geoMapOnRight";
		textAreaCss = textAreaCss + " geoTextOnLeft";
		%>
	</c:if>
	<c:if test="${geoMap.canShowTextOnRight}">
		<%
		mapAreaCss = mapAreaCss + " geoMapOnLeft";
		textAreaCss = textAreaCss + " geoTextOnRight";
		%>
	</c:if>
	
	<c:if test="${geoMap.canShowTextOnLeft}">
		<div style="${geoMap.textHtmlStyle}" class="<%=textAreaCss%>">${geoMap.mapText}</div>
	</c:if>
	<div id="${geoMap.id}" style="${geoMap.mapHtmlStyle}" class="<%=mapAreaCss%>">
	</div>
	<c:if test="${geoMap.canShowTextOnRight}">
		<div style="${geoMap.textHtmlStyle}" class="<%=textAreaCss%>">${geoMap.mapText}</div>
	</c:if>
</div>
