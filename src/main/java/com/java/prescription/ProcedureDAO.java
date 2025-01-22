package com.java.prescription;

import java.util.List;

public interface ProcedureDAO {
	
	String addProcedure(Procedure procedure);
	List<Procedure> showProcedureDetails();

}
