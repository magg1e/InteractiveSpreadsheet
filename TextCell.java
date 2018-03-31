package textExcel;

public class TextCell implements Cell, Comparable<Object>{
	
	private String x;
	
	public TextCell(String x){
		this.x = x;
	}
	
	public String abbreviatedCellText(){
		String hi = x;
		if(hi.length() <= 10){
			for(int i = (10 - hi.length()); i > 0; i--){
				hi = hi + " ";
			}
			return hi;
		}else{
			return hi.substring(0, 10);
		}
	}
	
	public String fullCellText(){
		return "\"" + x + "\"";
	}
	
	public int compareTo(Object o) {
		if(this.fullCellText().compareTo(((TextCell) o).fullCellText()) > 0)
			return 1;
		else if(this.fullCellText().compareTo(((TextCell) o).fullCellText()) == 0)
			return 0;
		else return -1;
	}

	
}
