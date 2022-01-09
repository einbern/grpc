package com.example.grpc.security;

import org.lognet.springboot.grpc.security.GrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GrpcSecurityConfiguration extends GrpcSecurityConfigurerAdapter{

    @Override
    public void configure(GrpcSecurity builder) throws Exception {
        log.info("configure grpc security configuration");

        builder.authorizeRequests().anyMethod().authenticated().and().authenticationProvider(new AuthenticationProvider() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                var credential = (String)authentication.getCredentials();
                log.info("authenticate with credential = {}", credential);

                if ("1234".equals(credential)) {
                    authentication.setAuthenticated(true);
                }
                return authentication;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return BearerTokenAuthenticationToken.class.equals(authentication);
            }
            
        });


    }
    
}
