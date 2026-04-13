package com.datalock.datalock.authentication.service;


import com.datalock.datalock.authentication.security.request.SignUpRequest;
import com.datalock.datalock.authentication.security.request.SigninRequest;
import com.datalock.datalock.authentication.security.responses.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request);


    JwtAuthenticationResponse signin(SigninRequest request);
}
