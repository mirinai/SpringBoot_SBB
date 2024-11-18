package com.mysite.sbb.user;

// JPA 어노테이션 및 Lombok 어노테이션을 임포트
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter // Lombok을 사용하여 모든 필드의 Getter 메서드를 자동으로 생성
@Setter // Lombok을 사용하여 모든 필드의 Setter 메서드를 자동으로 생성
@Entity // 이 클래스가 JPA 엔티티임을 나타냄. 데이터베이스의 테이블과 매핑됨
public class SiteUser {
	
	@Id // 이 필드가 엔티티의 기본 키(primary key)임을 나타냄
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임. 보통 AUTO_INCREMENT를 의미
	private Long id;
	
	@Column(unique = true) // 데이터베이스 컬럼에 유니크 제약 조건을 추가. 중복된 username을 허용하지 않음
	private String username;
	
	private String password; // @Column 어노테이션 없이도 기본적으로 데이터베이스 컬럼으로 매핑됨
	
	@Column(unique = true) // 데이터베이스 컬럼에 유니크 제약 조건을 추가. 중복된 email을 허용하지 않음
	private String email;
	
	// 기본 생성자가 명시적으로 정의되지 않았지만, 컴파일러가 자동으로 제공
	// JPA는 엔티티 클래스에 기본 생성자가 필요함. Lombok을 사용하지 않더라도, 기본 생성자가 제공되어야 함
}
