package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;// 페이징을 위한 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Question findBySubject(String subject);
	
	Question findBySubjectAndContent(String subject, String content);
	
	List<Question> findBySubjectLike(String subject);
	
	Page<Question> findAll(Pageable pageable);
	// Spring Data JPA에서 제공하는 기본 메서드로, 페이징 및 정렬된 데이터를 반환.
	// Pageable 객체: 현재 페이지, 페이지 크기, 정렬 조건.
	// Page<Question>: 페이징된 결과를 담은 객체.
	
	/**
	 * 동적 쿼리(Specification)를 기반으로 Question 데이터를 페이징 처리하여 조회하는 메서드.
	 *
	 * @param spec     검색 조건을 정의한 Specification 객체.
	 *                 - 동적으로 생성된 조건을 기반으로 데이터를 필터링.
	 *                 - 예: 제목, 내용, 작성자, 답변 등의 키워드를 포함한 검색.
	 * 
	 * @param pageable 페이징 처리를 위한 Pageable 객체.
	 *                 - 페이지 번호, 크기, 정렬 방식 등을 설정 가능.
	 *                 - 예: PageRequest.of(page, size, Sort.by("id").descending()).
	 * 
	 * @return Page<Question> 페이징 처리된 Question 객체 리스트.
	 *         - Page 인터페이스를 반환하여 현재 페이지, 총 페이지 수, 데이터 목록 등을 포함.
	 */
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	@Query("select "
    		+"distinct q "
    		+"from Question q "
    		+"left outer join SiteUser u1 on q.author=u1 "
    		+"left outer join Answer a on a.question=q "
    		+"left outer join SiteUser u2 on a.author=u2 "
    		+"where "
    		+"	q.subject like %:kw% "
    		+"	or q.content like %:kw% "
    		+"	or u1.username like %:kw% "
    		+"	or a.content like %:kw% "
    		+"	or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	
	/*
	 * 	@Query // 직접 작성한 JPQL 쿼리를 실행
	 * 
		"select distinct q" // 중복 제거를 위해 DISTINCT 사용
		
		"from Question q" // Question 엔티티를 기준으로 조회
		
		"left outer join SiteUser u1 on q.author=u1" // 질문 작성자와 LEFT JOIN
		
		"left outer join Answer a on a.question=q" // 답변과 LEFT JOIN
		
		"left outer join SiteUser u2 on a.author=u2" // 답변 작성자와 LEFT JOIN
		
		"where ..." // 제목, 내용, 작성자, 답변 내용, 답변 작성자에서 키워드 검색
		
		Page<Question> findAllByKeyword // 페이징과 검색 조건을 조합한 메서드
		
		@Param("kw") // 키워드 매개변수
		
		Pageable pageable // 페이징 매개변수
	 * */
}
