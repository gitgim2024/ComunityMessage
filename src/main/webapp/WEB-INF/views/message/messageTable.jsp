<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="../static/js/message/messageTable.js"></script>

<div class="table-container">
	<table id="msgTable">
		<c:forEach items="${list }" var="item">
			<tr id="${item}">
				<td class="col1"><img class="profileImg"
					src="../static/images/message/test/people.png" alt="이전" /></td>
				<td class="col2">
					<ul id="chatInfo1">
						<li class="username">유저${item }</li>
						<li id="nowMsg">추석 잘
							보내셨나요?ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</li>
					</ul>
				</td>
				<td class="col3">
					<ul id="chatInfo2">
						<li class="msgTime">오전 9:27</li>
						<li class="unread"><span>200</span></li>
					</ul>
				</td>
				<td class="col4"><img class="dotImg"
					src="../static/images/message/dot.png" alt="더보기" /></td>
			</tr>
		</c:forEach>
	</table>

	<!-- 모달 창 -->
	<div class="modal" id="room-set" style="display: none;">
		<div class="modal-content">
			<p>선택된 유저<span class="close" id="closeModal">&times;</span></p>
			<button id="markAsRead">읽음</button>
			<button id="leaveChat">나가기</button>
		</div>
	</div>

</div>