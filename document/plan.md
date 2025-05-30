# 예제 진행.  
  
1. JPA 설정.  
 - 사용자 계정에 사용할 Entity 및 리포지토리 작성.  
 - 연동해서 데이터 베이스 세팅 및 연결동작 확인.  
 - JPA 쿼리 빌더 작성하기.  
 - 시더 만들어서 데이터 집어넣어보기.  
   flyway 사용해서 데이터베이스 형상관리 적용하기. => 별도 문서로 사용법 정리.  

2. 가입, 로그인 페이지 작성  
 - 전체 페이지에 작성할 navbar 작성하기  => 일부 완성.
 - 가입 페이지 입력 폼 작성하기
   : 입력할 항목 정리하기.  
 - controller 동작 확인하기.  
 - react proxy 연결 동작 확인하기.  
 - 로그인 후 쿠키 데이터 연동.
 - spring security에서 login 핸들러 처리하기.
 - env 파일 사용법 익히기. 민감한 정보 숨기는 방법.
 - 발급받은 토큰 및 claims 정보 react에서 localStorage에 저장하기.
 
3. interceptor 기능 만들기.
 - spring security에 addFilterBefore를 추가해서 페이지 이동시 인증 사전 처리하기.
 - react에서 오류메시지 제대로 처리하도록 수정하기