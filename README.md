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
<code>
