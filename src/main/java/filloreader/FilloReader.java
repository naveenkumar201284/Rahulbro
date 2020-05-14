package filloreader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FilloReader {
	public List<Map<String, String>> getTestDataInListMap(File testDataFilePath, String sheetName, String QueryParser) throws Exception
	{
		Map<String,String> TestDataInMap = new HashMap<String,String>();
		List<Map<String,String>> TestDataInListMap = new ArrayList<Map<String,String>>();
		String query = null;
		query = String.format(QueryParser, sheetName);
		Fillo fillo = new Fillo();
		Connection conn;
		Recordset recordSet;
		boolean row=false;
			try
			{
					conn = fillo.getConnection(testDataFilePath.getAbsolutePath());
					recordSet = conn.executeQuery(query);
					while(recordSet.next())
					{								
							if(row)
								break;
							TestDataInMap = new HashMap<String, String>();
									for(String field : recordSet.getFieldNames())
									{
										String first_rowCell_value= recordSet.getField(0).value();
										if(first_rowCell_value.isEmpty()||first_rowCell_value == null)
										{
											row= true;
											break;
										}
										boolean is_rs_value_empty = recordSet.getField(field).isEmpty();
										if(is_rs_value_empty)
											continue;
									TestDataInMap.put(field, recordSet.getField(field));
									}	
									if(!TestDataInMap.isEmpty())
										TestDataInListMap.add(TestDataInMap);
					}
			}
			catch(FilloException Ex)
			{
				Ex.printStackTrace();
				throw new Exception("Test data cannot find...");
			}
			recordSet.close();
			conn.close();
			return TestDataInListMap;
	}

	
}
