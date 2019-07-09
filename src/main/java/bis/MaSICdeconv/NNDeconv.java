package bis.MaSICdeconv;

/**
 * @author : daewook 2019. 5. 15.
 * @Desc :
 */
public class NNDeconv {
//	public static void main(String[] args) throws Exception {
//		String path = args[0] + " " + args[1];
//		String hdf5 = args[2];
//
//		MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(hdf5);
//		System.out.println("load model!");
//
//		// evaluate the model on the test set
//		// String path = "D:\\Isotopic Deconvolution\\Data\\Training
//		// Data_190515_exp\\NegData.csv";
//		File file = new File(path);
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		br.readLine();
//
//		String reader = "";
//		String field[];
//		int cnt = 0;
//		// start
//		long start = System.currentTimeMillis();
//
//		INDArray testArray = Nd4j.zeros(1256511, 13);
//		System.out.println(testArray);
//
//		while ((reader = br.readLine()) != null) {
//			field = reader.split(",");
//			int k = 0;
//			for (int j = 2; j < 15; j++) {
//				testArray.putScalar(cnt, k, Double.parseDouble(field[j]));
//				k++;
//			}
//			cnt++;
//			if (cnt % 1000 == 0) {
//				System.out.println(cnt);
//			}
//		}
//		System.out.println("total : " + cnt);
//		INDArray output = model.output(testArray);
//
//		System.out.println("\n" + "output data");
//		System.out.println(output);
//
//		// double val = output.getDouble(2,1);
//		// System.out.println("value : " + val);
//
//		// end
//		long end = System.currentTimeMillis();
//
//		System.out.println("실행 시간 : " + (end - start) / 1000.0);
//		br.close();
//
//		ArrayList<Double> list = new ArrayList<>();
//		for (int i = 0; i < cnt; i++) {
//			double val = output.getDouble(i, 1);
//			list.add(val);
//		}
//
//		int tp = 0;
//		int fn = 0;
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i) > 0.5) {
//				tp++;
//			} else {
//				fn++;
//			}
//		}
//		System.out.println("tp : " + tp + " / " + "fn : " + fn);
//	}
//
//	static void output(ArrayList<Double> list, MultiLayerNetwork model) {
//		INDArray myArray = Nd4j.zeros(1, 13);
//
//		for (int i = 0; i < 13; i++) {
//			myArray.putScalar(0, i, list.get(i));
//		}
//
//		INDArray output = model.output(myArray);
//		System.out.println(output);
//	}
}
