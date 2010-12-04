package org.snowcrash.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


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
			Constructor<?> constructor = bean.getClass().getDeclaredConstructor();
			constructor.setAccessible( true );
			clone = (T) constructor.newInstance();
			constructor.setAccessible( false );
			
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
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		return clone;
	}
}
