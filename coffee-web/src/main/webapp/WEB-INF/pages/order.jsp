<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:requestEncoding value = "UTF-8" />

<fmt:setBundle basename="com.coffee.i18n.order.messages" var="msgs"/>
<fmt:setBundle basename="com.coffee.i18n.order.messagesLink" var="link_msgs"/>

<fmt:message bundle="${msgs}" key="confirmation" />
<br />
<br />
<fmt:message bundle="${msgs}" key="orderIsActivated" />
<br />
<br />
<a href="index.jsp"><fmt:message bundle="${link_msgs}" key="linkGoHome" /></a>