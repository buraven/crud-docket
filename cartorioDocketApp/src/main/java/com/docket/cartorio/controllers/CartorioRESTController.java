package com.docket.cartorio.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docket.cartorio.repository.CartorioRepository;
import com.docket.cartorio.models.Cartorio;

@RestController
public class CartorioRESTController {

	@Autowired
	private CartorioRepository repository;

	@RequestMapping(value = "/cartorios", method = RequestMethod.GET)
    public List<Cartorio> Get() {
        return (List<Cartorio>) repository.findAll();
    }
	
	@RequestMapping(value = "/cartorios/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cartorio> GetById(@PathVariable(value = "id") long id)
    {
		Optional<Cartorio> cartorio = repository.findById(id);
        if(cartorio.isPresent())
            return new ResponseEntity<Cartorio>(cartorio.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
