import java.net.*;
import java.text.NumberFormat;
import java.io.*;
import java.util.Scanner;

public class Server {

    public static int port = 3000; // �s����
    
	public static void main(String[] args) throws IOException {
	    ServerSocket ss = new ServerSocket(port);     // �إ� TCP ���A���C
		while(true){
	        System.out.println("���ݫإ߳s�u");
	        Socket sc = ss.accept();                // ������J�T���C���H�n��A�إ�socket,�N��accept�ʧ@
	        System.out.println("�إ߳s�u");
	        Thread td = new Thread( new Server_td(sc));
	        System.out.println("�}�l����");
	        td.start();
		}
	}

}

class Server_td extends Thread{
	static int client = 0;
	int errortime = 0, num;
	Socket s;
	String filename, filename2, password, password2, inpassword, send, select, money_str;
	File file;
	int money = 0, money2 = 0, tmp;
	Boolean account = false, retry = true;
	public Server_td(Socket s) {
		// TODO Auto-generated constructor stub
		this.s = s;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			DataOutputStream os = new DataOutputStream(s.getOutputStream());	// ���o��X��y�C
			DataInputStream is = new DataInputStream(s.getInputStream());
			while(!account){	//��b�����s�b�h���ư���
				filename = is.readUTF();
				file = new File("./" + filename + ".txt");
				if(file.exists()){
					send = "exist";
					account = true;
				}
				else{
					send = "not exist";
				}
				os.writeUTF(send);
				os.flush();
			}
			client++;
			num = client;
			System.out.println("Client " + num + " �n�J�b���G" + filename);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			password = br.readLine();
			money_str = br.readLine();
			br.close();
			while(errortime < 2){	//�K�X��J���~�̦h�T��
				inpassword = is.readUTF();
				if(inpassword.equals(password)) break;
				else{
					errortime++;
					send = "Wrong";
					os.writeUTF(send);
					os.flush();
				}
			}
			if(errortime == 2){
				send = "quit";
				os.writeUTF(send);
				os.flush();
				is.close();
				os.close();
				s.close();
				client--;
				System.out.println("Client " + num + "���u");
			}
			else{
				send = "Success";
				os.writeUTF(send);
				os.flush();
				money = Integer.parseInt(money_str);
				while(retry){
					select = is.readUTF();
					switch (select) {
					case "1":
						send = "good";
						os.writeUTF(send);
						os.flush();
						// Check money
						FileReader frc = new FileReader(file);
						BufferedReader brc = new BufferedReader(frc);
						brc.readLine();
						money_str = brc.readLine();
						brc.close();
						money = Integer.parseInt(money_str);
						send = String.valueOf(money);
						System.out.println("Client " + num + " �d�߾l�B�G" + money);
						os.writeUTF(send);
						os.flush();
						break;
					case "2":
						send = "good";
						os.writeUTF(send);
						os.flush();
						// Save_money
						FileReader frs = new FileReader(file);
						BufferedReader brs = new BufferedReader(frs);
						brs.readLine();
						money_str = brs.readLine();
						brs.close();
						money = Integer.parseInt(money_str);
						tmp = Integer.parseInt(is.readUTF());
						money += tmp;
						System.out.println("Client " + num + " �s�J���B�G"+ tmp +" �l�B�G" + money);
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(password);
						bw.newLine();
						bw.write(String.valueOf(money));
						bw.flush();
						bw.close();
						os.writeUTF(String.valueOf(money));
						os.flush();
						break;
					case "3":
						send = "good";
						os.writeUTF(send);
						os.flush();
						// Transfer
						FileReader frt = new FileReader(file);
						BufferedReader brt = new BufferedReader(frt);
						brt.readLine();
						money_str = brt.readLine();
						brt.close();
						money = Integer.parseInt(money_str);
						filename2 = is.readUTF();
						File file2 = new File("./" + filename2 + ".txt");
						if(file2.exists()){
							send = "exist";
							os.writeUTF(send);
							os.flush();
							send = String.valueOf(money);
							os.writeUTF(send);
							os.flush();
							FileReader fr2 = new FileReader(file2);
							BufferedReader br2 = new BufferedReader(fr2);
							password2 = br2.readLine();
							money2 = Integer.parseInt(br2.readLine());
							br2.close();
							tmp = Integer.parseInt(is.readUTF());
							System.out.println(filename+"�l�B��"+money+"��, ��b"+tmp+"����"+filename2+" ��,�l�B��"+(money-tmp)+"��. "+filename2+"�쥻�l�B��"+money2+"��, ����"+filename+"��b��"+tmp+"����,�l�B��"+(money2+tmp)+"��");
							send = String.valueOf(money2);
							os.writeUTF(send);
							os.flush();
							money -= tmp;
							money2 += tmp;
							FileWriter fwt = new FileWriter(file);
							FileWriter fw2 = new FileWriter(file2);
							BufferedWriter bwt = new BufferedWriter(fwt);
							BufferedWriter bw2 = new BufferedWriter(fw2);
							bwt.write(password);
							bw2.write(password2);
							bwt.newLine();
							bw2.newLine();
							bwt.write(String.valueOf(money));
							bw2.write(String.valueOf(money2));
							bwt.flush();
							bwt.close();
							bw2.flush();
							bw2.close();
						}
						else{
							send = "not exist";
							os.writeUTF(send);
							os.flush();
						}
						break;
					case "4":
						send = "good";
						os.writeUTF(send);
						os.flush();
						retry = false;
						break;
					default:
						send = "Error";
						os.writeUTF(send);
						os.flush();
						break;
					}
				}
				is.close();
				os.close();
				br.close();
				s.close();
				client--;
				System.out.println("Client " + num + "���u");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        System.out.println("error");
		}
	}
}
