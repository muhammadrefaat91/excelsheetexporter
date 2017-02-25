package com.excelsheet.exporter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.excelsheet.util.Column;
import com.excelsheet.util.Order;
 
  

public class ObjectMapper<T> implements RowMapper<T> {

 	private Class<T> clazz;
 
	public ObjectMapper(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("Argument class is missing");
		}
		this.clazz = clazz;
	}

	@Override
	public TableRow mapRow(T model) {
		Class<? extends Object> clazz = model.getClass();
		List<Object> cellList = new ArrayList<>();
		Field fields[] = clazz.getDeclaredFields();
		sortObjectFields(fields);
		for (Field field : fields) {
			try {
				Annotation columnAnnotation = field.getDeclaredAnnotation(Column.class);
				if (columnAnnotation != null) {
					field.setAccessible(true); 				 
	 				cellList.add(field.get(model));
				}
			} catch (IllegalArgumentException | IllegalAccessException ex) {
				throw new RuntimeException("ILLEGAL ACCESS OR ARGUMENT Exception", ex);
			}
		}
		return new TableRow(cellList);
	}

	@Override
	public TableRow mapHeader() {
 		List<Object> cellList = new ArrayList<>();
		Field fields[] = clazz.getDeclaredFields();
		sortObjectFields(fields);
		for (Field field : fields) {
			Annotation columnAnnotation = field.getDeclaredAnnotation(Column.class);
				if (columnAnnotation != null) {
					Column columnName = (Column) columnAnnotation;
					cellList.add(columnName.value());
				}
		}
		return new TableRow(cellList);
	}
  
/**
 *  
 * @param fields
 */
	private void sortObjectFields(Field [] fields ) {
	    Arrays.sort(fields, new Comparator<Field>() {
	        public int compare(Field field1, Field field2) {
	            Order or1 = field1.getDeclaredAnnotation(Order.class);
	            Order or2 = field2.getDeclaredAnnotation(Order.class);
	            if (or1 != null && or2 != null) {
	                return or1.value() - or2.value();
	            }
	            else if (or1 != null && or2 == null) {
	                return -1;
	            }
	            else if (or1 == null && or2 != null) {
	                return 1;
	            }
	            return 0;
	        }
	    });
	}
	
}
