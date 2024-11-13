package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller // 이 클래스를 Spring MVC 컨트롤러로 설정
public class MainController {

	@GetMapping("/sbb") // "/sbb" URL로 GET 요청이 오면 이 메서드가 실행됨
	@ResponseBody // 뷰 이름이 아닌 문자열 자체를 응답으로 전송하도록 설정
	public String index() {
		return "sbb에 온걸 환영한다 아쎄이"; // 클라이언트에게 이 문자열을 직접 응답으로 보냄
	}

	@GetMapping("/") // 루트 URL ("/")로 GET 요청이 오면 이 메서드가 실행됨
	public String root() {
		return "redirect:/question/list"; // "/question/list" URL로 리다이렉트
	}

}

