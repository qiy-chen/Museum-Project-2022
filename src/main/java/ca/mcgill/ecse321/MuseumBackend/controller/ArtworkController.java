package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkResponseDto;
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
  @PostMapping(value = {"/artworks", "/artworks/"})
  public ResponseEntity<ArtworkResponseDto> createArtwork(
      @Valid @RequestBody ArtworkRequestDto artworkRequestDto
    )   throws IllegalArgumentException {

    Artwork a = service.createArtwork(artworkRequestDto);
    
    return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.CREATED);
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
  @PostMapping(value = {"/artworks/update/{id}/", "/artworks/update/{id}"})
  public ResponseEntity<ArtworkResponseDto> updateArtwork(
    @PathVariable("id") int artId,
    @RequestParam String name,
    @RequestParam double value,
    @RequestParam boolean isLoanable
    )   throws IllegalArgumentException {
    Artwork a = service.updateFields(artId, name, value, isLoanable);
    return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.OK);
}
  
  @PostMapping(value= {"/artworks/delete/{id}/", "/artworks/delete/{id}"})
  public ResponseEntity<ArtworkResponseDto> deleteArtwork(
      @PathVariable("id") int artId
      )   throws IllegalArgumentException {
      Artwork a = service.deleteArtwork(artId);
      return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.OK);
  }
  
  /**
  *
  * @param artId
  * @param roomId
  * @return ArtworkDto
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/artworks/move/{id}/", "/artworks/move/{id}"})
  public ResponseEntity<ArtworkResponseDto> moveArtwork(
    @PathVariable("id") int artId,
    @RequestParam int roomId
    )   throws IllegalArgumentException {
    Artwork a = service.moveArtwork(artId, roomId);
    return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks", "/artworks/" })
public ResponseEntity<List<ArtworkResponseDto>> getAllArtwork() {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getAllArtwork()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkResponseDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/Available", "/artworks/Available/" })
public ResponseEntity<List<ArtworkResponseDto>> getAllArtworkNotAvailableForLoan() {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksNotAvailableForLoan()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkResponseDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/NotAvailable", "/artworks/NotAvailable/" })
public ResponseEntity<List<ArtworkResponseDto>> getAllArtworkAvailableForLoan() {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksAvailableForLoan()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkResponseDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/OnDisplay", "/artworks/OnDisplay/" })
public ResponseEntity<List<ArtworkResponseDto>> getAllArtworkOnDisplay() {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksOnDisplay()) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkResponseDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param id
* @return
*/
@GetMapping(value = {"/artworks/{id}", "/artworks/{id}/"})  
public ResponseEntity<ArtworkResponseDto> getArtworkById(@PathVariable("id") int id) {
   Artwork a = service.getArtwork(id);
   ArtworkResponseDto artDto = convertToDto(a);
   return new ResponseEntity<ArtworkResponseDto>(artDto, HttpStatus.OK);
}  


  private ArtworkResponseDto convertToDto(Artwork a) {
    
    if (a == null) {
      throw new IllegalArgumentException("There is no such Artwork!");
  }
    ArtworkResponseDto artworkDto = new ArtworkResponseDto(a);
    return artworkDto;
}
  
  //missing delete
  
}
