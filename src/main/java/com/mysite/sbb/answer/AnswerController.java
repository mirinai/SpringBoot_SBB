package com.mysite.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//"/answer" 경로에 대한 요청을 처리하는 컨트롤러 클래스
@RequestMapping("/answer")
//final 필드를 가진 생성자를 자동으로 생성해주는 Lombok 어노테이션
@RequiredArgsConstructor
//이 클래스가 Spring MVC의 컨트롤러임을 나타내는 어노테이션
@Controller
public class AnswerController {

	// QuestionService를 주입받아 사용
	private final QuestionService questionService;

	// AnswerService를 주입받아서 쓰기
	private final AnswerService answerService;

// // 답변 생성 요청을 처리하는 메서드
// @PostMapping("/create/{id}") // "/create/{id}" 경로로 POST 요청이 들어올 때 호출됨
////답변 생성 요청을 처리하는 메서드
// public String createAnswer(Model model, @PathVariable("id") Integer id,
//         @RequestParam(value = "content") String content) {
//	    // @PathVariable("id")로 URL 경로에서 질문 ID를 받아오고,
//	    // @RequestParam("content")로 요청 파라미터에서 답변 내용을 받아옴
//	 	
//     // ID에 해당하는 질문을 가져옴
//     Question question = this.questionService.getQuestion(id);
//     
//     // AnswerService의 create 메서드를 호출하여 답변을 생성하고 저장
//     this.answerService.create(question, content);
//
//     
//     // 질문 상세 페이지로 리다이렉트
//     return String.format("redirect:/question/detail/%s", id);
// }
	@PostMapping("/create/{id}") // "/create/{id}" URL로 POST 요청이 들어오면 이 메서드가 실행됩니다.
//URL의 {id} 부분은 질문의 ID로 사용됩니다.
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult) {

		// Question 객체를 조회하여 현재 작성 중인 답변이 속할 질문 정보를 가져옵니다.
		Question question = this.questionService.getQuestion(id);

		// 유효성 검사에서 에러가 발생한 경우 처리
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question); // 에러가 발생했을 때 질문 정보를 모델에 다시 추가하여 뷰에 전달
			return "question_detail"; // 유효성 검사 실패 시, 질문 상세 페이지로 이동
		}

		// 유효성 검사를 통과한 경우 답변을 저장
		this.answerService.create(question, answerForm.getContent()); // 답변 내용을 저장할 때 관련 질문과 답변 내용을 전달
		return String.format("redirect:/question/detail/%s", id); // 답변을 저장한 후 해당 질문 상세 페이지로 리다이렉트
	}

}
