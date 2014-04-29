<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://magnolia-cms.com/taglib/templating-components/cms" prefix="cms" %>
<%@ taglib uri="cms-taglib" prefix="cmsold" %>
<%@ taglib uri="blossom-taglib" prefix="blossom" %>
<%@ taglib uri="megion-site-core" prefix="megioncore" %>

<%@ page import="com.megion.site.core.model.*"%>
<%@ page import="com.megion.site.core.model.navigation.*"%>
<%@ page import="com.megion.site.core.model.pagination.*"%>
<%@ page import="com.megion.site.core.enums.PagingPositionType"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="info.magnolia.jcr.util.ContentMap" %>

