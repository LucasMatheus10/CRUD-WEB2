package com.imd.web2.controllers;

import com.imd.web2.entities.DTO.LoginDTO;
import com.imd.web2.entities.DTO.LoginResponseDTO;
import com.imd.web2.entities.DTO.RegisterDTO;
import com.imd.web2.services.TokenService;
import com.imd.web2.usuarios.UserEntity;
import com.imd.web2.usuarios.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        var userPassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(userPassword);
        var token = tokenService.gerarToken((UserEntity)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){

        if(this.userRepository.findByLogin(registerDTO.login()) != null){
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserEntity user = new UserEntity(registerDTO.login(), encryptedPassword, registerDTO.role());

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


}