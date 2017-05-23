package ru.two_gis.web_service.service.abstr;


import ru.two_gis.web_service.model.Filial;

import java.util.List;

public interface MainService {

	List<Filial> start(String fieldOfActivity);
}
