<!-- 서비스 간략설명  -->
<img width="100" alt="modal1" src="https://user-images.githubusercontent.com/98807506/171565945-5a4e4ec0-fa18-43e7-a5fa-1e0e981e47d7.png" align="left">
<h1 align="left"> 성공적인 팀플을 위한 체크인/팀관리 서비스<br/>아무튼 출석!</h1>



<center>
<a href="https://www.a-chool.com/"><img width="200" alt="modal1" src="https://user-images.githubusercontent.com/98807506/171565120-50a28594-4326-41ac-ab1e-4f099d9cfc16.png" align="left"></a>      
  
</center>


<br>
<br>
<br>
<br>



## 🗓 프로젝트 기간
- 2022년 4월 22일 ~ 2022년 6월 3일
- 1차 배포 : 2022년 5월 25일
- 최종 배포 : 2022년 6월 1일
- 유튜브 링크 : 

<br>

# 팀 구성
| 이름     | 깃허브 주소                                                | 포지션     |
|:--------:|:----------------------------------------------------------:|:-----------:|
| 이경태🔰 | [https://github.com/kyeongbong](https://github.com/kyeongbong)                     | Frontend     |
| 최경민   | [https://github.com/kyngmn](https://github.com/kyngmn)                     | Frontend     |
| 김호빈   | [https://github.com/hobit22](https://github.com/hobit22) | Backend     |
| 심현웅   | [https://github.com/hyun-woong](https://github.com/hyun-woong)                     | Backend |
| 김일권   | [https://github.com/jjems](https://github.com/jjems)                     | Backend |
| 장유진   | [https://github.com/A-Chool](https://github.com/A-Chool)                     | Designer |
| 전하경   | [https://github.com/A-Chool](https://github.com/A-Chool)                     | Designer |

<br>


# 핵심 기능 Key Feature

<details>
<summary>📚 그룹 채팅</summary>
<div markdown="1">
 <br>
  → 팀별 그룹 채팅 서비스를 지원
  <br> → 대화 내용은 저장되어, 페이지를 벗어났다 다시 돌아와도 유지
</div>
</details>

<details>
<summary>⏰ 공부 타이머</summary>
<div markdown="1">
<br>
  → 유저별 공부 시간을 기록할 수 있는 타이머 제공
  <br> → 타이머는 매일 오전 5시에 초기화 됨
  <br> → checkOut을 누르지 않은 유저의 경우 마지막 checkIn 시간에서 한 시간이 추가된 상태로 저장
</div>
</details>
<details>
<summary>🏆 랭킹 및 당근밭 콘텐츠</summary>
<div markdown="1">
 <br>
   → 유저의 일별 누적 공부시간을 기준으로 랭킹이 집계되며, 일별 공부시간에 비례해 당근 밭에 당근이 심어짐
</div>

</details>

<br>

## 📜 기술스택
|분류|기술|
| :-: |:- |
|Language|<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">|
|Framework|<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">|
|Build Tool|<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">|
|DB|<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">|
|Server|<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white"> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">|

## ✍️ Wireframe
<details>
<summary>Wireframe</summary>
<div markdown="1">       

<img width="804" alt="스크린샷 2022-05-02 오전 2 16 55" src="https://user-images.githubusercontent.com/98807506/166156916-484f604e-6a8c-411f-b657-6b1a5ee8b43e.png">
<img width="812" alt="스크린샷 2022-05-02 오전 2 21 36" src="https://user-images.githubusercontent.com/98807506/166157015-6cefbe94-3da0-49c0-9297-f92697a835e6.png">

</div>
</details>

## 🍀 Service Architecture
![서비스 아키텍처 (1)](https://user-images.githubusercontent.com/98807506/171093946-18023bcc-3f86-4621-84e5-75cb94320b2a.png)


## 🐳 ERD
<img width="1670" alt="스크린샷 2022-05-30 오후 6 53 35" src="https://user-images.githubusercontent.com/98807506/170968919-1de6cfec-af0e-4c40-923c-e15ebaf43f1e.png">

## 🏹 Team Trouble Shooting
1) 프로젝트 기획에 있어 해당 서비스의 당위성과 방향성을 정하는 것에 대한 어려움
→ 현재까지 작성한 기획 및 와이어프레임을 엎고, 다시 처음으로 돌아가 해당 프로젝트의 아이덴티티를 재정의함으로서 방향성과 기획의도를 재적립
→ 와이어프레임을 재작성하며, 방향성을 벗어나려 할 때마다 올바른 방향으로 돌아올 수 있도록 나침반 역할을 수행

2) ERD 설계시 어떻게 연관관계를 맺어야 보다 나은 설계를 할 수 있는지에 대한 고찰
→ 다른 동기분들과의 집단 지성 + 여러 개의 ERD를 그려보고, 서로 의견을 공유하며 수정
→ 실제 코드를 작성하면서, 추가적으로 발생하는 미흡한 부분을 보완 및 수정

## 📌 Personal Trouble Shooting
<a href="https://github.com/A-Chool/BE/wiki/%5BHobinKim%5DTrouble-Shooting" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=김호빈 &color=08CE5D&style=for-the-badge&>"/></a>
<a href="https://github.com/A-Chool/BE/wiki/%5BHyunWoong%5DTrouble-Shooting" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=심현웅 &color=08CE5D&style=for-the-badge&>"/></a>
