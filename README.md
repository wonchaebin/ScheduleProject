# CH3 일정관리 앱 만들기 과제

## 1. API 명세 및 ERD 작성
### 🔎API 명세서
![image](https://github.com/user-attachments/assets/faa8f0fd-a316-4eb4-a709-92e0b5a4e8b7)


### 🔎ERD
![image](https://github.com/user-attachments/assets/7306f533-5602-453f-a2e6-e43bb63c5099)

## 2. 일정 생성 및 조회
### 🔎기능
#### 일정 생성
- 할일(description), 작성자명(userName), 비밀번호(password), 작성/수정일(createdAt/updatedAt) 저장
- 각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리
- 최초 입력 시, 수정일은 작성일과 동일

#### 전체 일정 조회
- 수정일과 작성자명을 바탕으로 등록된 일정 목록 전부 조회
- 조건 중 한 가지만 충족하거나 둘 다 충족 안할 수도, 두 가지 모두 충족할 수 있음
- 수정일 기준 내림차순으로 정렬하여 조회

#### 선택 일정 조회
- 선택한 일정 단건의 정보 조회
- 일정의 고유 식별자(ID) 사용하여 조회

## 3. 일정 수정 및 삭제
### 🔎기능
#### 선택한 일정 수정
- 선택한 일정 내용 중 할일과 작성자명만 수정 가능
- 서버에 일정 수정 요철 시 비밀번호 함께 전달
- 작성일은 변경하지 못하고, 수정일은 수정 완료 시 수정한 시점으로 변경

#### 선택한 일정 삭제
- 선택한 일정을 삭제할 수 있음
- 서버에 일정 수정을 요청할 때 비밀번호 함께 전달
