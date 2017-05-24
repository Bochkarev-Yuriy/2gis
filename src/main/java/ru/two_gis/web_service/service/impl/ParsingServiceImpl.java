package ru.two_gis.web_service.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.two_gis.web_service.model.Filial;
import ru.two_gis.web_service.service.abstr.ParsingService;

@Service
@PropertySource(value = {"classpath:2gis.properties"})
public class ParsingServiceImpl implements ParsingService {

	@Autowired
	private Environment environment;

	@Override
	public Filial getFilialById(Long idFilial) {

		JSONObject jsonObject = new JSONObject(new RestTemplate().getForObject(getUrlProfile(idFilial), String.class));

		return new Filial(
				jsonObject.getString("name"),
				jsonObject.optString("city_name") + ", " + jsonObject.getString("address"),
				jsonObject.getString("rating") != null ? jsonObject.getDouble("rating") : 0);
	}

	@Override
	public Long getIdFilialByCity(String fieldOfActivity, String city) {

		JSONObject jsonObject = new JSONObject(new RestTemplate().getForObject(getUrlSearch(fieldOfActivity, city), String.class));

		if (jsonObject != null & jsonObject.get("response_code").equals("200")) {
			JSONObject jo = jsonObject.getJSONArray("result").getJSONObject(0);

			if (jo != null & (!jo.isNull("reviews_count") & !jo.isNull("address"))) {
				return jo.getLong("id");
			}
		}
		return null;
	}

	@Override
	public Filial getFilialByCity(String fieldOfActivity, String city) {
		return getFilialById(getIdFilialByCity(fieldOfActivity, city));
	}


	private String getUrlSearch(String fieldOfActivity, String city) {

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

		return customUrl.toString();
	}

	private String getUrlProfile(Long id) {

		StringBuilder customUrl = new StringBuilder();
		customUrl.append(environment.getRequiredProperty("2gis.parameter.profile"));

		customUrl.append("&id=");
		customUrl.append(id);

		customUrl.append("&version=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.version"));

		customUrl.append("&key=");
		customUrl.append(environment.getRequiredProperty("2gis.parameter.key"));

		return customUrl.toString();
	}

}
