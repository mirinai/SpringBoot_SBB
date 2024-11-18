package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;// 페이징을 위한 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Question findBySubject(String subject);
	
	Question findBySubjectAndContent(String subject, String content);
	
	List<Question> findBySubjectLike(String subject);
	
	Page<Question> findAll(Pageable pageable);
	// Spring Data JPA에서 제공하는 기본 메서드로, 페이징 및 정렬된 데이터를 반환.
	// Pageable 객체: 현재 페이지, 페이지 크기, 정렬 조건.
	// Page<Question>: 페이징된 결과를 담은 객체.
}
