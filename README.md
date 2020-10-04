<pre>
# Google_Facebook_Intgeration



Issue : The constructor PageRequest(int, int, Sort.Direction, String) is undefined
      Solution : PageRequest.of(PageNumber, PageSize, Sort.by(Sort.Direction.ASC, "id"))
      Never pass (Pageable pagebale , Sort sort) throws error
      
 Pageable 
 Sorting
 Page
 Slice

 Facebook accessToken Generation
 Google accessToken Generation

 Validation and Verification of facebook accessToken.
 Validation and Verification of google accessToken.
 
 Build Error: The type javax.mail.internet.MimeMessage cannot be resolved

<!-- https://mvnrepository.com/artifact/javax.mail/javax.mail-api -->
<dependency>
    <groupId>javax.mail</groupId>
    <artifactId>javax.mail-api</artifactId>
    <version>1.6.2</version>
</dependency>



Error : Spring Data Redis - Could not safely identify store assignment for repository candidate interface ****.

In application.properties add 
spring.data.redis.repositories.enabled= false
 
 
<code>
