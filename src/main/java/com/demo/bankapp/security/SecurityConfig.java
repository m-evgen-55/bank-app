//package com.demo.bankapp.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import java.util.List;
//
////@Configuration
//@EnableWebSecurity
////@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
////    private UserDetailsService userDetailsService;
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return NoOpPasswordEncoder.getInstance();
////    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Autowired
////    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .inMemoryAuthentication()
////                .withUser("user")
////                .password("$2a$10$R4G6a5Jt7q2TKxDQKAqPpec7o.tOf7vAOqXSPgw1EqQz4W1NSsK1q") // 123456
////                .roles("admin");
////
//////        http
//////                .authorizeRequests()
//////                .antMatchers("/resources/**", "/registration").permitAll()
//////                .anyRequest().authenticated()
//////                .and()
//////                .formLogin()
//////                .loginPage("/login")
//////                .permitAll()
//////                .and()
//////                .logout()
//////                .permitAll();
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
////        http
////                .authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .httpBasic();
//
//        http.cors();
//
////        http.cors(cors -> {
////            CorsConfigurationSource cs = resources -> {
////                CorsConfiguration corsConfiguration = new CorsConfiguration();
////                corsConfiguration.setAllowedOrigins(List.of("*"));
////                corsConfiguration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE", "OPTIONS"));
//////                corsConfiguration.setAllowedHeaders(List.of("Authorization",
//////                        "Content-Type",
//////                        "X-Requested-With",
//////                        "Accept",
//////                        "X-XSRF-TOKEN"));
//////                corsConfiguration.setAllowCredentials(true);
////                return corsConfiguration;
////            };
////
////            cors.configurationSource(cs);
////        });
//    }
//
////    @Bean
////    public AuthenticationManager customAuthenticationManager() throws Exception {
////        return authenticationManager();
////    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
////    }
//}
