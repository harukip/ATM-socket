import java.io.*;
import java.net.*;
import java.util.*;
import java.io.Console;

public class client
{
    public static void main( String[] args ) throws IOException
    {
    	String select = "";
        String hostIP = "";
        String account ="",account2 = "",password = "",read ="" ,balance = "",oldbalance = "",newbalance = "";
        Console cnsl = null;
        int port ,transfermoney;
        Socket client = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("�п�JServer�ݦ�}:");
        hostIP = br.readLine();
        System.out.print("�п�JServer��port:");
        port = Integer.parseInt(br.readLine());
        cnsl = System.console();
  /*------------------�U���}�l�إ߳s�u--------------------*/
        try
        {
        	//�s�u
            client = new Socket( hostIP, port );
            DataInputStream input = null;
            DataOutputStream output = null;
            try
            {
            	// input/output ���O�O �qserver�ݱ����H��/client�ǰe�T��
                input = new DataInputStream( client.getInputStream() );
                output = new DataOutputStream( client.getOutputStream() );
                while ( true )
                {
                    System.out.print("�п�J�b��:");
                    account = br.readLine();
					output.writeUTF(account) ;//client�N�T���ǵ�serverr
					output.flush();
					System.out.println("�b���ǰe! ����SERVER�^��!");
					read = input.readUTF() ; //Ū�Jserver�ǨӪ��T��
                    if(read.equals("exist"))
                    {
                    	System.out.println("�b���s�b!");
                    	while(true)
                    	{
                    			
						char[] pwd = cnsl.readPassword("�п�J�K�X:");
						password = String.valueOf(pwd);
						output.writeUTF(password) ;
						output.flush();
						System.out.println("�K�X�ǰe! ����SERVER�^��!");
						read =  input.readUTF() ;
						System.out.println();
							if(read.equals("Success"))
							{
								System.out.println("�K�X���T!");
								while(true)
								{
										System.out.print("1.�l�B�d�� 2.�s�� 3.��b 4.����\n�п�ܥ��@���\��:");
										select = br.readLine();
										output.writeUTF(select) ;
										output.flush();
											if(select.equals("1") )
											{
												read = input.readUTF();
												
												balance = input.readUTF();
												System.out.println( "�b��: "+account + " �Ѿl�l�B: "+balance+" ��");
												System.out.println();
											}
											else if( select.equals("2") )
											{
												int money;
												read = input.readUTF();
												System.out.print("��J�s�ڪ��B:");
												money = Integer.parseInt(br.readLine());
												while(!(money > 0))
												{
													money = Integer.parseInt(br.readLine());
												}
												output.writeUTF(String.valueOf(money)) ;
												output.flush();
												read = input.readUTF();
												System.out.println("�쥻�l�B: "+balance+" �b��l�B��s: " +read+ " ��" );
												System.out.println();
												
											}
											else if(select.equals("3"))
											{
												    read = input.readUTF();
													System.out.print("�п�J��b�b��: ");
													account2 = br.readLine();
													output.writeUTF(account2) ;
													output.flush();
													read = input.readUTF();
													System.out.println("Server�ݦ^��:"+ read );
														 if(read.equals("exist"))
														   {    
															    oldbalance = input.readUTF();
															 	System.out.println("��b�b��: "+account2+" �s�b!");
															 	System.out.println("�ثe'"+account+"'�b���l�B: "+oldbalance+ " ��" );
														     	System.out.print("�п�J��J�b��'"+account2+"'���B: ");
														     	transfermoney=Integer.parseInt(br.readLine());
														     	while(transfermoney > Integer.parseInt(oldbalance)){
														     		System.out.print("�W�L�b��l�B! ���s��J���B:");
														     		transfermoney=Integer.parseInt(br.readLine());
														     	}
																output.writeUTF(String.valueOf(transfermoney)) ;
																output.flush();
																newbalance = input.readUTF();
																System.out.println("Server�ݦ^��: "+"�b��: "+account +" �쥻�l�B: " +oldbalance + " ��X: "+transfermoney +" �s�l�B: "+(Integer.parseInt(oldbalance)-transfermoney));
																System.out.println("Server�ݦ^��: "+"�b��: "+account2 +" �쥻�l�B: "+newbalance + " ��J: "+transfermoney +" �s�l�B: "+(Integer.parseInt(newbalance)+transfermoney));
																System.out.println();
														   }
														 else
														   {
														    	 System.out.println("�b�����s�b! ��^���!");
														    	 System.out.println();
														   }
											}
											else if(select.equals("4"))
											{
												//����client�õ����{��
												client.close();
												System.exit(0);
											}
											else
											{
												System.out.print("���~�п�J�Ʀr1-4");
												;
											}
								     }
								}
								else if ( read.equals("quit"))
								{   
								    System.out.println("�K�X���~3�� �PSERVER�_�u! �{������!");
									System.exit(0);
								}
								else
									System.out.println("�K�X�����T!");
							}
	                    }
                    else if(read.equals("not exist"))
                    {
                    	System.out.println("�b�����s�b!");//��^��J�b������
                    	;
                    }
                }
            }
            catch ( IOException e )
            {
            }
            finally
            {
                if ( input != null )
                    input.close();
                if ( output != null )
                    output.close();
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( client != null )
                client.close();
            if ( br != null )
                br.close();
        }
    }
}