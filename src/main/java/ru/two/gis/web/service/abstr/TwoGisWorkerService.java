package ru.two.gis.web.service.abstr;

import ru.two.gis.web.model.Filial;

public interface TwoGisWorkerService {

	Long getIdFilialByCity(String fieldOfActivity, String city);

	Filial getFilialById(Long idFilial);

	Filial getFilialByCity(String fieldOfActivity, String city);
}
