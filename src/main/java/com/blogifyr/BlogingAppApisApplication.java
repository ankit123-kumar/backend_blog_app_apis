package com.blogifyr;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogifyr.entities.Role;
import com.blogifyr.helper.AppConstants;
import com.blogifyr.repositories.RoleRepo;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class BlogingAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogingAppApisApplication.class, args);
	}

	@Bean
	 ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

	
	
	
	 public void run(String... args) throws Exception {
	 
System.out.println(this.passwordEncoder.encode("1234")); 

	 try {
		
		 Role role  = new Role();
		 role.setId(AppConstants.NORMAL_USER);
		 role.setName("NORMAL_USER");
		 
		 Role role1 = new Role();
		 role1.setId(AppConstants.ADMIN_USER);
		 role1.setName("ADMIN_USER");
		 
         List<Role> roles  =  List.of(role,role1);
          List<Role> result =  this.roleRepo.saveAll(roles);
          
          result.forEach(r->{
        	 
        	  System.out.println(r.getName());
          });
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	 
	 }
	 
}
