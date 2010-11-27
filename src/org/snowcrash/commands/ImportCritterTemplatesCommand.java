package org.snowcrash.commands;


/**
 * 
 * This class allows the import of critter templates.
 * 
 * @author Mike
 * 
 * 18 Nov - Updated to reflect that the entire filename is captured via a file's filepath.
 *
 */
public class ImportCritterTemplatesCommand extends FileCommand
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
	public ImportCritterTemplatesCommand(String filepath, String filename)
	{
		super(filepath, filename);
	}
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param filename the file name of the file, including the file path
	 * 
	 */
	public ImportCritterTemplatesCommand(String filename)
	{
		super(filename);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.snowcrash.commands.Command#execute()
	 */
	public void execute()
	{
		CommandMediator.loadCritterTemplates( super.getFilename() );
	}
}
