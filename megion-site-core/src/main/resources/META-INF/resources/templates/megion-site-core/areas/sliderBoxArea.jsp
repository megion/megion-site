<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.megion.site.core.model.areas.slider.*"%>
<%
String localUuid = "" +  System.currentTimeMillis();
SliderBoxArea sliderBox = (SliderBoxArea) request.getAttribute("sliderArea");
%>

<div class="clear-table" id="slider-box-<%=localUuid%>">
	<div class="container">
		<table class="list">
			<tr>
				<c:forEach items="${sliderArea.components}" var="component">
					<td class="item">
						<!--  -->
						<div class="item-container">
							<cms:component content="${component}" />
						</div>
					</td>
				</c:forEach>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
    var localId = "#slider-box-" + "<%=localUuid%>";
    var minWidth = <%=sliderBox.getItemMinWidth()%>;
    var disablePopupInfo = <%=sliderBox.getDisabelPopupInfo()%>;
	jQuery(localId).sliderBox({
		scrollable: true,
		'min-width': minWidth,
		enablePopupInfo: !disablePopupInfo
	});
</script>

