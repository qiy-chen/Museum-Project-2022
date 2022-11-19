package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @PutMapping(value = {"/artworks/update/{id}", "/artworks/update/{id}/"})
  public ResponseEntity<ArtworkResponseDto> updateArtwork(
      @PathVariable int id,
      @Valid @RequestBody ArtworkRequestDto artworkRequestDto
    )   throws IllegalArgumentException {
    Artwork a = service.updateFields(id, artworkRequestDto.getArtworkName(), artworkRequestDto.getValue(), artworkRequestDto.getIsLoanable());
    return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.OK);
}
  
  @DeleteMapping(value= {"/artworks/delete/{id}", "/artworks/delete/{id}/"})
  public ResponseEntity<ArtworkResponseDto> deleteArtwork(
      @PathVariable int id
      )   throws IllegalArgumentException {
      service.deleteArtwork(id);
      return new ResponseEntity<ArtworkResponseDto>(HttpStatus.OK);
  }
  
  /**
  *
  * @param artId
  * @param roomId
  * @return ArtworkDto
  * @throws IllegalArgumentException
  */
  @PutMapping(value = {"/artworks/move/{id}", "/artworks/move/{id}/"})
  public ResponseEntity<ArtworkResponseDto> moveArtwork(
    @PathVariable int id,
    @Valid @RequestBody ArtworkRequestDto artworkRequestDto
    )   throws IllegalArgumentException {
    Artwork a = service.moveArtwork(id, artworkRequestDto.getRoomId());
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
* @param 
* @return
*/
@GetMapping(value = { "/artworks/ByRoom", "/artworks/ByRoom/" })
public ResponseEntity<List<ArtworkResponseDto>> getAllArtworkByRoomId(
    @Valid @RequestBody ArtworkRequestDto artworkRequestDto) {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksByRoomId(artworkRequestDto.getRoomId())) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkResponseDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = { "/artworks/OnStorage", "/artworks/OnStorage/" })
public ResponseEntity<List<ArtworkResponseDto>> getAllArtworkOnStorage() {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksOnStorage()) {
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
