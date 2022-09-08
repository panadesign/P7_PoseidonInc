package com.nnk.springboot.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Http trace actuator configuration.
 */
@Configuration
public class HttpTraceActuatorConfiguration {

    /**
     * Http trace repository http trace repository.
     *
     * @return the http trace repository
     */
    @Bean
	public HttpTraceRepository httpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
