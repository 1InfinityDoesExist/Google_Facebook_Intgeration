# Google_Facebook_Intgeration



Note : The constructor PageRequest(int, int, Sort.Direction, String) is undefined
      Solution : PageRequest.of(PageNumber, PageSize, Sort.by(Sort.Direction.ASC, "id"))
      Never pass (Pageable pagebale , Sort sort) throws error
Pageable 
Sortin
Page
Slice