<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- My Script -->
<script src="script/onlyNumbers.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:requestEncoding value = "UTF-8" />

<fmt:setBundle basename="com.coffee.i18n.coffeeList.messages" var="table_msgs"/>

<form name="coffeeListForm" onsubmit="true" action="CoffeeList"	method="post">
	<table class="table table-hover table-border width800 ">
		<thead>
			<tr class="table-head ">
				<td />
				<td> <fmt:message bundle="${table_msgs}" key="tableName" /> </td>
				<td class="cost"><fmt:message bundle="${table_msgs}" key="tablePrice" /></td>
				<td class="count"><fmt:message bundle="${table_msgs}" key="tableCount" /></td>
			</tr>
		</thead>
		<tbody>
			  <c:forEach var="coffee" items="${CoffeeTypeList}">
			  <tr>
			    <td align="center"><input type="checkbox" class="transform"	name="check${coffee.getId()}" id="check${coffee.getId()}"  ${coffee.getChecked()} /></td>
			    <td>${coffee.getTypeName()}</td>
			    <td>${coffee.getPrice()} TGR </td>
				<td align="center"><input type="text" class="enterNumber"name="count${coffee.getId()}" id="count${coffee.getId()}"  value="${coffee.getQuantity()}"
					onkeypress="return isNumber(event)"	maxlength="3" /></td>
			  </tr>
			  </c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="4" align="right">
					<font color="red">
						<c:forEach var="errorsCoffeeList" items="${validationErrorsCoffeeListPage}"> 
							<c:out value="${errorsCoffeeList.value}"></c:out>
						</c:forEach> 
					</font> <input type="submit"
					class="btn btn-primary" id="btnMakeOrder" name="btnMakeOrder"
					value="<fmt:message bundle="${table_msgs}" key="btnMakeOrder" />" /></td>
			</tr>
		</tfoot>
	</table>
	<font color="red">*</font> - каждая третья чашка бесплатно.
</form>