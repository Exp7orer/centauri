package br.com.exp7orer.centauri.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.exp7orer.centauri.repository.UserRepository;
import br.com.exp7orer.centauri.service.UserService;

@Service
public class UserServiceImplement implements UserService{

	@Autowired
	UserRepository userRepository; 
	
	
	
}
