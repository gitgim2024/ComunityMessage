package com.tech.whale.message.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private final WebSocketHandler webSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/msgroom").setAllowedOrigins("*");// 실제로 테스트해볼때 정해지는 url. *은 웹소켓 cors
																						// 정책으로 인해, 허용 도메인을 지정
	}

	// 포트 번호, 세션 타임아웃, SSL 설정 등을 정의할 수 있음
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(10000); // 텍스트 메세지의 최대 크기를 정해준다. (오류 방지용) 글자1000자는 2000바이트이나, 이미지 링크가 첨부될걸
														// 고려함
		container.setMaxBinaryMessageBufferSize(10000); // 바이너리 메세지의 최대크기를 정해준다. (오류 방지용)
		return container;
	}

}
