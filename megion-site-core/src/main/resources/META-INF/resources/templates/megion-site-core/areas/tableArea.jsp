<%@ include file="../includes/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:if test="${tableArea!=null}">
	<div class="tableArea-container">
		<c:if test="${tableArea.hasRows}">
			<table class="area-table">
				<tbody>
					<c:forEach items="${tableArea.rows}" var="row">
						<tr>
							<c:forEach items="${row.cells}" var="cell">
								<td ${cell.attributesAsString}><cms:component
										content="${cell.component}" /></td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</c:if>
