package ru.two.gis.web.service.abstr;

import ru.two.gis.web.model.Filial;

import java.util.List;

public interface TwoGisService {

	List<Filial> getFilialsByFieldOfActivity(String fieldOfActivity);
}
