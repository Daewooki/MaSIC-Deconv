/**
 
 */
package bis.MaSICdeconv;

import java.util.List;

/**
 * @author : daewook 2019. 6. 10.
 * @Desc :
 */
public class Cluster {
	public int clusterNum;
	public double monoMZ;
	public List<Peak> peakList;
	public int charge;

	public Cluster(double monoMZ, List<Peak> peakList, int charge) {
		this.monoMZ = monoMZ;
		this.peakList = peakList;
		this.charge = charge;
	}

	public double getMonoMZ() {
		return monoMZ;
	}

	public void setMonoMZ(double monoMZ) {
		this.monoMZ = monoMZ;
	}

	public List<Peak> getPeakList() {
		return peakList;
	}

	public void setPeakList(List<Peak> peakList) {
		this.peakList = peakList;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}
	
	public double getMonoMass() {
		double monoMass = (monoMZ * charge) - (charge * (Constants.MASS_H-Constants.MASS_ELECTRON));
		return monoMass;
	}
	public void setClusterNum(int cNum) {
		this.clusterNum = cNum;
		
	}
}