package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * The type Populator config.
 */
@Profile("dev")
@Configuration
public class PopulatorConfig {
    /**
     * Gets respository populator.
     *
     * @return the respository populator
     */
    @Bean
	public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
		Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		factory.setResources(new Resource[]{new ClassPathResource("user-data.json")});
		return factory;
	}
}
