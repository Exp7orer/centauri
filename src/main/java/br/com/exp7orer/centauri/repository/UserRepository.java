package br.com.exp7orer.centauri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exp7orer.centauri.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
