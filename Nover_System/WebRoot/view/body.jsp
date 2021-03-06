<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>身体</title>
    <script src="<%=path%>/script/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" href="<%=path%>/script/bootstrap/css/bootstrap.min.css">
    <script src="<%=path%>/script/bootstrap/js/bootstrap.min.js"></script>
    <style type="text/css">
        .container{width: 90%;background: #cf3;margin: 0 auto}
        .header{margin-top: 5px;}
        .content{overflow: auto;background-color: #9acfea;margin-top:20px;padding:10px 20px;}
        .content-left{height: 500px;background: #009933;width: 190px;float: left;margin-right: -190px}
        .content-right{float: right;width: 100%;}
        .content-right-child{margin-left: 200px;height: 500px;background: #a94442;}
    </style>
</head>
<body>
<div class="container">
    <div class="header">
    	<jsp:include page="header.jsp"></jsp:include>
    </div>
    <div class="content">
        <div class="content-left"></div>
        <div class="content-right">
            <div class="content-right-child"></div>
        </div>
    </div>
    <div class="footer"></div>
</div>
</body>
</html>