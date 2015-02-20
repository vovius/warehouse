package com.dio.javamentoring.warehouse;

public enum MatrixType {
	PLASMA("Plasma"), LCD("LCD");
	
	private final String matrixTypeStr;
	
	private MatrixType(String matrixTypeStr) {
		this.matrixTypeStr = matrixTypeStr;
	}
	
	public String toString() {
		return matrixTypeStr;
	}
}
