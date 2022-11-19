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
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class LoanController {
  @Autowired
  private LoanService service;
  
  @PostMapping(value = {"/loans", "/loans/"})
  public ResponseEntity<LoanResponseDto> createLoan(@RequestBody LoanRequestDto loanRequestDto)throws IllegalArgumentException{
    LoanResponseDto a = service.createLoan(loanRequestDto);
    return new ResponseEntity<LoanResponseDto>(a, HttpStatus.CREATED);
  }
  
  @GetMapping(value = {"/loans/{id}", "/loans/{id}/"})  
  public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable("id") int id) {
     Loan a = service.getLoan(id);
     LoanResponseDto loanDto = convertToDto(a);
     return new ResponseEntity<LoanResponseDto>(loanDto, HttpStatus.OK);
  } 
  
  @PostMapping(value = {"/loans/delete/{id}/", "/loans/delete/{id}"})
  public ResponseEntity<LoanResponseDto> deleteLoan(@PathVariable("id") int loanId) throws IllegalArgumentException{
    Loan loan = service.deleteLoan(loanId);
    return new ResponseEntity<LoanResponseDto>(convertToDto(loan), HttpStatus.OK);
  }
  @PostMapping(value = {"/loans/deny/{id}", "/loans/deny/{id}"})
  public ResponseEntity<LoanResponseDto> denyLoan(@PathVariable("id") int loanId) throws IllegalArgumentException{
    Loan loan = service.denyLoan(loanId);
    return new ResponseEntity<LoanResponseDto>(convertToDto(loan), HttpStatus.OK);
  }
  @PostMapping(value = {"/loans/approve/{id}", "/loans/approve/{id}"})
  public ResponseEntity<LoanResponseDto> approveLoan(@PathVariable("id") int loanId) throws IllegalArgumentException{
    Loan loan = service.approveLoan(loanId);
    return new ResponseEntity<LoanResponseDto>(convertToDto(loan), HttpStatus.OK);
  }
  @PostMapping(value = {"/loans/return/{id}", "/loans/return/{id}"})
  public ResponseEntity<LoanResponseDto> returnArtworkandEndLoan(@PathVariable("id") int loanId) throws IllegalArgumentException{
    Loan loan = service.returnArtworkandEndLoan(loanId);
    return new ResponseEntity<LoanResponseDto>(convertToDto(loan), HttpStatus.OK);
  }
  
  @GetMapping("/loans")
  public ResponseEntity<List<LoanResponseDto>> getAllTickets() {
    List<Loan> listTickets = service.getAllLoans();
    List<LoanResponseDto> listResponseLoans = new ArrayList<LoanResponseDto>();
    for (Loan ticket:listTickets) {
      listResponseLoans.add(new LoanResponseDto(ticket));
    }
    return new ResponseEntity<List<LoanResponseDto>>(listResponseLoans, HttpStatus.OK);
  }
  
  
  private LoanResponseDto convertToDto(Loan a) {
    if (a == null) {
      throw new IllegalArgumentException("There is no such Loan!");
    }
    LoanResponseDto loanDto = new LoanResponseDto(a);
    return loanDto;
  }
  
}
