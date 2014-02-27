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
	
	//辞書をHashに格納するメソッド
	void readDic(HashMap<String, String> hm) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream("C:/Users/seta/Desktop/配布物/資料/dictionary.csv"), "UTF-8"));

			String line;
			while( (line = br.readLine() ) != null ) {
			    line = line.replaceAll("\"","");
				String[] data = line.split(","); //データを分割
				int data_num = data.length;
				
				// data[0]:日本語, data[1]:英語
				//
				// 単語の出現頻度の集計
				//
				if( data_num == 2 ){ 
					hm.put( data[0], data[1] );
				}
				else { //その単語を初めてHashMapに格納するとき
					hm.put( data[0], "" );
				}
			} //end while
		}
		catch(Exception e) { // ファイル読み込みエラー時の処理
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //プログラムの終了
		}
	}


	//インプットファイルの修正箇所を英語から英語の名前を付けるメソッド
	void readFile(HashMap<String, String> hm, String infile) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream("C:/Users/seta/Desktop/配布物/資料/"+infile), "UTF-8"));

			System.out.println("*");
			String line;
			while( (line = br.readLine() ) != null ) {

			    int index = line.indexOf("<");
			    line = line.substring(index);
			    		
				//変更箇所を検出
			    String target_string = line.substring(0,5);
			    if(target_string.equals("<ATTR")){
			    	//変更処理を以下で行う
					String[] line_data = line.split(" ");
					int array_num = line_data.length;
					
					 // 指定した文字より後ろの文字取り出し
				    index = line_data[3].indexOf("P-NAME=\"");
				    index += "P-NAME=\"".length();
				    String target = line_data[3].substring(index);

				    index = target.indexOf("\"");
				    target = target.substring(0,index);
					
				    System.out.println(target);
				}
				//変更しない場合はそのまま出力する
				else
				{
				
				}
				
				
			} //end while
		}
		catch(Exception e) { // ファイル読み込みエラー時の処理
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //プログラムの終了
		}
	}


	void kadai(String inf, String of) {
		HashMap<String, String> dic_table; //英語-日本語の辞書を格納するHashMap
		dic_table = new HashMap<String, String>(101); // dic_tableのインスタンス化
		//辞書をdic_tableに格納する
		readDic(dic_table);
		//インプットファイルを読み込む
		readFile(dic_table, inf);
		
	}


	public static void main(String[] args) {
		TIS20140227 tis = new TIS20140227(args[0]);

		tis.kadai(tis.data_file, tis.out_file);
	}

} //class HashMapSample3 end
