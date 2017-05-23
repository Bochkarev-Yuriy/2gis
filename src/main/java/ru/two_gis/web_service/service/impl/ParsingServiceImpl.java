package ru.two_gis.web_service.service.impl;


import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.two_gis.web_service.model.Filial;
import ru.two_gis.web_service.service.abstr.ParsingService;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;


@Service
@PropertySource(value = {"classpath:2gis.properties"})
public class ParsingServiceImpl implements ParsingService {
	private final static Logger logger = Logger.getLogger(ParsingServiceImpl.class);

	@Autowired
	private Environment environment;


	@Override
	public Filial getFilialById(Long idFilial) {

		Filial filial = null;

		try {
			JSONObject jsonObject = new JSONObject(IOUtils.toString(new URL(new String(getUrlProfile(idFilial))),
					Charset.forName(environment.getRequiredProperty("2gis.parameter.encoding"))));

			filial = new Filial(
					jsonObject.getString("name"),
					jsonObject.optString("city_name") + ", " + jsonObject.getString("address"),
					jsonObject.getString("rating") != null ? jsonObject.getDouble("rating") : 0);

		} catch (IOException e) {
			logger.error("Failed url " + getUrlProfile(idFilial));
			e.printStackTrace();
		}
		return filial;
	}


	@Override
	public Long getIdFilialByCity(String fieldOfActivity, String city) {

		JSONObject jsonObject = null;
		Long idFilial = null;


		try {
			jsonObject = new JSONObject(IOUtils.toString(new URL(new String(getUrlSearch(fieldOfActivity, city))),
					Charset.forName(environment.getRequiredProperty("2gis.parameter.encoding"))));
		} catch (IOException e) {
			logger.error("Failed url " + getUrlSearch(fieldOfActivity, city));
			e.printStackTrace();
		}


		if (jsonObject != null & jsonObject.get("response_code").equals("200")) {
			JSONObject jo = jsonObject.getJSONArray("result").getJSONObject(0);

			if (jo != null & (!jo.isNull("reviews_count") & !jo.isNull("address"))) {
				idFilial = jo.getLong("id");
			}

		}

		return idFilial;
	}

	@Override
	public Filial getFilialByCity(String fieldOfActivity, String city) {
		return getFilialById(getIdFilialByCity(fieldOfActivity, city));
	}


	private StringBuilder getUrlSearch(String fieldOfActivity, String city) {

		StringBuilder customUrl = new StringBuilder();
		customUrl.append(environment.getRequiredProperty("2gis.parameter.search"));

		customUrl.append("&what=");
		customUrl.append(fieldOfActivity);

		customUrl.append("&where=");
		customUrl.append(city);

		customUrl.append("&version=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.version"));

		customUrl.append("&key=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.key"));

		customUrl.append("&sort=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.sort"));

		customUrl.append("&pagesize=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.pagesize"));

		return customUrl;
	}

	private StringBuilder getUrlProfile(Long id) {

		StringBuilder customUrl = new StringBuilder();
		customUrl.append(environment.getRequiredProperty("2gis.parameter.profile"));

		customUrl.append("&id=");
		customUrl.append(id);

		customUrl.append("&version=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.version"));

		customUrl.append("&key=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.key"));

		return customUrl;
	}

}
