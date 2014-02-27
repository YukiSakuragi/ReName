import java.io.*;
import java.util.*; // HashMap ���g���ۂ�import

public class TIS20140227 {
	String data_file;
	String out_file;

	// �R���X�g���N�^
	TIS20140227(String dfile) {
		data_file = dfile;
		out_file = "term_frequency.txt";
	}
	
	//������Hash�Ɋi�[���郁�\�b�h
	void readDic(HashMap<String, String> hm) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream("C:/Users/seta/Desktop/�z�z��/����/dictionary.csv"), "UTF-8"));

			String line;
			while( (line = br.readLine() ) != null ) {
			    line = line.replaceAll("\"","");
				String[] data = line.split(","); //�f�[�^�𕪊�
				int data_num = data.length;
				
				// data[0]:���{��, data[1]:�p��
				//
				// �P��̏o���p�x�̏W�v
				//
				if( data_num == 2 ){ 
					hm.put( data[0], data[1] );
				}
				else { //���̒P������߂�HashMap�Ɋi�[����Ƃ�
					hm.put( data[0], "" );
				}
			} //end while
		}
		catch(Exception e) { // �t�@�C���ǂݍ��݃G���[���̏���
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //�v���O�����̏I��
		}
	}


	//�C���v�b�g�t�@�C���̏C���ӏ����p�ꂩ��p��̖��O��t���郁�\�b�h
	void readFile(HashMap<String, String> hm, String infile) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream("C:/Users/seta/Desktop/�z�z��/����/"+infile), "UTF-8"));

			System.out.println("*");
			String line;
			while( (line = br.readLine() ) != null ) {

			    int index = line.indexOf("<");
			    line = line.substring(index);
			    		
				//�ύX�ӏ������o
			    String target_string = line.substring(0,5);
			    if(target_string.equals("<ATTR")){
			    	//�ύX�������ȉ��ōs��
					String[] line_data = line.split(" ");
					int array_num = line_data.length;
					
					 // �w�肵�����������̕������o��
				    index = line_data[3].indexOf("P-NAME=\"");
				    index += "P-NAME=\"".length();
				    String target = line_data[3].substring(index);

				    index = target.indexOf("\"");
				    target = target.substring(0,index);
					
				    System.out.println(target);
				}
				//�ύX���Ȃ��ꍇ�͂��̂܂܏o�͂���
				else
				{
				
				}
				
				
			} //end while
		}
		catch(Exception e) { // �t�@�C���ǂݍ��݃G���[���̏���
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //�v���O�����̏I��
		}
	}


	void kadai(String inf, String of) {
		HashMap<String, String> dic_table; //�p��-���{��̎������i�[����HashMap
		dic_table = new HashMap<String, String>(101); // dic_table�̃C���X�^���X��
		//������dic_table�Ɋi�[����
		readDic(dic_table);
		//�C���v�b�g�t�@�C����ǂݍ���
		readFile(dic_table, inf);
		
	}


	public static void main(String[] args) {
		TIS20140227 tis = new TIS20140227(args[0]);

		tis.kadai(tis.data_file, tis.out_file);
	}

} //class HashMapSample3 end