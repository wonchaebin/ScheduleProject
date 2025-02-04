 # 일정 관리 앱
 ## API 목록

| Method | Path                | RequestParam | Request                              | Response                                                         | Description | StatusCode             |
|--------|---------------------|--------------|--------------------------------------|------------------------------------------------------------------|-------------|------------------------|
| GET    | /api/schedules      | -            | -                                    | [ {"id": 1, "task" :, "author" :, "createdAT" :, "updateAt" :} ] | 다건 조회       | 200 Ok                 |
| GET    | /api/schedules/{id} | id           | -                                    | {"id": 1, "task" :, "author" :, "createdAT" :, "updateAt" :)}    | 단건 조회       | 200 Ok / 404 Not Found |
| POST   | /api/schedules      | -            | {"task" :, "author" :, "password" :} | {"id": 1, "task" :, "author" :, "createdAT" :, "updateAt" :)}    | 일정 생성       | 200 Ok                 |
| PUT    | /api/schedules/{id} | id           | {"task" :, "author" :, "password" :} | {"id": 1, "task" :, "author" :, "createdAT" :, "updateAt" :)}    | 일정 수정       | 200 Ok / 404 Not Found |
| DELETE | /api/schedules/{id} | id           | {"password" :}                       | -                                                                | 일정 삭제       | 200 Ok / 404 Not Found |

## ERD

![ERD](/images/img.png)