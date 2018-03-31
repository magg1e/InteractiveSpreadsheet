package textExcel;

// Update this file with your own code.

public class Spreadsheet implements Grid
{
	private Cell[][] ss;
	
	public Spreadsheet(){
		this.ss = new Cell[20][12];
		for(int i = 0; i < ss.length; i++){
			for(int j = 0; j < ss[i].length; j++){
				ss[i][j] = new EmptyCell();
			}
		}
	}
	
	@Override
	public String processCommand(String command)
	{
		if(command.trim().length() <= 4 && command.trim().length() >= 2){
			return returnCell(command);
		}else if(command.toLowerCase().contains("sort")){
			return sort(command);
		}else if(command.contains(" (") && command.contains(" )") && (command.indexOf("=") == command.lastIndexOf("="))){
			return assignFormulaCell(command);
		}else if(command.trim().contains("=") && command.contains("\"")){
			return assignStringValue(command);
		}else if(command.toLowerCase().equals("clear")){
			return clearAll();
		}else if(command.toLowerCase().contains("clear") && command.contains(" ")){
			return clear(command);
		}else if(command.contains("=") && !command.contains("%")){
			return assignValueCell(command);
		}else if(command.contains("%")){
			return assignPercentCell(command);
		}else{
			return command;
		}
	}
	
	public String sort(String command) {
		String[] a = command.split(" ");
		String[] b = a[1].split("-");
		SpreadsheetLocation start = new SpreadsheetLocation(b[0]);
		SpreadsheetLocation end = new SpreadsheetLocation(b[1]);
		Cell[] range = new Cell[(end.getRow() - start.getRow() + 1) * (end.getCol() - start.getCol() + 1)];
		int counter = 0;
		for(int i = start.getRow(); i <= end.getRow(); i++) {
			for(int j = start.getCol(); j <= end.getCol(); j++) {
				range[counter] = ss[i][j];
				counter++;
			}
		}
		
		Cell temp;
		
		if(range[0] instanceof TextCell) {
			for(int i = 0; i < range.length - 1; i++) {
				for(int j = i + 1; j < range.length; j++) {
					if(a[0].equalsIgnoreCase("sorta")){
						if(range[i].fullCellText().compareTo(range[j].fullCellText()) > 0) {
							temp = range[i];
							range[i] = range[j];
							range[j] = temp;
						}
					}
					else{
						if(range[i].fullCellText().compareTo(range[j].fullCellText()) < 0) {
							temp = range[i];
							range[i] = range[j];
							range[j] = temp;
						}
					}
				}
			}
		}
		else{
			for(int i = 0; i <range.length - 1; i++) {
				for(int j = i+1; j < range.length; j++) {
					if(a[0].equalsIgnoreCase("sorta")){
						if (((RealCell)range[i]).compareTo((RealCell)range[j]) > 0) {
							temp = range[i];
							range[i] = range[j];
							range[j] = temp;
						}
					}
					else{
						if(((RealCell)range[i]).compareTo((RealCell)range[j]) < 0) {
							temp = range[i];
							range[i] = range[j];
							range[j] = temp;
						}
					}
				}
			}
		}
		counter = 0;
		for(int i = start.getRow(); i <= end.getRow(); i++) {
			for(int j = start.getCol(); j <= end.getCol(); j++) {
				ss[i][j] = range[counter];
				counter++;
			}
		}
		return getGridText();
	}


	public String returnCell(String command){
		int col = (Character.toUpperCase(command.charAt(0)) - 'A');
		int row = Integer.parseInt(command.substring(1)) - 1;
		return ss[row][col].fullCellText();
	}
	
	public String assignStringValue(String command){
		String[] a = command.split(" ");
		if(a.length > 3){
			for(int i = 3; i < a.length; i++){
				a[2] = a[2] + " " + a[i];
			}
		}
		int col = (Character.toUpperCase(command.charAt(0)) - 'A');
		int row = Integer.parseInt(a[0].substring(1)) - 1;
		ss[row][col] = new TextCell(a[2].substring(1, a[2].length() - 1));
		return getGridText();
	}
	
	public String clearAll(){
		for(int i = 0; i < ss.length; i++){
			for(int j = 0; j < ss[i].length; j++){
				ss[i][j] = new EmptyCell();
			}
		}
		return getGridText();
	}
	
	public String clear(String command){
		String[] a = command.split(" ");
		int col = (Character.toUpperCase(a[1].charAt(0)) - 'A');
		int row = Integer.parseInt(a[1].substring(1)) - 1;
		ss[row][col] = new EmptyCell();
		return getGridText();
	}
	
	public String assignValueCell(String command){
		String[] a = command.split(" ");
		int col = (Character.toUpperCase(command.charAt(0)) - 'A');
		int row = Integer.parseInt(a[0].substring(1)) - 1;
		ss[row][col] = new ValueCell(a[2]);
		return getGridText();
	}
	
	public String assignPercentCell(String command){
		String[] a = command.split(" ");
		int col = (Character.toUpperCase(command.charAt(0)) - 'A');
		int row = Integer.parseInt(a[0].substring(1)) - 1;
		ss[row][col] = new PercentCell(a[2]);
		return getGridText();
	}
	
	public String assignFormulaCell(String command){
		String[] a = command.split(" ");
		int col = (Character.toUpperCase(command.charAt(0)) - 'A');
		int row = Integer.parseInt(a[0].substring(1)) - 1;
		if(a.length > 3){
			for(int i = 3; i < a.length; i++){
				a[2] = a[2] + " " + a[i];
			}
		}
		ss[row][col] = new FormulaCell(a[2], this);
		return getGridText();
	}
	
	@Override
	public int getRows()
	{
		return 20;
	}

	@Override
	public int getCols()
	{
		return 12;
	}

	@Override
	public Cell getCell(Location loc)
	{
		return ss[loc.getRow()][loc.getCol()];
	}

	@Override
	public String getGridText()
	{
		String digits = "ABCDEFGHIJKL";
		String grid = "   |";
		for(int i = 0; i < digits.length(); i++){
			grid = grid + digits.charAt(i) + "         |";
		}
		for(int j = 0; j < getRows(); j++){
			String space = " ";
			if(j < 9){
				space = "  ";
			}
			grid = grid + "\n";
			grid = grid + (j + 1) + space + "|";
			for(int k = 0; k < getCols(); k++){
				grid = grid + ss[j][k].abbreviatedCellText() + "|";
			}
		}
		grid = grid + "\n";
		return grid;
	}

}
