package ru.two_gis.web_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.two_gis.web_service.model.Filial;
import ru.two_gis.web_service.service.abstr.MainService;

import java.util.List;


@RestController
@RequestMapping()
public class UserController {

	@Autowired
	private MainService mainService;

	@GetMapping(value = {"/{fieldOfActivity}"})
	public ResponseEntity<List<Filial>> getFilials(@PathVariable String fieldOfActivity) {
		return new ResponseEntity<>(mainService.start(fieldOfActivity), HttpStatus.OK);
	}

}
