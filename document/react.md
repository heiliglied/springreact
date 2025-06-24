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



# react-router v7을 이용한 csr, ssr 동시쓰기 작업하기.  

## 설치  
1. npx create-react-router@latest ./ => 현재폴더에 설치.  
2. 설치시 dependency package 설치 여부 물어볼 때 yes를 선택하면  
   npm install을 따로 실행하지 않아도 설치 됨.  
3. npm run dev로 실행할 수 있음.  

## 설정.
1. csr과 ssr을 동시에 사용하기 위한 설정.
 - 기본적으로 remix와 합친 패키지이기 때문에 routes폴더 기반으로 자동 라우팅을 진행하긴 함.(SSR)  
 - react-router.config.ts 파일을 수정하여 async prerender() { return [경로들]; } 을 설정하면  
   정적페이지를 미리 읽어올 수 있음.

