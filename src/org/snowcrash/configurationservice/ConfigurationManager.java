package org.snowcrash.configurationservice;

import java.util.Arrays;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;


public class ConfigurationManager implements IConfigurationManager
{
	private DAO dao = DAOFactory.getDAO();

	public CritterTemplate createCritterTemplate( CritterPrototype prototype, String name )
	{
		CritterTemplate template = new CritterTemplate( prototype, name );
		
		try
		{
			dao.create( template );
		}
		catch (DAOException e)
		{
			throw new RuntimeException( e );
		}
		
		return template;
	}

	public void updateCritterTemplate(CritterTemplate template)
	{
		try
		{
			dao.update(template);
		}
		catch (DAOException e)
		{
			throw new RuntimeException( e );
		}
	}

	public void deleteCritterTemplate(CritterTemplate template)
	{
		try
		{
			dao.delete(template);
		}
		catch (DAOException e)
		{
			throw new RuntimeException( e );
		}
	}
	
	public CritterTemplate getCritterTemplateByName(String name)
	{
		CritterTemplate result = null;
		
		for ( CritterTemplate template : getCritterTemplates() )
		{
			if ( template.getName().equals( name ) )
			{
				result = template;
				break;
			}
		}
		
		return result;
	}
	
	public CritterTemplate[] getCritterTemplates()
	{
		CritterTemplate[] results = null;
		
		try
		{
			DatabaseObject[] arr = dao.read( CritterTemplate.class );
			results = new CritterTemplate[ arr.length ];
			
			for ( int i = 0; i < arr.length; i++ )
			{
				results[i] = ( arr[i] instanceof CritterTemplate ) ? (CritterTemplate) arr[i] : null;
			}
		}
		catch (DAOException e)
		{
			throw new RuntimeException( e );
		}
		
		return results;
	}

	public Critter createCritterFromTemplate()
	{
		return null;
	}
}
