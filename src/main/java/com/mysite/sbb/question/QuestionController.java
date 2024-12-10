package com.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

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
	
	private final UserService userService;

	@GetMapping("/list") // "/question/list" URL로 GET 요청이 오면 이 메서드가 실행됨
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) { 
		// 매개변수로 Model을 지정하면 객체가 알아서 만들어짐
		//list?page=0: 디폴트, 이런식으로 값 가져오기
		// /list?page=2&kw=Spring 이렇게 주소를 가져옴
		// /list?page=0&kw= : 디폴트
		
		Page<Question> paging = this.questionService.getList(page, kw);
		 // 현재 페이지와 검색 키워드에 해당하는 데이터를 가져옴 (페이징 및 검색 처리)
		
		model.addAttribute("paging",paging);
		// 페이징 데이터를 뷰에 전달
		
		int startPage = Math.max(0, paging.getNumber()-5);
		// 시작 페이지 번호를 현재 페이지에서 -5 범위로 설정 (최소 0으로 제한)
//		int startPage = 0;
		int endPage = Math.min(paging.getTotalPages()-1, paging.getNumber()+4);
		// 끝 페이지 번호를 현재 페이지에서 +4 범위로 설정 (최대 총 페이지 수 - 1로 제한)
//		int endPage = Math.min(9, paging.getTotalPages() - 1);
		
		
		
		model.addAttribute("startPage",startPage);
		// 뷰에서 사용할 시작 페이지 번호를 전달
		model.addAttribute("endPage",endPage);
		// 뷰에서 사용할 끝 페이지 번호를 전달
		model.addAttribute("kw", kw);
		 // 검색 키워드를 뷰에 전달하여 검색 결과와 입력된 키워드 표시
		
//		List<Question> questionList = this.questionRepository.findAll(); // 리포지토리를 사용해 모든 질문 데이터를 가져옴
		
//		List<Question> questionList = this.questionService.getList();
//		
//		model.addAttribute("questionList", questionList); 
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create") // "/create" URL로 GET 요청이 들어오면 이 메서드가 실행됩니다.
	public String questionCreate(QuestionForm questionForm) {
	    // QuestionForm 객체를 파라미터로 선언하면, Spring이 자동으로 이 객체를 초기화하고 모델에 추가합니다.
	    // 이로 인해 "questionForm"이라는 이름으로 모델에 전달되어, 템플릿에서 사용할 수 있게 됩니다.
	    // 예를 들어, "th:object=${questionForm}"을 통해 템플릿에서 해당 객체를 바인딩할 수 있습니다.
		// 질문 등록을 위한 빈 QuestionForm 객체를 뷰에 전달
	    return "question_form"; // "question_form"이라는 이름의 템플릿을 반환하여 폼 화면을 보여줍니다.
	    // question_form.html 템플릿을 렌더링
	}

	
	// spring-boot-starter-validation 없이 그냥 받아 저장할 때
//	 @PostMapping("/create")
//	 public String questionCreate(@RequestParam(value = "subject") String subject,
//	         @RequestParam(value = "content") String content) {
//	     this.questionService.create(subject, content);
//	     
//	     return "redirect:/question/list"; // 질문 저장한 뒤에 질문 리스트로 이동하기
//	 }

	@PreAuthorize("isAuthenticated()")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create") // "/create" URL로 POST 요청이 오면 실행되는 메서드
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
	    
	    // @Valid를 사용해 QuestionForm 객체의 유효성을 검증하고, 결과를 BindingResult에 저장
	    if(bindingResult.hasErrors()) { // 유효성 검증에서 에러가 발생했는지 확인
	        return "question_form"; // 에러가 있으면 입력 폼 페이지로 다시 이동
	    }
	    
	    SiteUser siteUser = this.userService.getUser(principal.getName());
	    
	    // 유효성 검증을 통과하면 QuestionForm에서 데이터를 가져와 서비스 계층으로 전달하여 저장
	    this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
	    
	    return "redirect:/question/list"; // 질문 저장한 뒤에 질문 리스트로 이동하기
	}
	
	@PreAuthorize("isAuthenticated()")
	// 이 메서드는 인증된 사용자만 접근 가능하도록 설정.
	// 인증되지 않은 사용자가 접근하면 Spring Security가 로그인 페이지로 리다이렉트.

	@GetMapping("/modify/{id}")
	// "/modify/{id}" 경로로 요청이 들어올 때 이 메서드를 실행.
	// {id}는 수정하려는 질문의 고유 ID를 나타냄.

	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
	    // 질문 수정 폼을 표시하는 메서드.
	    // - questionForm: 수정 폼에 기존 질문 데이터를 채우기 위한 객체.
	    // - id: 수정하려는 질문의 고유 ID.
	    // - principal: 현재 인증된 사용자의 정보를 나타냄.

	    Question question = this.questionService.getQuestion(id);
	    // ID를 기반으로 질문 데이터를 데이터베이스에서 조회.

	    if (!question.getAuthor().getUsername().equals(principal.getName())) {
	        // 현재 로그인한 사용자가 질문 작성자가 아니라면 권한이 없다고 예외를 발생.
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "고칠 권한이 없음");
	    }

	    questionForm.setSubject(question.getSubject());
	    // 기존 질문의 제목(subject)을 폼에 설정.

	    questionForm.setContent(question.getContent());
	    // 기존 질문의 내용(content)을 폼에 설정.

	    return "question_form";
	    // 수정 폼 페이지(question_form.html)를 반환.
	}

	
	@PreAuthorize("isAuthenticated()")
	// 인증된 사용자만 이 메서드에 접근할 수 있도록 제한.
	// 인증되지 않은 사용자가 접근하면 Spring Security가 로그인 페이지로 리다이렉트.

	@PostMapping("/modify/{id}")
	// "/modify/{id}" 경로로 POST 요청이 들어올 때 이 메서드가 실행.
	// {id}는 수정하려는 질문의 고유 ID를 나타냄.

	public String questionModify(
	    @Valid QuestionForm questionForm, // 폼 데이터를 검증하기 위한 QuestionForm 객체
	    BindingResult bindingResult,     // 폼 검증 결과를 저장
	    Principal principal,             // 현재 인증된 사용자의 정보를 나타냄
	    @PathVariable("id") Integer id   // 경로에서 질문 ID를 추출하여 매핑
	) {
	    if (bindingResult.hasErrors()) {
	        // 폼 데이터에 검증 오류가 있으면 다시 수정 폼 페이지로 이동
	        return "question_form";
	    }

	    Question question = this.questionService.getQuestion(id);
	    // ID를 기반으로 데이터베이스에서 질문을 조회

	    if (!question.getAuthor().getUsername().equals(principal.getName())) {
	        // 현재 로그인한 사용자가 질문 작성자가 아니라면 예외를 던짐
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "고칠 권한이 없음");
	    }

	    this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
	    // 질문의 제목과 내용을 수정하고 데이터베이스에 반영

	    return String.format("redirect:/question/detail/%s", id);
	    // 수정 완료 후 질문 상세 페이지로 리디렉션
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없음");
		}
		this.questionService.delete(question);
		
		return "redirect:/";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	// 인증된 사용자만 이 메서드에 접근 가능. 인증되지 않은 경우 로그인 페이지로 리다이렉트.

	@GetMapping("/vote/{id}")
	// "/vote/{id}" 경로로 GET 요청이 들어오면 이 메서드를 실행.
	// {id}는 투표하려는 질문의 고유 ID.

	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
	    
	    Question question = this.questionService.getQuestion(id);
	    // ID를 기반으로 질문 객체를 데이터베이스에서 조회.

	    SiteUser siteUser = this.userService.getUser(principal.getName());
	    // 현재 로그인한 사용자의 정보를 Principal 객체를 통해 가져와 SiteUser로 조회.

	    this.questionService.vote(question, siteUser);
	    // 질문에 대해 현재 사용자가 투표를 수행하도록 처리.

	    return String.format("redirect:/question/detail/%s", id);
	    // 투표가 완료되면 질문 상세 페이지로 리다이렉트.
	}

	
	

}

