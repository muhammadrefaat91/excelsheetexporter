package com.excelsheet.exporter;

import java.util.List;

 
public class Table {

	private TableRow headerRow;
	private List<TableRow> rowsList;
	
	public Table() {
	}
	
	public Table(TableRow headerRow, List<TableRow> rowsList) {
		this.headerRow = headerRow;
		this.rowsList = rowsList;
	}

	public List<TableRow> getRowsList() {
		return rowsList;
	}

	public void setRowsList(List<TableRow> rowsList) {
		this.rowsList = rowsList;
	}
 
	public TableRow getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(TableRow headerRow) {
		this.headerRow = headerRow;
	}

	@Override
	public String toString() {
		return "Sheet [headerRow=" + headerRow + ", rowsList=" + rowsList + "]";
	}
	
}
