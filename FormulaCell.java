package textExcel;

public class FormulaCell extends RealCell{
	
	private String formula;
	private Spreadsheet ss;
	
	public FormulaCell(String formula, Spreadsheet ss){
		super(formula);
		this.formula = formula;
		this.ss = ss;
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
		String[] a = formula.split(" ");
		String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
		double value = 0;
		if(a[1].equalsIgnoreCase("SUM") || a[1].equalsIgnoreCase("AVG")){
			String[] range = a[2].split("-");
			int col1 = 0;
			int row1 = Integer.parseInt(range[0].substring(1));
			int col2 = 0;
			int row2 = Integer.parseInt(range[1].substring(1));
			for (int i = 0; i < 12; i++) {
				if (range[0].substring(0, 1).equalsIgnoreCase(arr[i]))
					col1 = i;
				if (range[1].substring(0, 1).equalsIgnoreCase(arr[i]))
					col2 = i;
			}
			int z = 0;
			double sum = 0;
			for(int i = col1; i <= col2; i++){
				for(int j = row1; j <= row2; j++){
					Location m = new SpreadsheetLocation(arr[i] + j);
					sum += ((RealCell)(ss.getCell(m))).getDoubleValue();
					z++;
				}
			}
			value = sum;
			if(a[1].equalsIgnoreCase("AVG")){
				value = sum / z;
			}
		}else{
			for(int i = 0; i < 12; i++){
				if(a[1].substring(0, 1).equalsIgnoreCase(arr[i])){
					Location l = new SpreadsheetLocation(a[1]);
					value = ((RealCell)ss.getCell(l)).getDoubleValue();
				}
			}
			if(value == 0){
				value += Double.parseDouble(a[1]);
			}
			for(int i = 2; i < a.length - 1; i = i + 2){
				String oper = a[i];
				double op = 0;
				for (int j = 0; j < 12; j++) {
		        	if (a[i + 1].substring(0, 1).equalsIgnoreCase(arr[j])){
		        		Location m = new SpreadsheetLocation(a[i + 1]);
		        		op = ((RealCell)(ss.getCell(m))).getDoubleValue();
		        	}	
		        }
		        if (op == 0){
		        	op = Double.parseDouble(a[i + 1]);
		        }

				if(oper.equals("+")){
					value += op;
				}else if(oper.equals("-")){
					value -= op;		
				}else if(oper.equals("*")){
					value *= op;
				}else if(oper.equals("/")){
					value /= op;
				}else{
					throw new IllegalArgumentException("ERROR: Invalid input");
				}
			}
		}
		return value;
	}
	
}
