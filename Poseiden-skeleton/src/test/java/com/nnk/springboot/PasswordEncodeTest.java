package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PasswordEncodeTest {
	@Test
	public void testPassword() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String pw = encoder.encode("123456");
		System.out.println("[ " + pw + " ]");
	}
}
