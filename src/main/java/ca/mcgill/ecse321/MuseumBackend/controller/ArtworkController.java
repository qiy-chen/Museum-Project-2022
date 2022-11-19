package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
  @PostMapping(value = "/artwork")
  public ResponseEntity<ArtworkResponseDto> createArtwork(
     @RequestBody ArtworkRequestDto artworkRequestDto
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
  @PutMapping(value = "/artwork/{id}/")
  public ResponseEntity<ArtworkResponseDto> updateArtwork(
      @PathVariable int id,
      @RequestBody ArtworkRequestDto artworkRequestDto
    )   throws IllegalArgumentException {
    Artwork a = service.updateFields(id, artworkRequestDto.getArtworkName(), artworkRequestDto.getValue(), artworkRequestDto.getIsLoanable());
    return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.OK);
}
  
  @DeleteMapping(value= "/artwork/{id}")
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
  @PutMapping(value = "/artwork/room/{id}")
  public ResponseEntity<ArtworkResponseDto> moveArtwork(
    @PathVariable int id,
    @RequestBody ArtworkRequestDto artworkRequestDto
    )   throws IllegalArgumentException {
    Artwork a = service.moveArtwork(id, artworkRequestDto.getRoomId());
    return new ResponseEntity<ArtworkResponseDto>(convertToDto(a), HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = "/artwork")
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
@GetMapping(value = "/display/artworks")
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
@GetMapping(value = "/room/artworks/{roomId}")
public ResponseEntity<List<ArtworkResponseDto>> getAllArtworkByRoomId(
    @PathVariable int roomId) {
  List<ArtworkResponseDto> artDto = new ArrayList<>();
  for (Artwork art : service.getArtworksByRoomId(roomId)) {
      artDto.add(convertToDto(art));
  }
  return new ResponseEntity<List<ArtworkResponseDto>>(artDto, HttpStatus.OK);
}

/**
*
* @param 
* @return
*/
@GetMapping(value = "/storage/artworks")
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
@GetMapping(value = "/artwork/{id}")
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
