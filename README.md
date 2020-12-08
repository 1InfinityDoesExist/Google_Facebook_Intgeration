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
In pom.xml add. 
<!-- https://mvnrepository.com/artifact/javax.mail/javax.mail-api -->
&lt;dependency&gt;
    &lt;groupIdgt;javax.mail&lt;/groupIdgt;
    &lt;artifactIdgt;javax.mail-api&lt;/artifactIdgt;
    &lt;versiongt;1.6.2&lt;/versiongt;
&lt;/dependencygt;



Error : Spring Data Redis - Could not safely identify store assignment for repository candidate interface ****.

In application.properties add 
spring.data.redis.repositories.enabled= false
 
 
<code>
