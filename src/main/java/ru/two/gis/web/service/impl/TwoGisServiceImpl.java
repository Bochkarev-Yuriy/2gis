package ru.two.gis.web.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.two.gis.web.util.Location;
import ru.two.gis.web.service.abstr.TwoGisService;
import ru.two.gis.web.service.abstr.TwoGisWorkerService;
import ru.two.gis.web.model.Filial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class TwoGisServiceImpl implements TwoGisService {

	private final static Logger logger = Logger.getLogger(TwoGisWorkerServiceImpl.class);

	@Autowired
	private TwoGisWorkerService twoGisWorkerService;

	@Override
	public List<Filial> getFilialsByFieldOfActivity(String fieldOfActivity) {

		List<Filial> filialsByCity = new CopyOnWriteArrayList<>();
		List<Future> futures = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(Location.SIBERIAN_DISTRICT.size());

		for (String city : Location.SIBERIAN_DISTRICT) {
			Future future = executorService.submit(new Thread(() -> {
				Filial filial = twoGisWorkerService.getFilialByCity(fieldOfActivity, city);
				if (filial.getRating() != 0) {
					filialsByCity.add(filial);
				}
			}));
			futures.add(future);
		}

		futures.forEach(future -> {
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Failed future.get() in class TwoGisServiceImpl");
			}
		});

		filialsByCity.sort((Filial obj1, Filial obj2) -> obj2.getRating().compareTo(obj1.getRating()));
		return filialsByCity;
	}
}
