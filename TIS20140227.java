import java.io.*;
import java.util.*; // HashMap を使う際にimport

public class TIS20140227 {
	String data_file;
	String out_file;

	// コンストラクタ
	TIS20140227(String dfile) {
		data_file = dfile;
		out_file = "term_frequency.txt";
	}
	
	void readDic(HashMap<String, Integer> hm) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream(), "UTF-8"));

			String line;
			while( (line = br.readLine() ) != null ) {
				String[] data = line.split("\t"); //データを分割
				// data[0]:単語, data[1]:品詞などの解析情報
				//
				// 単語の出現頻度の集計
				//
     String targetType = line.split("[\t|,]")[1];

			if(targetType.equals("名詞") || targetType.equals("動詞")){
				if( hm.containsKey( data[0] ) ){ //すでにその単語が格納されているとき
					int hm_value =  hm.get(data[0]).intValue();
					int new_value = hm_value + 1;
					hm.put( data[0], new Integer(new_value) );
				}
				else { //その単語を初めてHashMapに格納するとき
					hm.put( data[0], 1 );
				}}
			} //end while
		}
		catch(Exception e) { // ファイル読み込みエラー時の処理
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //プログラムの終了
		}
	}



	void readFile(HashMap<String, Integer> hm, String infile) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream(infile), "UTF-8"));

			String line;
			while( (line = br.readLine() ) != null ) {
				String[] data = line.split("\t"); //データを分割
				// data[0]:単語, data[1]:品詞などの解析情報
				//
				// 単語の出現頻度の集計
				//
     String targetType = line.split("[\t|,]")[1];

			if(targetType.equals("名詞") || targetType.equals("動詞")){
				if( hm.containsKey( data[0] ) ){ //すでにその単語が格納されているとき
					int hm_value =  hm.get(data[0]).intValue();
					int new_value = hm_value + 1;
					hm.put( data[0], new Integer(new_value) );
				}
				else { //その単語を初めてHashMapに格納するとき
					hm.put( data[0], 1 );
				}}
			} //end while
		}
		catch(Exception e) { // ファイル読み込みエラー時の処理
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //プログラムの終了
		}
	}


	// HashMapの内容をファイルに出力
	void outputHashMap(HashMap<String, Integer> hm, String of) {
		Set keys = hm.keySet();

		try {
			FileWriter fw = new FileWriter( of );
			BufferedWriter bw = new BufferedWriter( fw );
			PrintWriter pw = new PrintWriter( bw );

			for( Iterator itr = keys.iterator(); itr.hasNext(); ) {
				String exp = (String)itr.next();
				String value = Integer.toString(hm.get(exp));
				pw.println(exp + ": " + value);// 文字列を書き出す
				pw.flush();
			}
			pw.close(); // 出力ストリームのクローズ
		}
		catch (IOException e) { // ファイル書き込みエラー時の処理
			System.out.println("I/O error at outputHashMap \n" + e);
			System.exit(1); //プログラムの終了
		}
	}


	void sumup(String inf, String of) {
		HashMap<String, String> dic_table; //英語-日本語の辞書を格納するHashMap
		dic_table = new HashMap<String, String>(101); // dic_tableのインスタンス化
		//辞書をdic_tableに格納する
		readDic(dic_table);
		
		//インプットファイルを読み込む
		readFile(dic_table, inf);
		outputHashMap(tf_table, of); //tf_table(HashMap)の内容を出力
	}


	public static void main(String[] args) {
		TIS20140227 tis = new TIS20140227(args[0]);

		tis.sumup(hms.data_file, hms.out_file);
	}

} //class HashMapSample3 end
