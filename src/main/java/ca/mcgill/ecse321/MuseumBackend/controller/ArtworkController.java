package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.service.ArtworkService;


@CrossOrigin(origins = "*")
@RestController
public class ArtworkController {
  
  @Autowired
  private ArtworkService service;

  /**
  *
  * @param name
  * @param rooId
  * @return ArtworkDto
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/artworks/", "/artworks"})
  public ArtworkDto createArtwork(
    @RequestParam String name,
    @RequestParam int roomId
    )   throws IllegalArgumentException {

    Artwork a = service.createArtwork(name, roomId);
    return convertToDto(a);
}
  
  /**
  *
  * @param artId
  * @param name
  * @param value
  * @param isLoanable
  * @return ArtworkDto
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/artworks/{id}/", "/artworks/{id}"})
  public ArtworkDto updateArtwork(
    @PathVariable("id") int artId,
    @RequestParam String name,
    @RequestParam double value,
    @RequestParam boolean isLoanable
    )   throws IllegalArgumentException {
    Artwork a = service.updateFields(artId, name, value, isLoanable);
    return convertToDto(a);
}
  
  /**
  *
  * @param artId
  * @param roomId
  * @return ArtworkDto
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/artworks/{id}/", "/artworks/{id}"})
  public ArtworkDto moveArtwork(
    @PathVariable("id") int artId,
    @RequestParam int roomId
    )   throws IllegalArgumentException {
    Artwork a = service.moveArtwork(artId, roomId);
    return convertToDto(a);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks", "/artworks/" })
public List<ArtworkDto> getAllArtwork() {
  List<ArtworkDto> artDto = new ArrayList<>();
  for (Artwork art : service.getAllArtwork()) {
      artDto.add(convertToDto(art));
  }
  return artDto;
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks", "/artworks/" })
public List<ArtworkDto> getAllArtworkAvailableForLoan() {
  List<ArtworkDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksAvailableForLoan()) {
      artDto.add(convertToDto(art));
  }
  return artDto;
}

/**
*
* @param id
* @return
*/
@GetMapping(value = {"/artworks/{id}", "/artworks/{id}/"})  
public ArtworkDto getArtworkById(@PathVariable("id") int id) {
   Artwork a = service.getArtwork(id);
   return convertToDto(a);
}  


  private ArtworkDto convertToDto(Artwork a) {
    
    if (a == null) {
      throw new IllegalArgumentException("There is no such Artwork!");
  }
    ArtworkDto artworkDto = new ArtworkDto(a);
    return artworkDto;
}
  
}
