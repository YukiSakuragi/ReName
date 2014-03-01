import java.io.*;
import java.util.*; // HashMap を使う際にimport
import java.util.Scanner;

public class TIS20140227 {
	String data_file;
	String out_file;
	final int doc_max = 16; // 最大文書数
	Document[] doc; // 文書情報を格納するクラスの配列

	// コンストラクタ
	TIS20140227(String dfile) {
		data_file = dfile;
		out_file = "output.edm";
		doc = new Document[this.doc_max];
	}

	// コンストラクタ

	// 辞書をHashに格納するメソッド
	void readDic(HashMap<String, String> hm, Document[] doc) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							"C:/Users/seta/Desktop/配布物/資料/dic.csv"), "UTF-8"));

			String line;
			int term_count = 0;

			while ((line = br.readLine()) != null) {
				line = line.replaceAll("\"", "");
				String[] data = line.split(","); // データを分割
				int data_num = data.length;

				doc[term_count] = new Document(data[0], data[0].length());

				// data[0]:日本語, data[1]:英語
				//
				// 単語の出現頻度の集計
				//
				if (data_num == 2) {
					hm.put(data[0], data[1]);
				} else { // その単語を初めてHashMapに格納するとき
					hm.put(data[0], "");
				}
				term_count++;
			} // end while

			Arrays.sort(doc); // 文字列の長さでの並び替え
		} catch (Exception e) { // ファイル読み込みエラー時の処理
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); // プログラムの終了
		}
	}

	// インプットファイルの修正箇所を英語から英語の名前を付けるメソッド
	void readFile(HashMap<String, String> hm, String infile, Document[] doc,
			String of) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:/Users/seta/Desktop/配布物/資料/"
							+ infile), "UTF-8"));

			FileWriter fw = new FileWriter(of);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			String line;
			String line1;
			while ((line = br.readLine()) != null) {

				int index = line.indexOf("<");
				line1 = line.substring(index);

				String[] line_data = line1.split(" ");
				int array_num = line_data.length;
				String new_line;

				// 変更箇所を検出
				// 指定した文字より後ろの文字取り出し
				index = line1.indexOf("<");
				String target_string = line1.substring(index + 1);
				if (target_string.contains(" ")) {
					index = line1.indexOf(" ");
				} else {
					index = line1.indexOf(">");
				}

				target_string = target_string.substring(0, index - 1);

				// String target_string = line1.substring(0,5);
				if ((target_string.equals("ATTR")
						|| target_string.equals("ENTITY")
						|| target_string.equals("RELATION") 
						|| target_string.equals("INDEX")) && line1.contains("P-NAME")) {
					// 変更処理を以下で行う
					// 指定した文字より後ろの文字取り出し
					index = line1.indexOf("P-NAME=\"");
					index += "P-NAME=\"".length();
					String target = line1.substring(index);
					String pre_target;

					index = target.indexOf("\"");
					target = target.substring(0, index);
					target = target.replaceAll(" ", "");
					target = target.replaceAll("\"", "");
					pre_target = target;
					int flag = 0;
					int LargeAlfaCount=0;
					
					//targetが辞書のリストに含まれていたら置換する
					for (int j = 0; j < doc_max; j++) {
						StringBuilder sb = new StringBuilder();
						if (target.contains(doc[j].getTerm())) {
							//JobTypeの処理//
							for (int i = 0; i < hm.get(doc[j].getTerm()).length(); i++) {
								// 文字が英大文字であったら
								if (Character.isUpperCase(hm.get(doc[j].getTerm()).charAt(i))) {
									if (LargeAlfaCount > 0) {
										sb.append("_" + hm.get(doc[j].getTerm()).charAt(i));
									} 
									else {
										sb.append(hm.get(doc[j].getTerm()).charAt(i));
									}
									LargeAlfaCount++;
								} 
								else {//小文字だったら
									sb.append(hm.get(doc[j].getTerm()).charAt(i));
								}
								//JobTypeの処理終了//
							}//end for
							String new_name = new String(sb);
							target = target.replaceAll(doc[j].getTerm(),"_"+new_name);
							flag = 1;//変更があった
						}
					}//end for 

							target = target.replaceAll("__", "_");
							if (target.substring(0,1).equals("_")) {
								target = target.substring(1);
							}
							if(target.substring(target.length()-1).equals("_"))
							{
								target = target.substring(0,target.length()-1);
							}
					System.out.println(target);

						String new_target = target;
						new_target = new_target.toUpperCase();
						new_target = new_target.replaceAll("__", "_");
						// 英語に変換終了
						// 変更し，書き出す
						String pName = "P-NAME=\"" + new_target + "\"";
						new_line = line.replaceAll(
								"P-NAME=\"" + pre_target + "\"", pName);

						pw.println(new_line);
					//}//flag
				}// end もし<ATTR
				else {
					pw.println(line);
				}
			}// end while readline
			pw.close();
		} catch (Exception e) { // ファイル読み込みエラー時の処理
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); // プログラムの終了
		}
	}

	void kadai(String inf, String of, Document[] doc) {
		HashMap<String, String> dic_table; // 英語-日本語の辞書を格納するHashMap
		dic_table = new HashMap<String, String>(101); // dic_tableのインスタンス化
		// 辞書をdic_tableに格納する
		readDic(dic_table, doc);
		// インプットファイルを読み込む
		readFile(dic_table, inf, doc, of);

	}

	public static void main(String[] args) {
		TIS20140227 tis = new TIS20140227(args[0]);
		tis.kadai(tis.data_file, tis.out_file, tis.doc);
	}

} // class HashMapSample3 end

// 辞書を表すクラス
class Document implements Comparable<Document> {
	private String term; // 文書名
	private int term_len; // 文書ベクトルの大きさ

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
		} else if (s1 < s2) {
			return 1;
		} else {
			return -1;
		}
	}
}