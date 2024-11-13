package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

@SpringBootTest // Spring Boot 테스트를 위한 어노테이션. 실제 애플리케이션과 동일한 환경에서 테스트를 실행
class SbbApplicationTests {

	//	@Test
	//	void contextLoads() {
	//	}
	
	@Autowired // Spring이 QuestionRepository 객체를 자동으로 주입
	private QuestionRepository questionRepository;
	
	@Autowired // Spring이 AnswerRepository 객체를 자동으로 주입
	private AnswerRepository answerRepository;
	
	@Transactional //하나의 트랜잭션 내에서 데이터베이스 세션이 유지됨
	@Test // JUnit 테스트 메서드로 지정
	void testJpa() {
		
		// 질문 데이터 저장하기
//		Question q1 = new Question(); // 새로운 Question 객체 생성
//		q1.setSubject("sbb가 뭐지?"); // 첫 번째 질문의 제목 설정
//		q1.setContent("sbb가 뭔지 알고 싶음"); // 첫 번째 질문의 내용 설정
//		q1.setCreateDateTime(LocalDateTime.now()); // 첫 번째 질문의 생성 일시를 현재 시간으로 설정
//		this.questionRepository.save(q1); // 첫 번째 질문을 데이터베이스에 저장

		
//		Question q2 = new Question(); // 새로운 Question 객체 생성
//		q2.setSubject("스프링 부트 모델 질문이다."); // 두 번째 질문의 제목 설정
//		q2.setContent("id는 자동생성되냐?"); // 두 번째 질문의 내용 설정
//		q2.setCreateDateTime(LocalDateTime.now()); // 두 번째 질문의 생성 일시를 현재 시간으로 설정
//		this.questionRepository.save(q2); // 두 번째 질문을 데이터베이스에 저장
		
		
		// 질문 데이터 조회하기
//		List<Question> all = this.questionRepository.findAll();// 데이터베이스에 저장된 모든 Question 객체를 리스트로 조회
//		assertEquals(2, all.size());// 리스트의 크기가 2인지 확인하여, 데이터가 제대로 저장되었는지 검증
//		
//		
//		Question q = all.get(0); // 리스트의 첫 번째 Question 객체를 가져옴
//		assertEquals("sbb가 뭐지?", q.getSubject()); // 첫 번째 Question의 제목이 "sbb가 뭐지?"인지 검증
		
		
//		Optional<Question> oq = this.questionRepository.findById(1); // ID가 1인 Question 객체를 데이터베이스에서 조회, 결과를 Optional로 반환
//		if (oq.isPresent()) { // Optional에 값이 있는지 확인 (ID가 1인 Question이 존재하는지 확인)
//			Question q = oq.get(); // Optional에서 Question 객체를 꺼냄
//			assertEquals("sbb가 뭐지?", q.getSubject()); // Question 객체의 제목이 "sbb가 뭐지?"와 일치하는지 검증
//		}
		
		
//		Question q = this.questionRepository.findBySubject("sbb가 뭐지?"); 
//		// "sbb가 뭐지?"라는 제목을 가진 Question 객체를 데이터베이스에서 조회
//		assertEquals(1, q.getId()); 
//		// 조회된 Question 객체의 ID가 1인지 검증
		
		
//		Question q = this.questionRepository.findBySubjectAndContent("sbb가 뭐지?", "sbb가 뭔지 알고 싶음"); 
//		// 제목이 "sbb가 뭐지?"이고 내용이 "sbb가 뭔지 알고 싶음"인 Question 객체를 데이터베이스에서 조회
//		assertEquals(1, q.getId()); 
//		// 조회된 Question 객체의 ID가 1인지 검증
		
		
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%"); 
//		// 제목이 "sbb"로 시작하는 모든 Question 객체를 조회하여 리스트로 반환
//		Question q = qList.get(0); 
//		// 조회된 리스트의 첫 번째 Question 객체를 가져옴
//		assertEquals("sbb가 뭐지?", q.getSubject()); 
//		// 첫 번째 Question 객체의 제목이 "sbb가 뭐지?"와 일치하는지 검증
		
		
		// 질문 데이터 고치기
//		Optional<Question> oq = this.questionRepository.findById(1); 
//		// ID가 1인 Question 객체를 데이터베이스에서 조회, 결과를 Optional로 반환
//		assertTrue(oq.isPresent()); 
//		// 조회된 Question 객체가 존재하는지 확인
//		Question q = oq.get(); 
//		// Optional에서 Question 객체를 꺼냄
//		q.setSubject("수정된 제목"); 
//		// Question 객체의 제목을 "수정된 제목"으로 변경
//		this.questionRepository.save(q); 
//		// 변경된 Question 객체를 데이터베이스에 저장하여 업데이트
		
		
		// 질문 데이터 지우기
//		assertEquals(2, this.questionRepository.count()); 
//		// 현재 데이터베이스에 저장된 Question 객체 수가 2인지 확인하여 검증
//		Optional<Question> oq = this.questionRepository.findById(1); 
//		// ID가 1인 Question 객체를 데이터베이스에서 조회, 결과를 Optional로 반환
//		assertTrue(oq.isPresent()); 
//		// 조회된 Question 객체가 존재하는지 확인
//		Question q = oq.get(); 
//		// Optional에서 Question 객체를 꺼냄
//		this.questionRepository.delete(q); 
//		// 해당 Question 객체를 데이터베이스에서 삭제
//		assertEquals(1, this.questionRepository.count()); 
//		// 데이터베이스에 남아 있는 Question 객체 수가 1인지 확인하여 검증

		
		// 답변 데이터 저장하기
//		Optional<Question> oq = this.questionRepository.findById(2); 
//		// ID가 2인 Question 객체를 데이터베이스에서 조회하고 Optional로 반환
//		assertTrue(oq.isPresent()); 
//		// 조회된 Question 객체가 존재하는지 확인
//		Question q = oq.get(); 
//		// Optional에서 Question 객체를 꺼냄
//
//		Answer a = new Answer(); 
//		// 새로운 Answer 객체 생성
//		a.setContent("어 알아서 만들어짐"); 
//		// Answer 객체의 내용 설정
//		a.setQuestion(q); 
//		// Answer 객체에 연결된 Question 객체 설정 (답변이 속한 질문)
//		a.setCreateDate(LocalDateTime.now()); 
//		// Answer 객체의 생성 날짜와 시간을 현재 시각으로 설정
//		this.answerRepository.save(a); 
//		// Answer 객체를 answerRepository를 통해 데이터베이스에 저장

		
		// 답변 데이터 조회하기
//		Optional<Answer> oa = this.answerRepository.findById(1); 
//		// ID가 1인 Answer 객체를 데이터베이스에서 조회하고, 결과를 Optional로 반환
//		assertTrue(oa.isPresent()); 
//		// 조회된 Answer 객체가 존재하는지 확인
//		Answer a = oa.get(); 
//		// Optional에서 Answer 객체를 꺼냄
//		assertEquals(2, a.getQuestion().getId()); 
//		// Answer 객체가 연관된 Question 객체의 ID가 2인지 검증

		
		
		//답변 데이터를 통해 질문 데이터 찾기 vs 질문 데이터를 통해 답변 데이터 찾기
		Optional<Question> oq = this.questionRepository.findById(2); 
		// ID가 2인 Question 객체를 데이터베이스에서 조회하고 Optional로 반환
		assertTrue(oq.isPresent()); 
		// 조회된 Question 객체가 존재하는지 확인
		Question q = oq.get(); 
		// Optional에서 Question 객체를 꺼냄

		List<Answer> answerList = q.getAnswerList(); 
		// Question 객체의 연관된 Answer 리스트를 가져옴

		assertEquals(1, answerList.size()); 
		// Answer 리스트의 크기가 1인지 확인하여, 연관된 답변이 하나임을 검증
		assertEquals("어 알아서 만들어짐", answerList.get(0).getContent()); 
		// 첫 번째 Answer 객체의 내용이 "어 알아서 만들어짐"과 일치하는지 검증

	}
}
