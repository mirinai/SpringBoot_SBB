package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter // Lombok을 사용해 모든 필드의 getter 메서드를 자동 생성
@Setter // Lombok을 사용해 모든 필드의 setter 메서드를 자동 생성
@Entity // JPA의 엔티티 클래스로 선언하여 데이터베이스 테이블과 매핑
public class Question {
	
	@Id // 해당 필드를 기본 키로 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스가 자동으로 관리하도록 설정
	private Integer id; // 질문의 고유 ID (기본 키)
	
	@Column(length = 200) // 컬럼 길이를 200자로 제한
	private String subject; // 질문의 제목
	
	@Column(columnDefinition = "TEXT") // 컬럼을 TEXT로 정의하여 긴 텍스트 저장 가능
	private String content; // 질문의 내용
	
	private LocalDateTime createDateTime; // 질문이 생성된 날짜와 시간
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList; // 질문에 달린 답변 목록, 질문과 답변 간의 일대다 관계를 나타냄. 질문 삭제 시 관련 답변들도 함께 삭제됨
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	

}
