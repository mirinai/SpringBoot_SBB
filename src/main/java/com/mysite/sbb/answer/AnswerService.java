package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

//final 필드를 가진 생성자를 자동으로 생성해주는 Lombok 어노테이션
@RequiredArgsConstructor
//이 클래스가 Spring의 서비스임을 나타내는 어노테이션
@Service
public class AnswerService {

	// AnswerRepository를 주입받아 사용
	private final AnswerRepository answerRepository;

	// 답변을 생성하고 저장하는 메서드
	public Answer create(Question question, String content, SiteUser author) {
		// 새로운 Answer 객체 생성
		Answer answer = new Answer();

		// 답변 내용 설정
		answer.setContent(content);

		// 답변 생성 날짜 설정
		answer.setCreateDate(LocalDateTime.now());

		// 답변이 연결된 질문 설정
		answer.setQuestion(question);

		answer.setAuthor(author);

		// 답변을 데이터베이스에 저장
		this.answerRepository.save(answer);
		
		return answer;

	}
	
	 public Answer getAnswer(Integer id) {
    	 Optional<Answer> answer = this.answerRepository.findById(id);
    	 
    	 if(answer.isPresent()) {
    		 return answer.get();
    	 }
    	 else {
    		 throw new DataNotFoundException("answer not found");
    	 }
     }
	 
	 public void modify(Answer answer, String content) {
		 answer.setContent(content);
		 answer.setModifyDate(LocalDateTime.now());
		 this.answerRepository.save(answer);
	 }
	 
	 public void delete(Answer answer) {
		 this.answerRepository.delete(answer);
	 }
	 
	 
	 public void vote(Answer answer, SiteUser siteUser) {
		 answer.getVoter().add(siteUser);
		 this.answerRepository.save(answer);
	 }
}
