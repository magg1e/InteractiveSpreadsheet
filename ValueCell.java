package textExcel;

public class ValueCell extends RealCell{
	
	private String complete;
	
	public ValueCell(String complete){
		super(complete);
		complete = complete.indexOf(".") < 0 ? complete : complete.replaceAll("0*$", "").replaceAll("\\.$", "");
		this.complete = complete;
	}
	
	public String abbreviatedCellText(){
		String truncated = Double.toString(getDoubleValue());
		String output = truncated;
		if(truncated.length() == 10){
			return output;
		}else if(truncated.length() < 10){
			for(int j = (10 - truncated.length()); j > 0; j--){
				output = output + " ";
			}
		}else{
			output = "";
			for(int i = 0; i < 10; i++){
			output = output + truncated.charAt(i);
			}
		}
			return output;
	}
	
	
	public double getDoubleValue(){
		double x = Double.parseDouble(complete);
		return x;
	}
}
