package com.mysite.sbb;

// Spring Framework의 어노테이션 및 보안 관련 클래스들을 임포트
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냄
@EnableWebSecurity // Spring Security의 웹 보안 지원을 활성화함
@EnableMethodSecurity(prePostEnabled = true)// 메서드 보안을 활성화하고 @PreAuthorize, @PostAuthorize를 사용할 수 있도록 설정.
public class SecurityConfig {

	@Bean // 이 메서드가 반환하는 객체를 Spring의 Bean으로 등록함
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// HttpSecurity 객체를 사용하여 웹 보안 설정을 구성
		http
				// URL별 접근 권한 제어
				.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
						// 모든 요청에 대해 접근을 허용함
						.requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // 모든 URL에 접근 허용
				).csrf((csrf) -> csrf
						// CSRF 보호를 비활성화할 경로를 지정함
						.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
				.headers((headers) -> headers
						// 프레임 옵션 헤더를 설정하여 Clickjacking 공격을 방지함
						.addHeaderWriter(new XFrameOptionsHeaderWriter(
								// SAMEORIGIN 모드를 사용하여 같은 출처에서만 프레임을 허용함
								XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
				.formLogin((formLogin) -> formLogin
						// 로그인 페이지 경로
						.loginPage("/user/login")
						// 로그인 성공 후 리디렉션 경로
						.defaultSuccessUrl("/"))
				.logout((logout) -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true))

		;

		// 설정된 보안 필터 체인을 빌드하여 반환
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		// Spring Bean으로 등록되는 메서드입니다.
		// PasswordEncoder 인터페이스의 구현체인 BCryptPasswordEncoder를 반환합니다.
		// BCryptPasswordEncoder는 비밀번호를 해싱(암호화)하는 데 사용됩니다.
		// BCrypt는 해싱 과정에서 랜덤한 솔트를 추가하여 동일한 비밀번호라도 항상 다른 해시 값을 생성합니다.
		// 이렇게 하면 데이터베이스가 유출되더라도 해시된 비밀번호를 역으로 추적하기 어렵습니다.
		return new BCryptPasswordEncoder();
	}

	@Bean // 이 메서드의 반환값을 Spring Bean으로 등록
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
	        throws Exception {
	    // Spring Security의 AuthenticationManager를 반환
	    // AuthenticationManager는 사용자 인증을 처리하는 주요 인터페이스로,
	    // AuthenticationConfiguration 객체를 통해 기본 인증 매니저를 가져옴
	    return authenticationConfiguration.getAuthenticationManager();
	}

}
