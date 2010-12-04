package org.snowcrash.utilities;

import java.lang.reflect.Field;


public class CloningUtility
{
	private CloningUtility()
	{
		// -- Static class.
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T clone( T bean )
	{
		T clone = null;
		
		try
		{
			clone = (T) bean.getClass().newInstance();
			
			for ( Class<?> clazz = bean.getClass(); !Object.class.equals( clazz ); clazz = clazz.getSuperclass() )
			{
				for ( Field field : clazz.getDeclaredFields() )
				{
					field.setAccessible( true );
					field.set( clone, field.get( bean ) );
					field.setAccessible( false );
				}
			}
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return clone;
	}
}
