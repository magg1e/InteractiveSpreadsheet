package textExcel;

//Update this file with your own code.

public class SpreadsheetLocation implements Location
{
	private int row = 0;
	private int col = 0;
    @Override
    public int getRow()
    {
        return row;
    }

    @Override
    public int getCol()
    {
        return col;
    }
    
    public SpreadsheetLocation(String cellName)
    {
        // TODO: Fill this out with your own code
    	char alphacol = cellName.charAt(0);
    	this.col = (Character.toUpperCase(alphacol) - 'A');
    	this.row = Integer.parseInt(cellName.substring(1)) - 1;
    }
    
    public SpreadsheetLocation(int col, int row){
    	this.col = col;
    	this.row = row;
    }

}
