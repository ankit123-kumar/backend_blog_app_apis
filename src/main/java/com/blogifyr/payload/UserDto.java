package com.blogifyr.payload;

import java.util.HashSet;
import java.util.Set;

import com.blogifyr.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	
	private int id;
	
	@NotNull
	private String name;
	
	@Email
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDto> role = new HashSet<>();
}
