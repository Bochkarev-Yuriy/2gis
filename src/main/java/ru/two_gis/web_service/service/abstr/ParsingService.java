package ru.two_gis.web_service.service.abstr;

import ru.two_gis.web_service.model.Filial;


public interface ParsingService {

	Long getIdFilialByCity(String fieldOfActivity, String city);

	Filial getFilialById(Long idFilial);

	Filial getFilialByCity(String fieldOfActivity, String city);

}
