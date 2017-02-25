package com.excelsheet.exporter;

import java.util.List;

public class TableRow {

	private List<Object> cellsList;

	public TableRow() {
	}

	public TableRow(List<Object> cellsList) {
 		this.cellsList = cellsList;
	}
	
	public List<Object> getCellsList() {
		return cellsList;
	}

	public void setCellsList(List<Object> cellsList) {
		this.cellsList = cellsList;
	}
 
	@Override
	public String toString() {
		return "SheetRow [ cellsList=" + cellsList + "]";
	}
 
}
