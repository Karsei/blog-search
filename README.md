# blog-search

## Spec

* OpenJDK 17
* Spring Boot 3.0.4

## Download

[app.jar](https://github.com/Karsei/bs/releases/download/1.0.2/app.jar)

### Usage

API 키는 내부에 포함되어 있습니다.

```shell
$ java -jar app.jar
```

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

## Used external libraries

* `h2` - H2 데이터베이스 사용
* `lombok` - 코드를 자동완성하기 위해 사용
* `openfeign` - API 를 쉽게 호출할 수 있도록 사용
* `circuitbreaker-resilience4j` - 장애 시 바로 fallback 을 위해 사용
