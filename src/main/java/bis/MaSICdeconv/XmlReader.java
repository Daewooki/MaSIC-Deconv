/**
 
 */
package bis.MaSICdeconv;

import org.systemsbiology.jrap.stax.MSXMLParser;
import org.systemsbiology.jrap.stax.Scan;
import org.systemsbiology.jrap.stax.ScanHeader;

/**
 * @author : daewook
 * 2019. 6. 5.
 * @Desc : 
 */
public class XmlReader {
	String xmlPath = "";
	MSXMLParser parser;
	ScanHeader scanhead = null;
	
	public XmlReader(String xmlPath){
		this.xmlPath = xmlPath;
		this.parser = new MSXMLParser(xmlPath);
	}
	public void setScanHeader(int scanNum) {
		this.scanhead = parser.rapHeader(scanNum);
	}
	
	public int getMSLevel(int scanNum) {
		return scanhead.getMsLevel();
	}
	public boolean isMSScan(int scanNum) {
		int ms_level = getMSLevel(scanNum);
		if(ms_level == 1) {
			return true;
		}else {
			return false;
		}
	}
	public int getMaxScanNum() {
		return parser.getMaxScanNumber();
	}
	
	public boolean isCentroid(int scanNum) {
		return (scanhead.getCentroided() > 0);
	}
	
	public Scan getMS1Scan(int scanNum) {
		Scan scanMS1 = parser.rap(scanNum);
		return scanMS1;
	}
}
