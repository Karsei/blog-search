# blog-search

블로그 검색 서비스 API

* 헥사고날 아키텍처 방식으로 설계
* 이벤트 소싱과 CQRS 를 비스무리 간단히 구현해본 프로젝트
* 스케줄러로 검색 키워드 이벤트에서 조회 모델을 만들어 제공하도록 구현

## Spec

* OpenJDK 17
* Spring Boot 3.0.4

## Endpoints

### [GET] 블로그 검색 API

```
http://localhost:8080/search
```

* **query** - 질의어
* sort - 정렬 (accuracy, recency)
* page - 페이지 번호 (1~50)
* size - 페이지별 출력 개수 (1~50)

### [GET] 키워드 조회수 검색 API

```
http://localhost:8080/top-keywords
```

* size - 키워드 출력 개수 (1~10)