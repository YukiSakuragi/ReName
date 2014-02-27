import java.io.*;
import java.util.*; // HashMap を使う際にimport
import java.util.Scanner;

public class TIS20140227 {
	String data_file;
	String out_file;
	final int doc_max = 16; //最大文書数
	Document[] doc; // 文書情報を格納するクラスの配列

	// コンストラクタ
	TIS20140227(String dfile) {
		data_file = dfile;
		out_file = "term_frequency.txt";
		doc = new Document[this.doc_max];
	}
	// コンストラクタ
	
	//辞書をHashに格納するメソッド
	void readDic(HashMap<String, String> hm) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream("C:/Users/seta/Desktop/配布物/資料/dic.csv"), "UTF-8"));

			String line;
			while( (line = br.readLine() ) != null ) {
			    line = line.replaceAll("\"","");
			    System.out.println(line);
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
					target = target.replaceAll(" ", "");
					target = target.replaceAll("\"", "");
				    
				    Set keys = hm.keySet();
				    for( Iterator itr = keys.iterator(); itr.hasNext(); ) {
						String exp = (String)itr.next();
						exp = exp.replaceAll(" ", "");
						exp = exp.replaceAll("\"", "");
						
						String value = hm.get(exp);
						
						//if(target.contains(exp))
						if(target.contains(exp))
						{
						    target = target.replaceAll(exp,value);
						}			
					}
				    int LargeAlfaCount = 0;
				    StringBuilder sb = new StringBuilder();
				    //文字列にアンダーバーを加える
				    for(int i=0 ; i < target.length();i++)
				    {
				    	//文字が英大文字であったら
				    	if(Character.isUpperCase(target.charAt(i)))
				    	{
				    		if(LargeAlfaCount > 0){
				    			sb.append( "_" + target.charAt(i));
				    		}
				    		else
				    		{
				    			sb.append(target.charAt(i));
				    		}
				    		LargeAlfaCount++;
				    	}
				    	else
				    	{
			    			sb.append(target.charAt(i));
				    	}
				    			
				    }
				    
				    String new_target = new String(sb);
				    new_target = new_target.toUpperCase();
				    System.out.println(new_target);
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

//辞書を表すクラス
class Document implements Comparable<Document> {
	private String term;	// 文書名
	private int term_len;	// 文書ベクトルの大きさ

	// コンストラクタ
	public Document(String term, int term_len) {
		this.term = term;
		this.term_len = term_len;
	}

	// 単語を返すメソッド
	public String getTerm() {
		return this.term;
	}

	// 単語を設定するメソッド
	public void setTerm(String term) {
		this.term = term;
	}
	// 単語の長さを設定するメソッド
	public void setLen(int term_len) {
		this.term_len = term_len;
	}

	// 単語の長さ返すメソッド
	public int getLen() {
		return this.term_len;
	}

	// ソート用メソッド（スコアの降順でソート）
	public int compareTo(Document d) {
		double s1 = this.term_len;
		double s2 = d.term_len;

		if (s1 == s2) {
			return 0;
		} 
		else if (s1 < s2) {
			return 1;
		} 
		else {
			return -1;
		}
	}
}