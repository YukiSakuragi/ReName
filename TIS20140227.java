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
	
	void readDic(HashMap<String, Integer> hm) {
		try
		{
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
											new FileInputStream(), "UTF-8"));

			String line;
			while( (line = br.readLine() ) != null ) {
				String[] data = line.split("\t"); //�f�[�^�𕪊�
				// data[0]:�P��, data[1]:�i���Ȃǂ̉�͏��
				//
				// �P��̏o���p�x�̏W�v
				//
     String targetType = line.split("[\t|,]")[1];

			if(targetType.equals("����") || targetType.equals("����")){
				if( hm.containsKey( data[0] ) ){ //���łɂ��̒P�ꂪ�i�[����Ă���Ƃ�
					int hm_value =  hm.get(data[0]).intValue();
					int new_value = hm_value + 1;
					hm.put( data[0], new Integer(new_value) );
				}
				else { //���̒P������߂�HashMap�Ɋi�[����Ƃ�
					hm.put( data[0], 1 );
				}}
			} //end while
		}
		catch(Exception e) { // �t�@�C���ǂݍ��݃G���[���̏���
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //�v���O�����̏I��
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
				String[] data = line.split("\t"); //�f�[�^�𕪊�
				// data[0]:�P��, data[1]:�i���Ȃǂ̉�͏��
				//
				// �P��̏o���p�x�̏W�v
				//
     String targetType = line.split("[\t|,]")[1];

			if(targetType.equals("����") || targetType.equals("����")){
				if( hm.containsKey( data[0] ) ){ //���łɂ��̒P�ꂪ�i�[����Ă���Ƃ�
					int hm_value =  hm.get(data[0]).intValue();
					int new_value = hm_value + 1;
					hm.put( data[0], new Integer(new_value) );
				}
				else { //���̒P������߂�HashMap�Ɋi�[����Ƃ�
					hm.put( data[0], 1 );
				}}
			} //end while
		}
		catch(Exception e) { // �t�@�C���ǂݍ��݃G���[���̏���
			System.out.println("I/O error at readFile \n" + e);
			System.exit(1); //�v���O�����̏I��
		}
	}


	// HashMap�̓��e���t�@�C���ɏo��
	void outputHashMap(HashMap<String, Integer> hm, String of) {
		Set keys = hm.keySet();

		try {
			FileWriter fw = new FileWriter( of );
			BufferedWriter bw = new BufferedWriter( fw );
			PrintWriter pw = new PrintWriter( bw );

			for( Iterator itr = keys.iterator(); itr.hasNext(); ) {
				String exp = (String)itr.next();
				String value = Integer.toString(hm.get(exp));
				pw.println(exp + ": " + value);// ������������o��
				pw.flush();
			}
			pw.close(); // �o�̓X�g���[���̃N���[�Y
		}
		catch (IOException e) { // �t�@�C���������݃G���[���̏���
			System.out.println("I/O error at outputHashMap \n" + e);
			System.exit(1); //�v���O�����̏I��
		}
	}


	void sumup(String inf, String of) {
		HashMap<String, String> dic_table; //�p��-���{��̎������i�[����HashMap
		dic_table = new HashMap<String, String>(101); // dic_table�̃C���X�^���X��
		//������dic_table�Ɋi�[����
		readDic(dic_table);
		
		//�C���v�b�g�t�@�C����ǂݍ���
		readFile(dic_table, inf);
		outputHashMap(tf_table, of); //tf_table(HashMap)�̓��e���o��
	}


	public static void main(String[] args) {
		TIS20140227 tis = new TIS20140227(args[0]);

		tis.sumup(hms.data_file, hms.out_file);
	}

} //class HashMapSample3 end
