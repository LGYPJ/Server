//package com.garamgaebi.GaramgaebiServer.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig {
//    private final TokenProvider tokenProvider;
//    private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web) -> web.ignoring()
//                .antMatchers("/favicon.ico");
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//
//                /**401, 403 Exception 핸들링 */
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAtuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                /**세션 사용하지 않음*/
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                /** HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
//                .and()
//                .authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
//
//                /**JwtSecurityConfig 적용 */
//                .and()
//                .apply(new JwtSecurityConfig(tokenProvider))
//
//                .and().build();
//    }
//}
