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
        System.out.print("請輸入Server端位址:");
        hostIP = br.readLine();
        System.out.print("請輸入Server端port:");
        port = Integer.parseInt(br.readLine());
        cnsl = System.console();
  /*------------------下面開始建立連線--------------------*/
        try
        {
        	//連線
            client = new Socket( hostIP, port );
            DataInputStream input = null;
            DataOutputStream output = null;
            try
            {
            	// input/output 分別是 從server端接收信息/client傳送訊息
                input = new DataInputStream( client.getInputStream() );
                output = new DataOutputStream( client.getOutputStream() );
                while ( true )
                {
                    System.out.print("請輸入帳號:");
                    account = br.readLine();
					output.writeUTF(account) ;//client將訊息傳給serverr
					output.flush();
					System.out.println("帳號傳送! 等待SERVER回應!");
					read = input.readUTF() ; //讀入server傳來的訊息
                    if(read.equals("exist"))
                    {
                    	System.out.println("帳號存在!");
                    	while(true)
                    	{
                    			
						char[] pwd = cnsl.readPassword("請輸入密碼:");
						password = String.valueOf(pwd);
						output.writeUTF(password) ;
						output.flush();
						System.out.println("密碼傳送! 等待SERVER回應!");
						read =  input.readUTF() ;
						System.out.println();
							if(read.equals("Success"))
							{
								System.out.println("密碼正確!");
								while(true)
								{
										System.out.print("1.餘額查詢 2.存款 3.轉帳 4.結束\n請選擇任一項功能:");
										select = br.readLine();
										output.writeUTF(select) ;
										output.flush();
											if(select.equals("1") )
											{
												read = input.readUTF();
												
												balance = input.readUTF();
												System.out.println( "帳號: "+account + " 剩餘餘額: "+balance+" 元");
												System.out.println();
											}
											else if( select.equals("2") )
											{
												int money;
												read = input.readUTF();
												System.out.print("輸入存款金額:");
												money = Integer.parseInt(br.readLine());
												while(!(money > 0))
												{
													money = Integer.parseInt(br.readLine());
												}
												output.writeUTF(String.valueOf(money)) ;
												output.flush();
												read = input.readUTF();
												System.out.println("原本餘額: "+balance+" 帳戶餘額更新: " +read+ " 元" );
												System.out.println();
												
											}
											else if(select.equals("3"))
											{
												    read = input.readUTF();
													System.out.print("請輸入轉帳帳號: ");
													account2 = br.readLine();
													output.writeUTF(account2) ;
													output.flush();
													read = input.readUTF();
													System.out.println("Server端回應:"+ read );
														 if(read.equals("exist"))
														   {    
															    oldbalance = input.readUTF();
															 	System.out.println("轉帳帳號: "+account2+" 存在!");
															 	System.out.println("目前'"+account+"'帳號餘額: "+oldbalance+ " 元" );
														     	System.out.print("請輸入轉入帳號'"+account2+"'金額: ");
														     	transfermoney=Integer.parseInt(br.readLine());
														     	while(transfermoney > Integer.parseInt(oldbalance)){
														     		System.out.print("超過帳戶餘額! 重新輸入金額:");
														     		transfermoney=Integer.parseInt(br.readLine());
														     	}
																output.writeUTF(String.valueOf(transfermoney)) ;
																output.flush();
																newbalance = input.readUTF();
																System.out.println("Server端回應: "+"帳號: "+account +" 原本餘額: " +oldbalance + " 轉出: "+transfermoney +" 新餘額: "+(Integer.parseInt(oldbalance)-transfermoney));
																System.out.println("Server端回應: "+"帳號: "+account2 +" 原本餘額: "+newbalance + " 轉入: "+transfermoney +" 新餘額: "+(Integer.parseInt(newbalance)+transfermoney));
																System.out.println();
														   }
														 else
														   {
														    	 System.out.println("帳號不存在! 返回選單!");
														    	 System.out.println();
														   }
											}
											else if(select.equals("4"))
											{
												//關閉client並結束程式
												client.close();
												System.exit(0);
											}
											else
											{
												System.out.print("錯誤請輸入數字1-4");
												;
											}
								     }
								}
								else if ( read.equals("quit"))
								{   
								    System.out.println("密碼錯誤3次 與SERVER斷線! 程式關閉!");
									System.exit(0);
								}
								else
									System.out.println("密碼不正確!");
							}
	                    }
                    else if(read.equals("not exist"))
                    {
                    	System.out.println("帳號不存在!");//返回輸入帳號重來
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