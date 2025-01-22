package com.java.healthcare.idao;

import java.util.List;

import com.java.healthcare.entity.ProviderAvailability;

public interface IAvailabilityDao {
	String createProviderAvailability(ProviderAvailability providerAvailability);
	List<ProviderAvailability> showAvailabilityDetails();
	String searchProviderAvailabilityById(String availabilityId);
	String updateProviderAvailability(ProviderAvailability updateAvailability);
	String cancelProviderAvailability(String availabilityId);
	ProviderAvailability searchProviderAvailability(String availabilityId);
}
