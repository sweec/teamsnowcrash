package org.snowcrash.commands;


/**
 * 
 * This class allows the saving of the current configuration.
 * 
 * @author Mike
 * 
 * 18 Nov - Updated to reflect that the entire filename is captured via a file's filepath.
 *
 */
class SaveConfigurationCommand extends FileCommand
{
	/**
	 * 
	 * Constructor.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @deprecated
	 * 
	 */
	@Deprecated
	public SaveConfigurationCommand( String filepath, String filename )
	{
		super( filepath, filename );
	}
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param filename the file name of the file, including the file path
	 * 
	 */
	public SaveConfigurationCommand( String filename )
	{
		super( filename );
	}
}
