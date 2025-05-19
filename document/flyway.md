# Flyway 형상관리 툴 사용법.

## 설치  
build.gradle 파일에  
Mysql 8.x 또는 mariaDB일 경우 implementation 'org.flywaydb:flyway-mysql' 추가.  
그 외의 경우에는 implementation 'org.flywaydb:flyway-core' 를 추가한다.  

## 설정.  
1. application.properties 파일 설정.  
 - spring.jpa.hibernate.ddl-auto= 값을 설정한다.  
   validate를 설정하면 실 DB값과 JPA Entity 내용이 다르면 실행을 방지한다.  
   none일 경우 무시하고 실행한다.  
   create, update의 경우 생성 수정이나, 실제로는 잘 사용하지 않음.  
 - spring.flyway.baselineOnMigrate 설정.  
   true일 경우 가장 최신 버전의 migration 스크립트를 실행.  
   false일 경우 migration 동작하지 않음.  

2. 스키마 파일 등록.
 - main/resources/db/migration 폴더 생성.(툴이 위치가 지정되어 있음)  
 - 생성한 폴더 아래에 sql, json, xml 형식으로 된 파일을 생성한다.  
   migration 파일은 생성 규칙이 정해져 있음.  
   {prefix}{version}__{name}.사용할 형식 형태로 생성한다.
   prefix는 V: 버전 마이그레이션, U: 실행취소, R: 반복실행 으로 정해져 있으나 대충 V를 사용.  
   version은 1부터 순차적으로 증가시키며, 가장 높은 값을 인식해서 실행한다.  
   구분자의 경우 __ 반드시 언더바 2번을 사용하며, 1개일 경우 무시된다.   
 - 파일은 일반적인 쿼리문을 사용해서 등록하면, 순차적으로 실행된다.  
   실행된 파일은 flyway_schema_history에 버전별로 기록된다.  
   spring.flyway.baselineOnMigrate=true 설정시 실행과 동시에 실행된다.  
