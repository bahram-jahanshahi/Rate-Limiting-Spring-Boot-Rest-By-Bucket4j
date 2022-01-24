# Rate Limiting a Spring Boot Rest API
This project aims to practice a best practice rate limiting a Rest API.  
The reference of this practice is this [page](https://www.baeldung.com/spring-bucket4j).

## Bucket4j Quick Start
Add this dependency to your project
```xml
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.0.0</version>
</dependency>
```
For `n` tries in `t` seconds, config **_Bucket4j_** like this:
```java
Refill refill = Refill.intervally(tries, Duration.ofSeconds(seconds));
Bandwidth bandwidth = Bandwidth.classic(tries, refill);
bucket = Bucket.builder()
        .addLimit(bandwidth)
        .build();
```
and for each try just call the `tryConsume` method.
```java
bucket.tryConsume(1);
```
then you can run this test:
```java
@Test
void given_10_try_in_1_minute_when_try_10_times_then_the_11th_try_should_return_false() {
    SimpleRateLimiterService simpleRateLimiterService = new SimpleRateLimiterService(10, 60);
    for (int i = 0; i < 10; i++) {
        assertThat(simpleRateLimiterService.tryCall()).isEqualTo(true);
    }
    assertThat(simpleRateLimiterService.tryCall()).isEqualTo(false);
}
```
