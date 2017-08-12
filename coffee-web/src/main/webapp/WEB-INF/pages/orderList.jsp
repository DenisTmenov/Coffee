<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:requestEncoding value="UTF-8" />

<fmt:setBundle basename="com.coffee.i18n.orderList.messagesAddress" var="tableAddress_msgs" />
<fmt:setBundle basename="com.coffee.i18n.orderList.messagesOrder" var="tableOrder_msgs"/>
<fmt:setBundle basename="com.coffee.i18n.orderList.messagesLink" var="link_msgs"/>

<form name="orderListform" onsubmit="true" action="Order" method="post">
	<table class="table table-striped table-border width500 ">
		<thead class="table-head">
			<tr>
				<td colspan="2"><fmt:message bundle="${tableAddress_msgs}" key="tableHead" /></td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><b><fmt:message bundle="${tableAddress_msgs}" key="tableFullName" /></b></td>
				<td><input type="text" class="text" name="fullname"
					id="fullname" /></td>
			</tr>
			<tr>
				<td><b><fmt:message bundle="${tableAddress_msgs}" key="tableAddress" /></b></td>
				<td><input type="text" class="text" name="contactAddress"
					id="contactAddress" /></td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2" align="right"><input type="submit" name="btnOrderOK"
					id="btnOrderOK" class="btn btn-primary" value="<fmt:message bundle="${tableAddress_msgs}" key="btnOrder" />"
					 /></td>
			</tr>
		</tfoot>
	</table>
</form>

<br />

<table class="table table-striped table-border width500 ">
	<thead class="table-head">
		<tr>
			<td><fmt:message bundle="${tableOrder_msgs}" key="tableHeadTitle" /></td>
			<td><fmt:message bundle="${tableOrder_msgs}" key="tableHeadCoast" /></td>
			<td><fmt:message bundle="${tableOrder_msgs}" key="tableHeadQuantity" /></td>
			<td><fmt:message bundle="${tableOrder_msgs}" key="tableHeadTotal" /></td>
		</tr>
	</thead>
	<tbody>
		
		<c:forEach var="choice" items="${UserChoice}">
		<tr>
			<td>${choice.getTypeName()}</td>
			<td>${choice.getPrice()}</td>
			<td>${choice.getCount()}</td>
			<td>${choice.getTotalPrice()} TGR</td>
			
		</tr>
			  </c:forEach>
		<tr>
			<td colspan="3"><b><fmt:message bundle="${tableOrder_msgs}" key="tableBodyTotal" />:</b></td>
			<td>${sumCost} TGR</td>
		</tr>
		<tr>
			<td colspan="3"><b><fmt:message bundle="${tableOrder_msgs}" key="tableBodyDelivery" />:</b></td>
			<td>${transport} TGR</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="3"><b><fmt:message bundle="${tableOrder_msgs}" key="tableBodyTotal" />:</b></td>
			<td>${totalCost} TGR</td>
		</tr>
	</tfoot>

</table>

<br />
<a href="index.jsp"><fmt:message bundle="${link_msgs}" key="linkGoHome" /></a>