<%@page import="com.google.gson.Gson"%>
<%@page import="com.tech.whale.message.dto.MessageRoomDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="../static/js/message/messageTable.js"></script>
<%
    List<MessageRoomDto> msgRoomList = (List<MessageRoomDto>) request.getAttribute("list");
    String jsonList = new Gson().toJson(msgRoomList); // Gson을 사용하여 JSON으로 변환
%>
<div class="table-container">
	
<input type="hidden" id="roomList" value='<%= jsonList %>' />
	<table id="msgTable">
		<c:forEach items="${list }" var="item">
			<tr id="${item.message_room_id}" onclick="goToMessageRoom('${item.message_room_id}','${item.userList}')">
				<td class="col1"><img class="profileImg"
					src="../static/images/message/test/people.png" alt="이전" /></td>
				<td class="col2">
					<ul class="chatInfo1">
						<li class="username">${item.userList }</li>
						<li class="nowMsg">${item.lastmsg_context }</li>
					</ul>
				</td>
				<td class="col3">
					<ul class="chatInfo2">
						<li class="msgTime">${item.lastmsg_date }</li>
						<li class="unread">
							<c:if test="${item.unread_msg != 0}">
								<span>${item.unread_msg }</span>
							</c:if>
						</li>
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