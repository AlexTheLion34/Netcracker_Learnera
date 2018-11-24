package com.netcracker.learnera;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.learnera.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

//    @Bean
//    public ApiBasicAuthenticationEntryPoint apiAwareLoginUrlAuthenticationEntryPoint() {
//        ApiBasicAuthenticationEntryPoint entryPoint = new ApiBasicAuthenticationEntryPoint();
//        entryPoint.setRealmName("Api Server");
//        return entryPoint;
//    }

//    public static class ApiBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
//
//        @Override
//        public void commence(HttpServletRequest request, HttpServletResponse response,
//                             AuthenticationException authException) throws IOException, ServletException {
//            response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            //response.setContentType("");
//            PrintWriter writer = response.getWriter();
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(writer, ApiDataGenerator.buildResult(
//                    ErrorCode.AUTHORIZATION_REQUIRED, "Authorization failed"));
//        }
//
//    }
}