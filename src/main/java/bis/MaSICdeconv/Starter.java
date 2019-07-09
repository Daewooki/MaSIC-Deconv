/**
 
 */
package bis.MaSICdeconv;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.systemsbiology.jrap.stax.Scan;

/**
 * @author : daewook 2019. 6. 5.
 * @Desc :
 */
public class Starter {
	static int MAXCHARGE = 3;
	static String testxml = "D:\\HEK293\\mzXML\\";
	static File file = new File(testxml);
	static File[] list = file.listFiles();
	static String testxml2 = "D:\\HEK293\\mzXML\\b1922_293T_proteinID_01A_QE3_122212.mzXML";

	static long stime = 0;

	static String hdf5Path = "model3_20190515.h5";

	public static void main(String args[]) throws Exception {
		MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(hdf5Path);
		XmlReader xmlReader = null;
		List<Peak> originalList = null;
		List<Peak> intensitySortedList = null;
		// PeakList maxPeak = null;
		ClusterList cluList = null;

		CompareInt comp = new CompareInt();

		for (File f : list) {
			// time
			stime = System.currentTimeMillis();
			// file name
			System.out.println(f.getName());
			xmlReader = new XmlReader(f.getAbsolutePath());
			for (int i = 1; i <= xmlReader.getMaxScanNum(); i++) {
				xmlReader.setScanHeader(i);
				if (xmlReader.isMSScan(i) == true) {
					if (i % 10 == 0) {
						System.out.println("scan = " + i + "/" + xmlReader.getMaxScanNum() + "\t"
								+ (System.currentTimeMillis() - stime) / 1000);
					}
					// System.out.println(xmlReader.isCentroid(i));
					if (xmlReader.isCentroid(i) == true) {
						// total peakList
						originalList = new ArrayList<>();
						// MS1scan
						Scan scanMS1 = xmlReader.getMS1Scan(i);
						// mz & intensity
						double[][] massInt = scanMS1.getMassIntensityList();
						// put peakList
						for (int idx = 0; idx < massInt[0].length; idx++) {
							Peak peak = new Peak(massInt[0][idx], massInt[1][idx], idx, false);
							// System.out.println(massInt[0][idx] + " , " + massInt[1][idx]);
							originalList.add(peak);
						}
						// maxPeak = new PeakList(originalList);
						// System.out.println(maxPeak.maxIntPeak().mz);
						// System.out.println(peakList.size());

						// intensity based sorting. Peak ds shared.
						intensitySortedList = new ArrayList<>();
						intensitySortedList.addAll(originalList);
						Collections.sort(intensitySortedList, comp);

						//System.out.println("Original first : " + originalList.get(0).mz);

						cluList = new ClusterList();
						cluList.Clustering(originalList, intensitySortedList, MAXCHARGE, model);

						//System.out.println("Original first : " + originalList.get(0).mz);
						// System.out.println(cluList.cluList.size());
					}
					// do nothing
					else {
						System.out.println("Not Centroid Data. Please Peak Picking.");
					}
				}
				break;
			}
			break;
		}
	}

}

class CompareInt implements Comparator<Peak> {
	@Override
	public int compare(Peak first, Peak second) {
		double firstInt = first.intensity;
		double secondInt = second.intensity;

		// order by descending
		if (firstInt > secondInt) {
			return -1;
		} else if (firstInt < secondInt) {
			return 1;
		} else {
			return 0;
		}
	}
}