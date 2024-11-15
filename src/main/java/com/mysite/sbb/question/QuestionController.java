package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/question") //프리픽스, localhost:8080/question 이하의 요청을 처리하는 컨트롤러
@RequiredArgsConstructor // final 필드를 포함한 생성자를 자동으로 생성해 주는 Lombok 애너테이션
@Controller // 이 클래스를 Spring MVC의 컨트롤러로 지정
public class QuestionController {

//	private final QuestionRepository questionRepository; 
//	// 질문 데이터를 관리하는 리포지토리, 생성자 주입 방식으로 주입
	
	private final QuestionService questionService;

	@GetMapping("/list") // "/question/list" URL로 GET 요청이 오면 이 메서드가 실행됨
	public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 알아서 만들어짐
//		List<Question> questionList = this.questionRepository.findAll(); // 리포지토리를 사용해 모든 질문 데이터를 가져옴
		
		List<Question> questionList = this.questionService.getList();
		
		model.addAttribute("questionList", questionList); 
		// 모델에 "questionList"라는 이름으로 데이터를 추가하여 뷰로 전달
		
		return "question_list"; 
		// src/main/resources/templates 안의 파일 이름에 해당하는 뷰를 반환
	}
	
	@GetMapping(value = "/detail/{id}") // "/question/detail/{id}" URL로 GET 요청이 오면 이 메서드가 실행됨, {id}는 경로 변수로 사용됨
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
	    // @PathVariable을 사용하여 URL의 {id} 값을 메서드의 매개변수 id에 매핑,{id}는 동적인 경로 변수로, 해당 위치의 값을 id 매개변수로 전달받음
		
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		
	    return "question_detail"; 
	    // src/main/resources/templates 안의 "question_detail"이라는 이름의 뷰를 반환
	}

	@GetMapping("/create") // "/create" URL로 GET 요청이 들어오면 이 메서드가 실행됩니다.
	public String questionCreate(QuestionForm questionForm) {
	    // QuestionForm 객체를 파라미터로 선언하면, Spring이 자동으로 이 객체를 초기화하고 모델에 추가합니다.
	    // 이로 인해 "questionForm"이라는 이름으로 모델에 전달되어, 템플릿에서 사용할 수 있게 됩니다.
	    // 예를 들어, "th:object=${questionForm}"을 통해 템플릿에서 해당 객체를 바인딩할 수 있습니다.

	    return "question_form"; // "question_form"이라는 이름의 템플릿을 반환하여 폼 화면을 보여줍니다.
	}

	
	// spring-boot-starter-validation 없이 그냥 받아 저장할 때
//	 @PostMapping("/create")
//	 public String questionCreate(@RequestParam(value = "subject") String subject,
//	         @RequestParam(value = "content") String content) {
//	     this.questionService.create(subject, content);
//	     
//	     return "redirect:/question/list"; // 질문 저장한 뒤에 질문 리스트로 이동하기
//	 }

	@PostMapping("/create") // "/create" URL로 POST 요청이 오면 실행되는 메서드
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
	    
	    // @Valid를 사용해 QuestionForm 객체의 유효성을 검증하고, 결과를 BindingResult에 저장
	    if(bindingResult.hasErrors()) { // 유효성 검증에서 에러가 발생했는지 확인
	        return "question_form"; // 에러가 있으면 입력 폼 페이지로 다시 이동
	    }
	    
	    // 유효성 검증을 통과하면 QuestionForm에서 데이터를 가져와 서비스 계층으로 전달하여 저장
	    this.questionService.create(questionForm.getSubject(), questionForm.getContent());
	    
	    return "redirect:/question/list"; // 질문 저장한 뒤에 질문 리스트로 이동하기
	}

	

}

