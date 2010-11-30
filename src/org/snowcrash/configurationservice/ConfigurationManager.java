package org.snowcrash.configurationservice;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;


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
			dao.read( CritterTemplate.class );
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
