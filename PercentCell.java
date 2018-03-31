package textExcel;

public class PercentCell extends RealCell{
	
	private String percent;
	
	public PercentCell(String percent){
		super(percent);
		this.percent = percent;
	}
	
	public String abbreviatedCellText(){
		String y = percent;
		if(percent.contains(".")){
			int x = percent.indexOf('.');
			y = percent.substring(0, x) + "%";
		}
		
		if(y.length() <= 10){
			for(int i = (10 - y.length()); i > 0; i--){
				y = y + " ";
			}
			return y;
		}else{
			return y.substring(0, 10);
		}
	}
	
	public String fullCellText(){
		return String.valueOf(getDoubleValue());
	}
	
	public double getDoubleValue(){
		String nopercent = super.fullCellText().trim().substring(0, super.fullCellText().length() - 1);
		return Double.parseDouble(nopercent)/100.0;
	}
	
	public double getDecimalValue(){
		double x = getDoubleValue()/100;
		return x;
	}

}
