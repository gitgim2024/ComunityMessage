<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="../static/js/message/userTable.js"></script>
<div class="table-container">
	<table>
		<c:forEach items="${list }" var="item">
			<tr id="${item}">
				<td class="col1"><img class="profileImg"
					src="../static/images/message/test/people.png" alt="이전" /></td>
				<td class="col2 username">유저A</td>
				<td class="col3"><label class="userCheckbox"> <input
						type="checkbox" class="box" data-id="${item}"> <span></span>
						<!-- 체크박스 모양을 위한 span -->
				</label></td>
			</tr>
		</c:forEach>
	</table>
</div>

<button id="confirmButton">확인</button>
