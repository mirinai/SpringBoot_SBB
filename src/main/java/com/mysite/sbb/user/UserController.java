package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	// UserService를 의존성 주입받습니다. 사용자 생성 및 관련 비즈니스 로직을 처리하는 서비스입니다.

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		// GET 요청으로 "/user/signup" URL에 접근하면 회원가입 폼을 반환합니다.
		return "signup_form";
		// "signup_form"은 회원가입 화면을 나타내는 뷰 템플릿(HTML/JSP 등)입니다.
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		// POST 요청으로 "/user/signup" URL에 접근하면 회원가입 데이터를 처리합니다.
		// @Valid: UserCreateForm 객체의 유효성을 검사합니다.
		// BindingResult: 유효성 검사 결과를 담는 객체입니다.

		if (bindingResult.hasErrors()) {
			// 유효성 검사에서 오류가 발생하면 회원가입 폼을 다시 표시합니다.
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			// 입력된 두 비밀번호가 동일하지 않으면 에러 메시지를 추가합니다.
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 같지 않습니다.");
			// rejectValue: 특정 필드에 대해 사용자 정의 에러 메시지를 설정합니다.|필드이름, 오류 코드, 오류 메시지|
			return "signup_form";
		}
		
		try {
		    // 사용자가 입력한 username, email, password를 UserService의 create 메서드에 전달하여
		    // 새로운 사용자를 생성합니다. 이 메서드는 주로 데이터베이스에 새로운 사용자 정보를 저장합니다.
		    userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		    // UserService를 호출하여 새로운 사용자를 생성합니다.
		    // username, email, password를 전달하여 사용자를 데이터베이스에 저장합니다.
		} catch (DataIntegrityViolationException e) {
		    // 데이터 무결성 예외가 발생한 경우 (예: username이나 email이 이미 등록되어 있는 경우)
		    // 스택 트레이스를 출력하고, BindingResult에 "이미 등록된 유저입니다."라는 에러 메시지를 추가합니다.
		    e.printStackTrace(); // 예외 세부정보를 로그에 출력하여 디버깅에 도움을 줍니다.
		    bindingResult.reject("signupFailed", "이미 등록된 유저입니다.");
		    return "signup_form"; // 회원가입 폼을 다시 표시하여 사용자가 재입력할 수 있도록 합니다.
		} catch (Exception e) {
		    // 그 외에 발생할 수 있는 모든 예외를 처리합니다.
		    // 예외 세부정보를 출력하고, BindingResult에 예외 메시지를 추가합니다.
		    e.printStackTrace(); // 예외 내용을 출력하여 문제를 파악할 수 있도록 합니다.
		    bindingResult.reject("signupFailed", e.getMessage()); // 예외 메시지를 사용자에게 보여줍니다.
		    return "signup_form"; // 회원가입 폼을 다시 표시합니다.
		}

		

		return "redirect:/";
		// 회원가입이 완료되면 홈페이지("/")로 리다이렉트합니다.
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
}

