# Project for Education : ContactApp

본 프로젝트는 안드로이드 교육 과정에 있어서, 학습을 목적으로 만들어진 Android App 임을 밝힙니다!






---





Android 앱개발 숙련 프로젝트 18조 팀 과제 제출입니다.



[프로젝트 Notion 페이지](https://www.notion.so/18-bc8dc20c3f994a419f2b01b67552e78d)





## 참여 팀원

| 이름   | 담당 | Github 주소                   |
| ------ | ---- | ----------------------------- |
| 전지성 | 팀장 | https://github.com/PMETNT     |
| 이상훈 | 팀원 | https://github.com/2020304056 |
| 김민우 | 팀원 | https://github.com/mwkimm     |
| 김소현 | 팀원 | https://github.com/sinw212    |
| 전환휘 | 팀원 | https://github.com/dashboard  |



## 프로젝트 구성

### 시작 페이지 (SplashActivity)

<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/c3c80eb1-a5e2-4596-a016-d9c23aa93b68" width="200" height="400"/>

* Glide 라이브러리를 사용하여, 배경에 GIF 파일 띄우기
* 화면을 클릭하면 로그인 페이지로 이동





### 로그인 페이지 (SignInActivity)

<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/d021c5ce-d171-4b8f-abd0-9edde947f7d0" width="200" height="400"/>


* Glide 라이브러리를 사용하여, 배경에 GIF 파일 띄우기
* 이름, 암호명으로 로그인

### 회원가입 페이지 (SignUpActivity)

<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/ca47c7ad-9f26-4832-8b1b-48360a8fb631" width="200" height="400"/>



* 이름, 이메일, 비밀번호, 지역명(암호명), 능력, 전화번호 입력 > 예외처리
* 각 항목 입력시 TextWatcher 활용




### 메인 페이지 (MainActivity)
<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/6f52115f-ec5d-4319-8ecb-0cd2efdefdb6" width="200" height="400"/>
<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/badeed50-6758-4fa0-874c-2f47a153f127" width="200" height="400"/>
<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/9373240d-bbb9-464d-a494-c1ecc886d7f8" width="200" height="400"/>
<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/d77c0eb3-753c-44ca-bffd-8a51483b2360" width="200" height="400"/>

* 메인 화면 (TabLayout X ViewPager2)
* 갤러리 접근, 주소록 접근, 전화걸기 기능 권한 허용 여부 Dialog 출력
* 연락처 리스트 화면
* 더미데이터 RecyclerView 출력
* 홀/짝에 맞춰 LTR/RTL 출력
* 각 항목 클릭 시 DetailFragment 이동
* 메세지 보내기, 전화걸기 기능
* 왼쪽에서 오른쪽으로 Swipe 기능 활용하여 전화걸기 기능
* 좋아요 클릭 시, 좋아요 항목 상단 우선 위치 (이름순 정렬)
* 우측상단 GridView, ListView 타입 선택 가능
* ListView 아이콘 롱 클릭 시, 실제 연락처 불러오기 기능
* Floating 버튼 클릭 시, 연락처 추가 Dialog 출력
* 갤러리 연동
* 입력란 예외처리
* Notification 생성 가능 (시연을 위해 임의로 5분 > 5초로 수정)
* Notification 클릭 시, 연락처 리스트 화면 이동
* 연락처 추가 시, 이름순으로 자동 정렬되어 추가


### 마이 페이지, 디테일 페이지 (DetailFragment)
<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/d1ce85b7-1eed-4cf2-bb26-96c0fbcf6536" width="200" height="400"/>
<img src="https://github.com/Team18Contact/ContactApp/assets/139109345/a981e693-fa3a-4514-98e6-29df79847f90" width="200" height="400"/>


* 마이페이지 화면 (디테일페이지 View 재사용)
* 마이페이지 화면에서는 메세지, 콜 버튼 안 보이게 적용
* 로그인 한 계정 정보 출력


