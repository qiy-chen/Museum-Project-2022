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

@CrossOrigin(origins = "*")
@RestController
public class LoanController {
  @Autowired
  LoanService loanService;
  
  @GetMapping("/loan/{id}")
  public ResponseEntity<LoanResponseDto> getLoanByLoanId(@PathVariable int id){
    Loan loan = loanService.getLoanByLoanId(id);
    return new ResponseEntity<LoanResponseDto>(new LoanResponseDto(loan), HttpStatus.OK);
  }
  @PostMapping("/loan")
  public Loan createLoan(@RequestBody Loan loan){
    /*Loan loan = loanRequest.toModel();
    Loan createdLoan = loanService.createLoan(loan);
    LoanResponseDto response = new LoanResponseDto(createdLoan);
    return new ResponseEntity<LoanResponseDto>(response, HttpStatus.CREATED);*/
    Loan response = loanService.createLoan(loan);
    return response;
  }
  
}
