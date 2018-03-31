package textExcel;

public abstract class RealCell implements Cell, Comparable<Object>{
	
	private String abbrev;
	private String complete;
	
	public RealCell(String a){
		this.abbrev = a;
		this.complete = a;
	}
	
	public String fullCellText(){
		return complete;
	}
	
	public String getReal(){
		return complete;
	}
	
	public abstract String abbreviatedCellText();
	public abstract double getDoubleValue();
	
	public int compareTo(Object o){
		if(this.getDoubleValue() > ((RealCell) o).getDoubleValue())
			return 1;
		else if(this.getDoubleValue() == ((RealCell) o).getDoubleValue())
			return 0;
		else return -1;
	}

	
}
