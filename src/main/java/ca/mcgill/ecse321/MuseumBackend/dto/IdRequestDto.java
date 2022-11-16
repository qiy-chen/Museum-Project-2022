package ca.mcgill.ecse321.MuseumBackend.dto;

public class IdRequestDto {

  private Integer id;

  
  public IdRequestDto() {}
  public IdRequestDto(Integer id) {
    this.id = id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getId() {
    return id;
  }
 
  
}
