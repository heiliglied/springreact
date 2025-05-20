# JPA 사용법.  
  
## 설치  
build.gradle의 dependencies 항목에 다음 내용을 추가한다.(버전은 사용할 버전을 기입, boot는 버전기입 안하면 최신 안정판 다운받음.)  
implementation 'org.springframework.boot:spring-boot-starter-data-jpa:3.4.3'  

## 사용방법  
1. Entity생성.  
2. 클래스가 Entity라는것을 확인할 수 있도록 annotation을 설정한다.(@Entity)  
3. 옵션값에 맞춰 컬럼값들을 설정한다.(옵션값은 jpa 공식문서에서 확인)  
4. repository 인터페이스 생성.  
  : 
    기본적인 save, update, delete 등은 interface에 설정되어 있다.(선언하지 않아도 사용 가능)  
    별도 사용할 메소드는 JPA 규칙에 맞춰 생성한다.(findFirst~ findBy~ 등 기본적인 Entity를 이용한 조회는 자동으로 생성 됨.)    

## 참고사항  
※ 자동생성은 사용하지 않는다.(제약조건이 큼)
  : 대신 flyway db 형상관리툴을 사용한다.