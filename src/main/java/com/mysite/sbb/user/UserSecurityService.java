package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // Lombok을 사용하여 final이 붙은 필드의 생성자를 자동 생성
@Service // 이 클래스가 Spring의 서비스 레이어로 동작하도록 지정
public class UserSecurityService implements UserDetailsService {

    // UserRepository는 데이터베이스와의 상호작용을 담당
    private final UserRepository userRepository;

    // Spring Security에서 사용자 인증을 처리하기 위해 호출되는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 데이터베이스에서 username을 기반으로 사용자 조회
        Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
        
        // 사용자가 존재하지 않을 경우 예외를 던짐
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("유저를 찾을 수 없음");
        }
        SiteUser siteUser = _siteUser.get();
        
        // 사용자의 권한 정보를 저장할 리스트 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // 관리자(admin) 계정일 경우 관리자 권한 부여
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            // 일반 사용자 계정일 경우 일반 사용자 권한 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        
        // Spring Security에서 사용하는 User 객체로 반환
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}

