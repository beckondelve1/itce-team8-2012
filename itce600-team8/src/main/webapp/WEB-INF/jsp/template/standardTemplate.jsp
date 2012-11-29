<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../template/includes.jsp" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<html>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/default.css"/> " type="text/css" />
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css" />
</head>

<body>
	<div id="header">
        <tiles:insertAttribute name="header"/>
    </div>
    
    <div id="navigation">
        <tiles:insertAttribute name='navigation'/>
    </div>

	<div id="page">
        <div id="content">
            <tiles:insertAttribute name='body'/>
        </div>
        <div id="sidebar">
            <tiles:insertAttribute name='menu'/>
        </div>
        <div style="clear: both; height: 1px;"></div>
	</div>
	
	<div id="footer">
        <tiles:insertAttribute name="footer"/>
    </div>
    
</body>

</html>
