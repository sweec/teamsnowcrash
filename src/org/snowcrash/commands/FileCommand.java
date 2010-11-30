package org.snowcrash.commands;


/**
 * 
 * This class introduces a common base for file-related commands.
 * 
 * @author Mike
 * 
 * 18 Nov - Updated to reflect that the entire filename is captured via a file's filepath.
 *
 */
abstract class FileCommand implements Command
{
	/*
	 * The file path of the file.
	 */
	@Deprecated
	private String filepath;

	/*
	 * The file name of the file, including the file path.
	 */
	private String filename;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param filepath the file path of the file
	 * @param filename the file name of the file
	 * @deprecated Clients should use the 1-argument constructor instead.
	 * 
	 */
	@Deprecated
	protected FileCommand(String filepath, String filename)
	{
		this.filepath = filepath;
		this.filename = filename;
	}
	
	/**
	 * 
	 * Constructor.
	 * 
	 * @param filename the file name of the file, including the file path
	 * 
	 */
	protected FileCommand(String filename)
	{
		this.filename = filename;
	}

	/**
	 * 
	 * Returns the file path of the file.
	 * 
	 * @return the file path of the file
	 * @deprecated
	 * 
	 */
	@Deprecated
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
