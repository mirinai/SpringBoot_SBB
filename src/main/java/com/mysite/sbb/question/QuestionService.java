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

import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor // final 필드를 포함한 생성자를 자동으로 생성해 주는 Lombok 애너테이션
@Service // 이 클래스를 Spring의 서비스로 등록하여 비즈니스 로직을 처리하도록 지정
public class QuestionService {

    private final QuestionRepository questionRepository; // QuestionRepository를 주입받아 데이터 접근 처리

    
//    // 생성자를 통해 의존성 주입. @Autowired가 없어도 Spring이 자동으로 주입, @RequiredArgsConstructor가 있어서 주석으로 놓음
//    public QuestionService(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }
    
    
    
    private Specification<Question> search(String kw) {
        // 검색 키워드(kw)를 기준으로 Question 엔티티의 동적 쿼리를 생성하는 메서드
        return new Specification<Question>() {
            
            private static final long serialVersionUID = 1L; 
            // 직렬화가 필요한 경우를 대비한 고유 식별자(serialVersionUID).
            
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // Root<Question> q: 쿼리에서 Question 엔티티를 루트로 사용.
                // CriteriaQuery<?> query: 쿼리 객체.
                // CriteriaBuilder cb: 조건(Predicate) 생성에 사용되는 객체.

                query.distinct(true); 
                // 중복 제거 설정 (SELECT DISTINCT).

                // Question 엔티티와 author 필드를 LEFT JOIN
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);

                // Question 엔티티와 answerList 필드를 LEFT JOIN
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);

                // Answer 엔티티와 author 필드를 LEFT JOIN
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);

                // 여러 조건을 OR로 묶어 동적 쿼리 생성
                return cb.or(
                    cb.like(q.get("subject"), "%" + kw + "%"),          // 제목에서 키워드 검색
                    cb.like(q.get("content"), "%" + kw + "%"),          // 내용에서 키워드 검색
                    cb.like(u1.get("username"), "%" + kw + "%"),        // 질문 작성자의 이름에서 키워드 검색
                    cb.like(a.get("content"), "%" + kw + "%"),          // 답변 내용에서 키워드 검색
                    cb.like(u2.get("username"), "%" + kw + "%")         // 답변 작성자의 이름에서 키워드 검색
                );
            }
        };
    }

    

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
    
    public Page<Question> getList(int page, String kw){
    	
    	List<Sort.Order> sorts = new ArrayList<>();
    	sorts.add(Sort.Order.desc("createDateTime"));
    	
    	Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    	
    	
    	// 정렬 기준: createDateTime 필드를 기준으로 내림차순 정렬.
    	// 페이지 크기: 한 페이지당 10개씩 표시.
    	// Pageable 객체 생성: PageRequest.of(page, 10, Sort.by(sorts)).
    	
//    	Specification<Question> spec = search(kw); // 동적 쿼리 생성
//    	
//    	return this.questionRepository.findAll(spec, pageable);// page: 조회할 페이지, 10: 한페이에 보여 줄 게시물의 갯수
//    	// 페이징 + 검색 키워드 조건을 함께 적용.
    	
    	return this.questionRepository.findAllByKeyword(kw, pageable);

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
    
    public void vote(Question question, SiteUser siteUser) {
        // 질문(Question) 객체의 voter(투표자) 목록에 현재 사용자(SiteUser)를 추가
        question.getVoter().add(siteUser);
        
        // 수정된 질문 객체를 데이터베이스에 저장하여 투표 결과 반영
        this.questionRepository.save(question);
    }
    
    
}


