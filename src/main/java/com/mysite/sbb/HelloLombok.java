package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor: final 필드에 대해 자동으로 생성자를 생성, 
// final이나 @NonNull 필드가 있을 때만 해당 필드를 초기화하는 생성자를 자동으로 생성 
// @Getter: 각 필드의 getter 메서드를 자동으로 생성
@RequiredArgsConstructor
@Getter
public class HelloLombok {
	private final String hello; // "hello" 필드, final로 선언되어 변경 불가능
	private final int lombok; // "lombok" 필드, final로 선언되어 변경 불가능
	
	public static void main(String[] args) {
		// HelloLombok 클래스의 인스턴스를 생성하면서 "헬로"와 5를 전달하여 초기화
		HelloLombok helloLombok = new HelloLombok("헬로", 5);
		
		// getHello 메서드를 통해 hello 필드의 값을 출력
		System.out.println(helloLombok.getHello());
		// getLombok 메서드를 통해 lombok 필드의 값을 출력
		System.out.println(helloLombok.getLombok());
	}
}

//아래는 @RequiredArgsConstructor를 안 쓰면 쓰게 되는 코드

/*@Getter // getter 메서드를 자동으로 생성
public class HelloLombok {
	private final String hello; // "hello" 필드, final로 선언되어 변경 불가능
	private final int lombok; // "lombok" 필드, final로 선언되어 변경 불가능

	// 필드를 초기화하는 생성자
	public HelloLombok(String hello, int lombok) {
		this.hello = hello;
		this.lombok = lombok;
	}

	public static void main(String[] args) {
		// HelloLombok 클래스의 인스턴스를 생성하면서 "헬로"와 5를 전달하여 초기화
		HelloLombok helloLombok = new HelloLombok("헬로", 5);
		
		// @Getter로 자동 생성된 getHello 메서드를 통해 hello 필드의 값을 출력
		System.out.println(helloLombok.getHello());
		// @Getter로 자동 생성된 getLombok 메서드를 통해 lombok 필드의 값을 출력
		System.out.println(helloLombok.getLombok());
	}
}*/



//import lombok.Getter;
//import lombok.Setter;


// @Getter와 @Setter 어노테이션을 통해 필드의 getter/setter 메서드를 자동 생성
//@Getter
//@Setter
//public class HelloLombok {
//	private String hello; // "hello" 필드, getter와 setter가 자동으로 생성됨
//	private int lombok; // "lombok" 필드, getter와 setter가 자동으로 생성됨
//	
//	public static void main(String[] args) {
//		// HelloLombok 클래스의 인스턴스 생성
//		HelloLombok helloLombok = new HelloLombok();
//		
//		// setHello 메서드를 사용하여 hello 필드에 "헬로" 값을 설정
//		helloLombok.setHello("헬로");
//		// setLombok 메서드를 사용하여 lombok 필드에 5 값을 설정
//		helloLombok.setLombok(5);
//		
//		// getHello 메서드를 통해 hello 필드의 값을 출력
//		System.out.println(helloLombok.getHello());
//		// getLombok 메서드를 통해 lombok 필드의 값을 출력
//		System.out.println(helloLombok.getLombok());
//	}
//}


// 아래는 직접 쓴 코드

/*public class HelloLombok {
	private String hello; // "hello" 필드
	private int lombok; // "lombok" 필드

	// "hello" 필드의 getter 메서드
	public String getHello() {
		return hello;
	}

	// "hello" 필드의 setter 메서드
	public void setHello(String hello) {
		this.hello = hello;
	}

	// "lombok" 필드의 getter 메서드
	public int getLombok() {
		return lombok;
	}

	// "lombok" 필드의 setter 메서드
	public void setLombok(int lombok) {
		this.lombok = lombok;
	}

	public static void main(String[] args) {
		// HelloLombok 클래스의 인스턴스 생성
		HelloLombok helloLombok = new HelloLombok();
		
		// setHello 메서드를 사용하여 hello 필드에 "헬로" 값을 설정
		helloLombok.setHello("헬로");
		// setLombok 메서드를 사용하여 lombok 필드에 5 값을 설정
		helloLombok.setLombok(5);
		
		// getHello 메서드를 통해 hello 필드의 값을 출력
		System.out.println(helloLombok.getHello());
		// getLombok 메서드를 통해 lombok 필드의 값을 출력
		System.out.println(helloLombok.getLombok());
	}
}
*/