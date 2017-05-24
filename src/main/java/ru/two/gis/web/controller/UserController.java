package ru.two.gis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.two.gis.web.service.abstr.TwoGisService;
import ru.two.gis.web.model.Filial;

import java.util.List;

@RestController
@RequestMapping()
public class UserController {

	@Autowired
	private TwoGisService twoGisService;

	@GetMapping(value = {"/{fieldOfActivity}"})
	public ResponseEntity<List<Filial>> getFilials(@PathVariable String fieldOfActivity) {
		return new ResponseEntity<>(twoGisService.getFilialsByFieldOfActivity(fieldOfActivity), HttpStatus.OK);
	}
}
