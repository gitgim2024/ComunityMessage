package com.tech.whale.message.global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tech.whale.message.dao.MessageDao;
import com.tech.whale.message.dto.MessageDto;
import com.tech.whale.message.dto.MessageImageDto;
import com.tech.whale.message.util.MsgStringUtil;

@Component
public class WebSocketHandler extends TextWebSocketHandler {//텍스트 메시지를 처리하는 WebSocket 핸들러
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketSession>> CLIENTS = new ConcurrentHashMap<>();
    //CLIENTS 라는 변수에 세션을 담아두기위한 맵형식의 공간
    @Autowired
	private MessageDao iDao;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	// 세션 ID와 방 ID를 기반으로 CLIENTS에 추가
        String roomId = getRoomIdFromSession(session); // 세션에서 방 ID 추출
        System.out.println("방id : "+roomId);
        CLIENTS.putIfAbsent(roomId, new ConcurrentHashMap<>());
        CLIENTS.get(roomId).put(session.getId(), session);
    }
    //사용자가 웹소켓 서버에 접속하게 되면 동작하는 메소드. 이때 WebSocketSession 값이 생성 되는데 그 값을 위에서 미리 만들어준, CLIENTS 변수에 put으로 담아줍니다.(키값은 세션의 고유값 입니다.)
    
    private String getRoomIdFromSession(WebSocketSession session) {
        String uri = session.getUri().toString();
        String query = uri.split("\\?")[1]; // 쿼리 스트링 분리
        String[] params = query.split("&"); // 여러 파라미터로 분리
        for (String param : params) {
            if (param.startsWith("roomId=")) {
                return param.substring("roomId=".length()); // "roomId=" 이후의 값 반환
            }
        }
        return null; // 방 ID가 없을 경우 null 반환
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
    }//이 코드는 웹소켓 서버접속이 끝났을때 동작하는 메소드 입니다. 이때 CLIENTS 변수에 있는 해당 세션을 제거 합니다.

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {//이 코드는 사용자의 메세지를 받게되면 동작하는 메소드
    	//CLIENT 변수에 담긴 세션값들을 가져와서 반복문으로 돌려서, 위 처럼 메세지를 발송해주면, 본인 이외의 사용자에게 메세지를 보낼 수 있는 코드가 됩니다.
    	String payload = message.getPayload(); // 클라이언트로부터 받은 메시지
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode jsonNode = objectMapper.readTree(payload); // JSON 파싱
    	
    	String userId = jsonNode.get("userId").asText(); // 유저 ID 추출
    	String textMessage = jsonNode.get("message").asText(); // 메시지 추출
    	// 클라이언트에게 보낼 메시지를 JSON 형식으로 생성
    	ObjectNode messageJson = objectMapper.createObjectNode();
    	messageJson.put("userId", userId);
    	messageJson.put("message", textMessage);
    	
    	String roomId = getRoomIdFromSession(session); // 세션에서 방 ID 추출
    	
    	//저장할 dto셋팅
    	MessageDto msgDto=new MessageDto();
    	msgDto.setMessage_context(textMessage);
    	msgDto.setMessage_room_id(Integer.parseInt(roomId));
    	msgDto.setUser_id(userId);
    	//DB저장
    	iDao.addMessage(msgDto);
    	//DB에서 불러오기
    	int message_id = msgDto.getMessage_id();
    	msgDto = iDao.getMessageById(message_id);//시간 가져오기위한 불러오기
    	MsgStringUtil stringutil=new MsgStringUtil();
    	String msgTime= stringutil.formatMessageTime(msgDto.getMessage_create_date());
    	messageJson.put("msgTime", msgTime);
    	if (!jsonNode.get("imageids").asText().equals("[]")) {//빈배열이 왔는지 확인
    		String imageids = jsonNode.get("imageids").asText(); // 이미지 추출
    		String[] imagesArray = objectMapper.readValue(imageids, String[].class);  // JSON 문자열을 String[]로 변환
    		System.out.println("메시지 아이디 : "+message_id);
    		iDao.updateImgMsgID(message_id,imagesArray);
        	messageJson.put("imagesHtml", objectMapper.writeValueAsString(imagesArray));
        	ArrayList<MessageImageDto> imageList=iDao.getImages(message_id);
        	ArrayList<String> imgUrls = new ArrayList<>();
        	for (MessageImageDto image : imageList) {
        	    imgUrls.add(image.getImg_url()); // img_url 값을 가져와서 리스트에 추가
        	}
        	// ArrayList를 JSON 문자열로 변환
        	String imgUrlsJson = objectMapper.writeValueAsString(imgUrls);
        	messageJson.put("imagesHtml", imgUrlsJson); // JSON 객체에 추가
    	}
    	// JSON 문자열로 변환
    	String jsonMessage = messageJson.toString();
//    	String id = session.getId();  //메시지를 보낸 세션 아이디
    	// 해당 방의 세션에 있는 모든 클라이언트에게 메시지 전송
        CLIENTS.get(roomId).forEach((key, clientSession) -> {
            try {
                clientSession.sendMessage(new TextMessage(jsonMessage));
            } catch (IOException e) {
                e.printStackTrace();
            }
    });

    }
}
