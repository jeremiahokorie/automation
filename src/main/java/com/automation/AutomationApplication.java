package com.automation;

import com.automation.core.global.model.Permission;
import com.automation.core.global.model.Roles;
import com.automation.core.global.repository.PermissionRepository;
import com.automation.core.global.repository.RoleRepository;
import com.automation.core.global.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;

import java.util.Set;

@EnableMethodSecurity
@SpringBootApplication
public class AutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationApplication.class, args);
	}
}
