package ca.mcgill.ecse321.MuseumBackend.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.MuseumBackend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "*")
@RestController
public class LoanController{
  
  @Autowired
  private LoanService service;

}