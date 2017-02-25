package com.excelsheet.exporter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excelsheet.util.FileData;
 
 

/**
 * 
 * @author mohamedrefaat 
 *
 *  Excel Sheet Exporter to convert list of objects to excel sheet file 
 * @param <T> type of object 
 */
public class ExcelSheetExporter<T> {

	private Table table = new Table();
	private Sheet sheet;
	private List<T> dataModelList;
	private RowMapper<T> rowMapper;
 	private ExporterOptions exporterOptions;

	/**
	 * Initialize exporter with data and mapper 
	 * @param dataModel List of objects
	 * @param rowMapper The way exporter map each object to row
	 * @return
	 */
	public ExcelSheetExporter<T> build(List<T> dataModel, RowMapper<T> rowMapper) {
		this.dataModelList = dataModel;
		this.rowMapper = rowMapper;
		exporterOptions = new ExporterOptions(new SimpleDateFormat("MM-dd-yyyy"), ExporterOptions.FileExtension.XLS, true);
		return this;
	}

	/**
	 *  Export list of objects to excel sheet file
	 * @return FileData object contains file name, excel sheet data as bytes and extension of file
	 */
	public FileData export() {
		try(Workbook workbook = createWorkbook();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			sheet = workbook.createSheet();
			createRows();
			workbook.write(outputStream);
			return getExportedFileData(outputStream.toByteArray());
		} catch (IOException ex) {
			throw new RuntimeException("error while exporting file",ex);
		}
	}
	
	private Workbook createWorkbook() {
		if (exporterOptions.getFileExtension().equals(ExporterOptions.FileExtension.XLS)) {
			return new HSSFWorkbook();
		} else {
			return new XSSFWorkbook();
		}
	}

	private FileData getExportedFileData(byte[] contents){
		FileData fileData = new FileData();
		fileData.setContents(contents);
		fileData.setFileExtension(exporterOptions.getFileExtension().getExtension());
		return fileData;
	}
	
	public ExcelSheetExporter<T> addExporterOptions(ExporterOptions options){
		this.exporterOptions = options;
		return this;
	}

	private Table mapObjectToRow() {
		if (exporterOptions.isIncludeHeader()) {
			table.setHeaderRow(rowMapper.mapHeader());
		}
		List<TableRow> rowList = new ArrayList<>();
		for (T model : dataModelList) {
			rowList.add(rowMapper.mapRow(model));
		}
		table.setRowsList(rowList);
		return table;
	}
	
	private void createRows() {
		Table table = mapObjectToRow();
		List<TableRow> rowList = table.getRowsList();
		int rowIndex = 0;
		if (table.getHeaderRow() != null && exporterOptions.isIncludeHeader()) {
			createHeaderRow(table);
			rowIndex++;
		}
		
		for (int i = 0; i < rowList.size(); i++) {
			TableRow row = rowList.get(i);
			List<Object> cellsList = row.getCellsList();
			Row rowData = sheet.createRow(rowIndex);
			createRowCells(rowData, cellsList);
			rowIndex++;
		}
	}
	
	private void createHeaderRow(Table dataTable){
		TableRow headerRow = dataTable.getHeaderRow();
		List<Object> headerCellsList = headerRow.getCellsList();
		Row headerRowData = sheet.createRow(0);
		createHeaderCells(headerRowData, headerCellsList);
	}

	private void createRowCells(Row rowData, List<Object> cellsList) {
		for (int cellCount = 0; cellCount < cellsList.size(); cellCount++) {
			Object object = cellsList.get(cellCount);
			if (object != null) {
				if (object instanceof Integer) {
					rowData.createCell(cellCount).setCellValue((Integer) object);
				} else if (object instanceof Double) {
					rowData.createCell(cellCount).setCellValue((Double) object);
				} else if (object instanceof Long) {
					rowData.createCell(cellCount).setCellValue((Long) object);
				}else if (object instanceof String) {
					rowData.createCell(cellCount).setCellValue((String) object);
				} else if (object instanceof LocalDateTime) {
					LocalDateTime localDateTime = (LocalDateTime) object;
					Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
					rowData.createCell(cellCount).setCellValue(exporterOptions.getDatePattern().format(date));
				} else if (object instanceof Date) {
					rowData.createCell(cellCount).setCellValue((Date) object);
				} else if (object instanceof Boolean) {
					rowData.createCell(cellCount).setCellValue((Boolean) object);
				} else {
					RuntimeException exception = new RuntimeException("Not supported property type!");
					throw exception;
				}
			}
		}
	}
	
	private void createHeaderCells(Row rowData, List<Object> cellsList){
		for (int cellCount = 0; cellCount < cellsList.size(); cellCount++) {
			Object object = cellsList.get(cellCount);
			rowData.createCell(cellCount).setCellValue((String) object);
		}
	}
	
	public ExporterOptions getExporterOptions() {
		return exporterOptions;
	}

	public void setExporterOptions(ExporterOptions exporterOptions) {
		this.exporterOptions = exporterOptions;
	}
	
	public static <T> FileData exportExcelSheet(List<T> listOfModelTobeExported, Class<T> model , String exportedFileName) {
		ObjectMapper<T> mapper = new ObjectMapper<T>(model);
		ExcelSheetExporter<T> exporter = new ExcelSheetExporter<>();
		FileData fileData = exporter.build(listOfModelTobeExported, mapper).addExporterOptions(
									new ExporterOptions(new SimpleDateFormat("MM-dd-yyyy"), ExporterOptions.FileExtension.XLS, true))
									.export();
		fileData.setName(exportedFileName);
		return fileData;
	}
}
