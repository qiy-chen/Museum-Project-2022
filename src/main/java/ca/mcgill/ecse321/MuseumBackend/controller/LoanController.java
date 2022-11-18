package ca.mcgill.ecse321.MuseumBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.service.LoanService;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class LoanController {
  @Autowired
  private LoanService service;
  
  @PostMapping(value = {"/loans", "/loans/"})
  public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody LoanRequestDto loanRequestDto)throws IllegalArgumentException{
    LoanResponseDto a = service.createLoan(loanRequestDto);
    return new ResponseEntity<LoanResponseDto>(a, HttpStatus.CREATED);
  }
  
  @GetMapping(value = {"/loans/{id}", "/loans/{id}/"})  
  public ResponseEntity<LoanResponseDto> getArtworkById(@PathVariable("id") int id) {
     Loan a = service.getLoan(id);
     LoanResponseDto artDto = convertToDto(a);
     return new ResponseEntity<LoanResponseDto>(artDto, HttpStatus.OK);
  } 
  
  private LoanResponseDto convertToDto(Loan a) {
    if (a == null) {
      throw new IllegalArgumentException("There is no such Artwork!");
    }
    LoanResponseDto artworkDto = new LoanResponseDto(a);
    return artworkDto;
  }
  
}
