package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor // final 필드를 포함한 생성자를 자동으로 생성해 주는 Lombok 애너테이션
@Controller // 이 클래스를 Spring MVC의 컨트롤러로 지정
public class QuestionController {

//	private final QuestionRepository questionRepository; 
//	// 질문 데이터를 관리하는 리포지토리, 생성자 주입 방식으로 주입
	
	private final QuestionService questionService;

	@GetMapping("/question/list") // "/question/list" URL로 GET 요청이 오면 이 메서드가 실행됨
	public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 알아서 만들어짐
//		List<Question> questionList = this.questionRepository.findAll(); // 리포지토리를 사용해 모든 질문 데이터를 가져옴
		
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList); 
		// 모델에 "questionList"라는 이름으로 데이터를 추가하여 뷰로 전달
		
		return "question_list"; 
		// src/main/resources/templates 안의 파일 이름에 해당하는 뷰를 반환
	}
	
	@GetMapping(value = "/question/detail/{id}") // "/question/detail/{id}" URL로 GET 요청이 오면 이 메서드가 실행됨, {id}는 경로 변수로 사용됨
	public String detail(Model model, @PathVariable("id") Integer id) {
	    // @PathVariable을 사용하여 URL의 {id} 값을 메서드의 매개변수 id에 매핑,{id}는 동적인 경로 변수로, 해당 위치의 값을 id 매개변수로 전달받음
		
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		
	    return "question_detail"; 
	    // src/main/resources/templates 안의 "question_detail"이라는 이름의 뷰를 반환
	}

	

}

