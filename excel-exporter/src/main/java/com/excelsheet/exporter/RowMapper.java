package com.excelsheet.exporter;

 
public interface RowMapper<T> {
	
	/**
	 * The way in which each row in the table should be mapped
	 * @param the object to be mapped
	 * @return the mapped row
	 */
	TableRow mapRow(T t);
	
	/**
	 * Generate the header row
	 * @return the mapped header
	 */
	 default TableRow mapHeader(){
		return new TableRow();
	}
}
