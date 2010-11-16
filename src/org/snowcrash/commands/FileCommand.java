package org.snowcrash.commands;


/**
 * 
 * This class introduces a common base for file-related commands.
 * 
 * @author Mike
 *
 */
abstract class FileCommand extends AbstractCommand
{
	/*
	 * The file path of the file.
	 */
	private String filepath;

	/*
	 * The file name of the file.
	 */
	private String filename;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * 
	 */
	protected FileCommand(String filepath, String filename)
	{
		this.filepath = filepath;
		this.filename = filename;
	}

	/**
	 * 
	 * Returns the file path of the file.
	 * 
	 * @return the file path of the file
	 * 
	 */
	public String getFilepath()
	{
		return filepath;
	}

	/**
	 * 
	 * Returns the file name of the file.
	 * 
	 * @return the file name of the file
	 * 
	 */
	public String getFilename()
	{
		return filename;
	}
}
