/**
 
 */
package bis.MaSICdeconv;

import java.util.List;

/**
 * @author : daewook
 * 2019. 6. 5.
 * @Desc : 
 */
public class PeakList {
	public List<Peak> list=null;
	public PeakList(List<Peak> peakList) {
		this.list = peakList;
	}
	
	public Peak maxIntPeak() {
		Peak maxPeak = null;
		double max = list.get(0).intensity;
		int idx = 0;
		for(int i=1; i<list.size(); i++) {
			if(max<list.get(i).intensity) {
				max=list.get(i).intensity;
				idx = i;
			}
		}
		maxPeak = list.get(idx);
		return maxPeak;
	}
}
