package com.excelsheet.util;

import java.util.Arrays;

public class FileData {

	private String name;
	
	private byte[] contents;
	
	private String fileExtension;

	public FileData() {
	}
	
	public FileData(String name) {
 		this.name = name;
 	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public String toString() {
		return "FileData [name=" + name + ", contents=" + Arrays.toString(contents) + ", fileExtension=" + fileExtension
				+ "]";
	}

	
}
