package com.cristian.ticket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cristian.ticket.business.domain.security.JwtUtil;

@SpringBootTest
class TicketApplicationTests {

	@MockitoBean
	private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
	}

}
