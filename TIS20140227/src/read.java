import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class read {
	String data_file;

	// コンストラクタ
	read(String dfile) {
		data_file = dfile;
	}
	
	void readFile(String infile){
	try {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						"C:/Users/seta/Desktop/配布物/資料/"+infile), "UTF-8"));

		String line;
		String new_doc;
		int term_count = 0;

		while ((line = br.readLine()) != null) {
			new_doc = new_doc + line;			
		} // end while
		System.out.println(new_doc);
	}

	} catch (Exception e) { // ファイル読み込みエラー時の処理
		System.out.println("I/O error at readFile \n" + e);
		System.exit(1); // プログラムの終了
	}
	
	void kadai(String inf) {
		readFile(inf);
	}
}
public static void main(String[] args) {
	read re = new read(args[0]);
	re.kadai(re.data_file);
}

