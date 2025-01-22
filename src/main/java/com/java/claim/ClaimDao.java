package com.java.claim;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public interface ClaimDao {

	List<Claim> showClaimDetailsDao();

	String searchClaim(String claimId);

	//String updateClaim(String claimId, String insuranceId, double procedureCharge);

	String addClaimViewfield(Claim claim, ClaimViewfield viewfield);

	public String generateClaimId();

}
