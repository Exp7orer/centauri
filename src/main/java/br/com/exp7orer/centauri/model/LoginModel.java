package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.Login;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class LoginModel {
    private final UsuarioModel usuarioModel;
    

    @Autowired
    public LoginModel(UsuarioModel usuarioModel,LoginRepository loginRepository) {
        this.usuarioModel = usuarioModel;
    }

    public Usuario fazLogin(String senha, String email) {
        if(senha == null || email == null){
            throw  new RuntimeException("Verifique os parametros eles não podem se nulos!");
        }
        if(senha.isBlank() || email.isBlank()){
            throw new RuntimeException("Verifique os parametros eles não podem ficar em brancos!");
        }
        return usuarioModel.buscaPorSenhaEmail(senha,email);
    }
    
    
 
    

}
