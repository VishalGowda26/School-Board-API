package com.school.sba.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sba.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userRepo.findByUsername(username).map(user -> new CustomUserDetails(user))
				.orElseThrow(() -> new UsernameNotFoundException("Failed to authenticate the user"));
	}
}
