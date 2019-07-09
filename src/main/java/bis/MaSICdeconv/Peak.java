/**
 
 */
package bis.MaSICdeconv;

/**
 * @author : daewook
 * 2019. 6. 5.
 * @Desc : 
 */
public class Peak {
	public double mz;
	public int idx;
	public double intensity;
	public boolean used;
	public Peak(double mz, double intensity, int index, boolean used) {
		this.mz = mz;
		this.intensity = intensity;
		this.idx = index;
		this.used = used;
	}
	public double getMz() {
		return mz;
	}
	public void setMz(double mz) {
		this.mz = mz;
	}
	public double getIntensity() {
		return intensity;
	}
	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public boolean getUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public double mass(int chrg) {
		if (chrg == 0) {
			return mz;
		}
		else {
			return (mz - (Constants.MASS_H - Constants.MASS_ELECTRON))*chrg;
		}
	}
}
