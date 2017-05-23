package ru.two_gis.web_service.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.two_gis.web_service.model.Filial;
import ru.two_gis.web_service.service.abstr.MainService;
import ru.two_gis.web_service.service.abstr.ParsingService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.two_gis.web_service.config.Location.SIBERIAN_DISTRICT;

@Service
public class MainServiceImpl implements MainService {
	private final static Logger logger = Logger.getLogger(ParsingServiceImpl.class);

	@Autowired
	private ParsingService parsingService;


	@Override
	public List<Filial> start(String fieldOfActivity) {


		List<Filial> filialsByCity = new CopyOnWriteArrayList<>();
		List<Thread> threads = new ArrayList<>();

		for (String city : SIBERIAN_DISTRICT) {
			Thread thread = new Thread(() -> filialsByCity.add(parsingService.getFilialByCity(fieldOfActivity, city)));
			threads.add(thread);
			thread.start();
		}

		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				logger.error("Failed thread " + thread.getName());
				e.printStackTrace();
			}
		});

		filialsByCity.sort((Filial obj1, Filial obj2) -> obj2.getRating().compareTo(obj1.getRating()));
		return filialsByCity;
	}

}
