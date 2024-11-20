package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest; 
//현재 페이지와 한페이지에 보여줄 게시물 개수등을 설정하여 페이징 요청을 하는 클래스다.
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor // final 필드를 포함한 생성자를 자동으로 생성해 주는 Lombok 애너테이션
@Service // 이 클래스를 Spring의 서비스로 등록하여 비즈니스 로직을 처리하도록 지정
public class QuestionService {

    private final QuestionRepository questionRepository; // QuestionRepository를 주입받아 데이터 접근 처리

    
//    // 생성자를 통해 의존성 주입. @Autowired가 없어도 Spring이 자동으로 주입, @RequiredArgsConstructor가 있어서 주석으로 놓음
//    public QuestionService(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }
    

    public List<Question> getList() { // 전체 질문 목록을 반환하는 메서드
        return this.questionRepository.findAll(); // questionRepository를 통해 모든 질문을 조회
    }
    
    public Question getQuestion(Integer id) {
        // 주어진 ID로 Question 객체를 Optional로 조회
        Optional<Question> question = this.questionRepository.findById(id);
        
        if (question.isPresent()) { // question이 존재하는 경우
            return question.get(); // Optional에서 Question 객체를 꺼내어 반환
        } else {
            throw new DataNotFoundException("question not found"); // question이 존재하지 않으면 예외를 발생시킴
        }
    }
    
    public void create(String subject, String content, SiteUser user) {
    	Question q  = new Question();
    	q.setSubject(subject);
    	q.setContent(content);
    	q.setCreateDateTime(LocalDateTime.now());
    	q.setAuthor(user);
    	this.questionRepository.save(q);
    }
    
    public Page<Question> getList(int page){
    	
    	List<Sort.Order> sorts = new ArrayList<>();
    	sorts.add(Sort.Order.desc("createDateTime"));
    	
    	Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    	
    	return this.questionRepository.findAll(pageable);// page: 조회할 페이지, 10: 한페이에 보여 줄 게시물의 갯수
    	
    	// 정렬 기준: createDateTime 필드를 기준으로 내림차순 정렬.
    	// 페이지 크기: 한 페이지당 10개씩 표시.
    	// Pageable 객체 생성: PageRequest.of(page, 10, Sort.by(sorts)).
    }
    
    public void modify(Question question, String subject, String content) {
    	question.setSubject(subject);
    	question.setContent(content);
    	question.setModifyDate(LocalDateTime.now());
    	this.questionRepository.save(question);
    }
    
    public void delete(Question question) {
    	this.questionRepository.delete(question);
    }
}


