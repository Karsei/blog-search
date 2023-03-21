# blog-search

## Spec

* OpenJDK 17
* Spring Boot 3.0.4

## Endpoints

### 블로그 검색 API

```
[GET] http://localhost:8080/search
```

* **query** - 질의어
* sort - 정렬 (accuracy, recency)
* page - 페이지 번호 (1~50)
* size - 페이지별 출력 개수 (1~50)

### 키워드 조회수 검색 API

```
[GET] http://localhost:8080/top-keywords
```

* size - 키워드 출력 개수 (1~10)

## Used external libraries

* `Lombok` - 코드를 자동완성하기 위해 사용
* `OpenFeign` - Interface 를 이용하여 API 를 쉽게 호출할 수 있도록 사용