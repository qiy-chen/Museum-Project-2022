package ca.mcgill.ecse321.MuseumBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.service.LoanService;

@RestController
public class LoanController {
  @Autowired
  LoanService loanService;
  
  @GetMapping
  public ResponseEntity<LoanResponseDto> getLoanByLoanId(@PathVariable int id){
    Loan loan = loanService.getLoanById(id);
  }
  
}
