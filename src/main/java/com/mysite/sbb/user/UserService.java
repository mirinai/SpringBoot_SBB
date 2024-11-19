package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	// PasswordEncoder를 의존성 주입받습니다. 이를 통해 비밀번호를 안전하게 암호화할 수 있습니다.

	public SiteUser create(String username, String email, String password) {
		// 새로운 사용자를 생성하는 메서드입니다.
		
		SiteUser user = new SiteUser();
		// SiteUser 객체를 생성합니다.

		user.setUsername(username);
		// 전달받은 username을 SiteUser 객체에 설정합니다.

		user.setEmail(email);
		// 전달받은 email을 SiteUser 객체에 설정합니다.

//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		주석 처리된 기존 코드입니다. 직접 객체를 생성하지 않고 의존성 주입을 통해 PasswordEncoder를 사용하도록 변경되었습니다.

//		user.setPassword(password);
//		주석 처리된 기존 코드입니다. 비밀번호를 평문으로 설정하는 것은 보안에 취약하기 때문에 수정되었습니다.

		user.setPassword(passwordEncoder.encode(password));
		// 의존성 주입받은 PasswordEncoder를 사용하여 비밀번호를 암호화한 후 설정합니다.

		this.userRepository.save(user);
		// 사용자 정보를 데이터베이스에 저장합니다. JPA의 save 메서드를 통해 실행됩니다.

		return user;
		// 저장된 사용자 객체를 반환합니다.
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}
		else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
}
