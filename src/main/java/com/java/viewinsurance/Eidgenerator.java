package com.java.viewinsurance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class Eidgenerator implements IdentifierGenerator{
	

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		
		SessionFactory sf=SessionHelper.getConnection();
		Session session =sf.openSession();
		Criteria cr=session.createCriteria(RecInsurance.class);
		
		List<RecInsurance> cList=cr.list();
		
		if(cList.size()==0){
			String strfound="I001";
			return strfound;
		}else {
			String strfound=cList.get(cList.size()-1).getInsuranceID();

			
			String sub=strfound.substring(1);
			
			int temp=Integer.parseInt(sub);
			
			temp=temp+1;
			
			strfound=String.format("I%03d", temp);
			
			return strfound;
			
		}
		
	}
	
	

}
