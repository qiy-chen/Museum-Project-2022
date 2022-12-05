package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.MuseumBackend.dto.DisplayDto;
import ca.mcgill.ecse321.MuseumBackend.dto.StorageDto;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.service.RoomService;

@CrossOrigin(origins = "*")
@RestController
public class RoomController {

  @Autowired
  private RoomService service;

  /**
  *
  * @param name
  * @param roomNumber
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/display/", "/display"})
  public ResponseEntity<DisplayDto> createDisplay(
      @RequestBody DisplayDto request
    )   throws IllegalArgumentException {

    Display d = service.createDisplayRoom(request.getRoomNumber(), request.getMaxArtworks(), request.getMuseumId());
    return new ResponseEntity<DisplayDto>(convertToDto(d), HttpStatus.CREATED);
}
  
  /**
  *
  * @param name
  * @param roomNumber
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/storage/", "/storage"})
  public ResponseEntity<StorageDto> createStorage(
      @RequestBody StorageDto request
    )   throws IllegalArgumentException {

    Storage s = service.createStorageRoom(request.getRoomNumber(), request.getMuseumId());
    return new ResponseEntity<StorageDto>(convertToDto(s), HttpStatus.CREATED);
}
  
  /**
  *
  * @param id
  * @return
  */
  @GetMapping(value = {"/display/{id}", "/display/{id}/"})  
  public ResponseEntity<DisplayDto> getDisplayById(@PathVariable int id) 
      throws IllegalArgumentException {
    
     Display d = service.getDisplayById(id);
     return new ResponseEntity<DisplayDto>(convertToDto(d), HttpStatus.OK);
  }
  
  /**
  *
  * @param id
  * @return
  */
  @GetMapping(value = {"/storage/{id}", "/storage/{id}/"})  
  public ResponseEntity<StorageDto> getStorageById(@PathVariable int id) 
      throws IllegalArgumentException {
    
     Storage s = service.getStorageById(id);
     return new ResponseEntity<StorageDto>(convertToDto(s), HttpStatus.OK);
  }
  
  @DeleteMapping(value = {"/display/delete/{id}", "/display/delete/{id}/"})  
  public ResponseEntity<DisplayDto> deleteDisplay(@PathVariable int id) 
      throws IllegalArgumentException {
    
     service.deleteDisplayRoom(id);
     return new ResponseEntity<DisplayDto>(HttpStatus.OK);
  }

  @GetMapping(value = "/display")
public ResponseEntity<List<DisplayDto>> getAllDisplays() {
  List<DisplayDto> displayDto = new ArrayList<>();
  for (Display d : service.getAllDisplays()) {
    displayDto.add(convertToDto(d));
  }
  return new ResponseEntity<List<DisplayDto>>(displayDto, HttpStatus.OK);
}

@GetMapping(value = "/storage")
public ResponseEntity<List<StorageDto>> getAllStorages() {
  List<StorageDto> storageDto = new ArrayList<>();
  for (Storage s : service.getAllStorages()) {
    storageDto.add(convertToDto(s));
  }
  return new ResponseEntity<List<StorageDto>>(storageDto, HttpStatus.OK);
}
  
  //helper, convert from display to display dto
private DisplayDto convertToDto(Display d) {
    
    if (d == null) {
      throw new IllegalArgumentException("There is no such Display Room!");
  }
    DisplayDto displayDto = new DisplayDto(d);
    return displayDto;
}

//helper, convert from storage to storage dto
private StorageDto convertToDto(Storage s) {
  
  if (s == null) {
    throw new IllegalArgumentException("There is no such Storage Room!");
}
  StorageDto storageDto = new StorageDto(s);
  return storageDto;
}
}
