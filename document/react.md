# 리액트 사용법 정리.  

## 설치 과정  
1. vite를 이용한 설치.  
 - npm create vite@latest {설치경로(생략불가 현재폴더 지정시 .)} -- --template {사용할 템플릿 명. 여기서는 react} npm의 경우 더블하이픈이 한번 더 들어가야함.  
   : react 이외에도 react-ts vue vue-ts 등도 사용가능.(stelve 등의 옵션도 지원함.)  
 - 실행시 자동으로 지정한 경로에 기본 react 설정과 샘플 페이지를 만들어 줌.

2. 

## 사용법.  
1. 문법 차이.  
 - react 내부에서 html 사용시에는 class => className, for htmlFor 등 문법이 다른 경우를 확인해야 함.
 - inner style 사용시에는 style={{name: 'value'}} 형식으로 지정하며, 여러개를 사용할 때는 ; 대신 ,를 사용해야함.

2. html return.
 - return(html); 형식으로 html 값을 반환하며, 최상위 tag가 열고 닫는 형식의 레이아웃이 아닐때는 빈 <></> 태그를 지정해야 에러가 나지 않음.

3. 