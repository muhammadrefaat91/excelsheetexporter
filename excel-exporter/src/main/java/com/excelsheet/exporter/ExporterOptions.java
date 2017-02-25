package com.excelsheet.exporter;

import java.text.SimpleDateFormat;

/**
 * 
 * @author muhammadrefaat
 *
 */
public class ExporterOptions {

	private SimpleDateFormat dateFormat;
	private boolean includeHeader;
    private FileExtension fileExtension;
 
	public enum FileExtension{
		XLS("xls"),
		XLSX("xlsx");
		
		private String extension;
		
		FileExtension(String extension){
			this.extension = extension;
		}
		
		public String getExtension(){
			return extension;
		}
	};

    public ExporterOptions() {
	}
 
    /**
     *  provide some options for exporter
     * 
     * @param dateFormat date can be formatted such as mm-dd-yy
     * @param fileExtension  can generate file with xls or xlsx
     * @param includeHeader  export excel sheet with header or without  
     */
	public ExporterOptions(SimpleDateFormat dateFormat, FileExtension fileExtension,  boolean includeHeader) {
 		this.dateFormat = dateFormat;
		this.includeHeader = includeHeader;
		this.fileExtension = fileExtension;
	}
 
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public SimpleDateFormat getDatePattern() {
		return dateFormat;
	}
	
	public FileExtension getFileExtension(){
		return fileExtension;
	}


	public boolean isIncludeHeader() {
		return includeHeader;
	}

	public void setIncludeHeader(boolean includeHeader) {
		this.includeHeader = includeHeader;
	}

	@Override
	public String toString() {
		return "ExporterOptions [dateFormat=" + dateFormat + ", includeHeader=" + includeHeader + ", fileExtension="
				+ fileExtension + "]";
	}
}
