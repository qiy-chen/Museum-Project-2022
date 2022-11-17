package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ArtworkDto> createArtwork(
    @RequestParam String name,
    @RequestParam int roomId
    )   throws IllegalArgumentException {

    Artwork a = service.createArtwork(name, roomId);
    return new ResponseEntity<ArtworkDto>(convertToDto(a), HttpStatus.CREATED);
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
  public ResponseEntity<ArtworkDto> updateArtwork(
    @PathVariable("id") int artId,
    @RequestParam String name,
    @RequestParam double value,
    @RequestParam boolean isLoanable
    )   throws IllegalArgumentException {
    Artwork a = service.updateFields(artId, name, value, isLoanable);
    return new ResponseEntity<ArtworkDto>(convertToDto(a), HttpStatus.OK);
}
  
  @PostMapping(value= {"/artworks/delete/{id}/", "/artworks/delete/{id}"})
  public ResponseEntity<ArtworkDto> deleteArtwork(
      @PathVariable("id") int artId
      )   throws IllegalArgumentException {
      Artwork a = service.deleteArtwork(artId);
      return new ResponseEntity<ArtworkDto>(convertToDto(a), HttpStatus.OK);
  }
  
  /**
  *
  * @param artId
  * @param roomId
  * @return ArtworkDto
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/artworks/{id}/", "/artworks/{id}"})
  public ResponseEntity<ArtworkDto> moveArtwork(
    @PathVariable("id") int artId,
    @RequestParam int roomId
    )   throws IllegalArgumentException {
    Artwork a = service.moveArtwork(artId, roomId);
    return new ResponseEntity<ArtworkDto>(convertToDto(a), HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks", "/artworks/" })
public ResponseEntity<List<ArtworkDto>> getAllArtwork() {
  List<ArtworkDto> artDto = new ArrayList<>();
  for (Artwork art : service.getAllArtwork()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/Available", "/artworks/Available/" })
public ResponseEntity<List<ArtworkDto>> getAllArtworkNotAvailableForLoan() {
  List<ArtworkDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksNotAvailableForLoan()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/NotAvailable", "/artworks/NotAvailable/" })
public ResponseEntity<List<ArtworkDto>> getAllArtworkAvailableForLoan() {
  List<ArtworkDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksAvailableForLoan()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/OnDisplay", "/artworks/OnDisplay/" })
public ResponseEntity<List<ArtworkDto>> getAllArtworkOnDisplay() {
  List<ArtworkDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksOnDisplay()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param id
* @return
*/
@GetMapping(value = {"/artworks/{id}", "/artworks/{id}/"})  
public ResponseEntity<ArtworkDto> getArtworkById(@PathVariable("id") int id) {
   Artwork a = service.getArtwork(id);
   return new ResponseEntity<ArtworkDto>(convertToDto(a), HttpStatus.OK);
}  


  private ArtworkDto convertToDto(Artwork a) {
    
    if (a == null) {
      throw new IllegalArgumentException("There is no such Artwork!");
  }
    ArtworkDto artworkDto = new ArtworkDto(a);
    return artworkDto;
}
  
  //missing delete
  
}
