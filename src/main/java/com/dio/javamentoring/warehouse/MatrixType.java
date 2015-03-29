package com.dio.javamentoring.warehouse;

public enum MatrixType {
	PLASMA("PLASMA"), LCD("LCD"), UNDEFINED("");
	
	private final String matrixTypeStr;
	
	private MatrixType(String matrixTypeStr) {
		this.matrixTypeStr = matrixTypeStr;
	}
	
	public String toString() {
		return matrixTypeStr;
	}
}
