package bis.MaSICdeconv;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * @author : daewook 2019. 6. 10.
 * @Desc :
 */
public class ClusterList {
	public List<Cluster> cluList = null;

	public ClusterList() {
		this.cluList = new ArrayList<>();
	}

	/**
	 * @param originalList
	 * @param intensitySortedList
	 * @param charge
	 */
	public void Clustering(List<Peak> originalList, List<Peak> intensitySortedList, int charge,
			MultiLayerNetwork model) {
		for (int cs = 1; cs <= charge; cs++) {
			int clusCnt = 0;
			// peak used initialize
			for (int i = 0; i < originalList.size(); i++) {
				originalList.get(i).setUsed(false);
			}

			LinkedList<Peak> deq = new LinkedList<>();
			for (int i = 0; i < intensitySortedList.size(); i++) {
				int idx = intensitySortedList.get(i).idx;
				if (originalList.get(idx).used == false) {
					Peak p1 = originalList.get(idx);
					deq.addFirst(p1);
					p1.setUsed(true);

					int startIdx = idx - 1;
					double tempIntensity = p1.getIntensity();
					while (startIdx > 0) {
						double preTolerance = p1.mz - (Constants.MASS_ONE + 20 * Constants.MASS_ERR * p1.mass(cs)) / cs;
						double postTolerance = p1.mz
								- (Constants.MASS_ONE - 20 * Constants.MASS_ERR * p1.mass(cs)) / cs;
						// System.out.println("pretolerance : " + preTolerance);
						// System.out.println("postTolerance : " + postTolerance);
						if (originalList.get(startIdx).mz >= preTolerance
								&& originalList.get(startIdx).mz <= postTolerance
								&& originalList.get(startIdx).getUsed() == false) {
							p1 = originalList.get(startIdx);
							if (p1.getIntensity() < tempIntensity) {
								deq.addFirst(p1);
								p1.setUsed(true);
								startIdx--;
								tempIntensity = p1.getIntensity();
							} else {
								break;
							}
						} else {
							startIdx--;
						}
					}
					Peak p2 = originalList.get(idx);
					int endIdx = idx + 1;
					tempIntensity = p2.getIntensity();
					while (endIdx < originalList.size() - 1) {
						double preTolerance = p2.mz + (Constants.MASS_ONE - 20 * Constants.MASS_ERR * p2.mass(cs)) / cs;
						double postTolerance = p2.mz
								+ (Constants.MASS_ONE + 20 * Constants.MASS_ERR * p2.mass(cs)) / cs;
						// System.out.println("pretolerance : " + preTolerance);
						// System.out.println("postTolerance : " + postTolerance);
						if (originalList.get(endIdx).mz >= preTolerance && originalList.get(endIdx).mz <= postTolerance
								&& originalList.get(endIdx).getUsed() == false) {
							p2 = originalList.get(endIdx);
							if (p2.getIntensity() < tempIntensity) {
								deq.addLast(p2);
								p2.setUsed(true);
								endIdx++;
								tempIntensity = p2.getIntensity();
							} else {
								break;
							}
						} else {
							endIdx++;
						}
					}

					// Cluster e = new Cluster(0.0, deq, cs);
					// cluList.add(e);

					if (deq.size() < 13 && deq.size() > 1) {
						// for(int t = 0; t < deq.size(); t++) {
						// System.out.println(deq.get(t).intensity);
						// }
						clusCnt++;
						INDArray input = PossibleClusters(deq, cs);
						INDArray output = model.output(input);
						System.out.println(output);
						double max = 0.0;
						int cluIdx = 0;
						if (output.maxNumber().doubleValue() > 0.5) {
							for (int z = 0; z < output.length(); z++) {
								if (max < output.getDouble(z)) {
									max = output.getDouble(z);
									System.out.println(max);
									cluIdx = z;
								}
							}
							System.out.println(String.format("%.2f", max));
							System.out.println(input.getRow(cluIdx));
						}
						// if (output.maxNumber().doubleValue() > 0.5) {
						// System.out.println(String.format("%.2f", output.maxNumber().doubleValue()));
						// }
					}
					// System.out.println("inputlenght : " +inputs.length());
					deq.clear();
				}
				 break;
			}
			// System.out.println("cs" + cs + ":"+ clusCnt);
			// System.out.println("cluster # : " + clusCnt);
			// System.out.println(output);
			// System.out.println(output.length());
			 break;
		}
	}

	// 모든 가능한 경우 만들어서 INDArray에 넣기

	public INDArray PossibleClusters(LinkedList<Peak> deq, int cs) {
		int sum = 0;
		for (int i = deq.size() - 1; i > 0; i--) {
			sum += i;
		}
		INDArray array = Nd4j.zeros(sum, 13);
		ArrayList<Double> intList = new ArrayList<>();
		ArrayList<Double> normList = new ArrayList<>();
		int n = 0;
		for (int i = 0; i < deq.size(); i++) {
			intList.add(deq.get(i).intensity);
			for (int j = i + 1; j < deq.size(); j++) {
				double monoMass = deq.get(i).mass(cs);
				intList.add(deq.get(j).intensity);
				normalization(intList, normList);
				array.putScalar(n, 0, monoMass);
				for (int k = 0; k < normList.size(); k++) {
					array.putScalar(n, k + 1, normList.get(k));
				}
				n++;
				normList.clear();
			}
			intList.clear();
		}
		return array;
	}

	static void normalization(ArrayList<Double> ar, ArrayList<Double> list) {
		// double format
		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);

		double max;
		if (ar.size() == 0) {
			return;
		}
		max = Collections.max(ar);
		// System.out.println("min : " + f.format(min) + " , max : " + f.format(max));

		for (int i = 0; i < ar.size(); i++) {
			list.add((ar.get(i)) * 10000.0 / (max));
		}

		for (int i = ar.size(); i < 12; i++) {
			list.add(0.0);
		}
	}
}
