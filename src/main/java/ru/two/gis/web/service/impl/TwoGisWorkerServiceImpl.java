package ru.two.gis.web.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.two.gis.web.service.abstr.TwoGisWorkerService;
import ru.two.gis.web.model.Filial;
import ru.two.gis.web.util.UrlConstant;

@Service
@PropertySource(value = {"classpath:2gis.properties"})
class TwoGisWorkerServiceImpl implements TwoGisWorkerService {

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

		StringBuilder urlSearch = new StringBuilder();
		urlSearch.append(UrlConstant.SEARCH);

		urlSearch.append("&what=");
		urlSearch.append(fieldOfActivity);

		urlSearch.append("&where=");
		urlSearch.append(city);

		urlSearch.append("&version=");
		urlSearch.append(environment.getRequiredProperty("2gis.parameter.version"));

		urlSearch.append("&key=");
		urlSearch.append(environment.getRequiredProperty("2gis.parameter.key"));

		urlSearch.append("&sort=");
		urlSearch.append(environment.getRequiredProperty("2gis.parameter.sort"));

		urlSearch.append("&pagesize=");
		urlSearch.append(environment.getRequiredProperty("2gis.parameter.pagesize"));

		return urlSearch.toString();
	}

	private String getUrlProfile(Long id) {

		StringBuilder urlProfile = new StringBuilder();
		urlProfile.append(UrlConstant.PROFILE);

		urlProfile.append("&id=");
		urlProfile.append(id);

		urlProfile.append("&version=");
		urlProfile.append(environment.getRequiredProperty("2gis.parameter.version"));

		urlProfile.append("&key=");
		urlProfile.append(environment.getRequiredProperty("2gis.parameter.key"));

		return urlProfile.toString();
	}
}
