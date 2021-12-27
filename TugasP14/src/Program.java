import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Program {
    public static Integer nopegawai;
    public static String nama;
    public static Integer nojabatan;
    public static Double gajipokok;
    public static Integer harimasuk;
    public static Double potongan;
    public static Double totalgaji;
    public static String jabatan;
    static Connection conn;
    public static void main(String[] args) {
        try (Scanner terimaInput = new Scanner (System.in)) {
            String pilihanUser;
            boolean isLanjutkan = true;

            String url = "jdbc:mysql://localhost:3306/ptabc";
            try {
            	Class.forName("com.mysql.cj.jdbc.Driver");
            	conn = DriverManager.getConnection(url,"root","");
            	System.out.println("Class Driver ditemukan");
            	
            	while (isLanjutkan) {
            		System.out.println("\n------------------");
            		System.out.println("Database Pegawai");
            		System.out.println("------------------");
            		System.out.println("1. Lihat Data Pegawai");
            		System.out.println("2. Tambah Data Pegawai");
            		System.out.println("3. Ubah Data Pegawai");
            		System.out.println("4. Hapus Data Pegawai");
            		System.out.println("5. Cari Data Pegawai");
            		
            		System.out.print("\nPilihan anda (1/2/3/4/5): ");
            		pilihanUser = terimaInput.next();
            		
            		switch (pilihanUser) {
            			case "1":
            				lihatdata();
            				break;
            			case "2":
            				tambahdata();
            				break;
            			case "3":
            				ubahdata();
            				break;
            			case "4":
            				hapusdata();
            				break;
            			case "5":
            				caridata();
            				break;
            			default:
            				System.err.println("\nInput anda tidak ditemukan\nSilakan pilih [1-5]");
            		}
            		
            		System.out.print("\nApakah Anda ingin melanjutkan [y/n]? ");
            		pilihanUser = terimaInput.next();
            		isLanjutkan = pilihanUser.equalsIgnoreCase("y");
            	}
            	System.out.println("\nBye.... Selamat Berjumpa Kembali!!!");
            	
            }
            catch(ClassNotFoundException ex) {
            	System.err.println("Driver Error");
            	System.exit(0);
            }
            catch(SQLException e){
            	System.err.println("Tidak berhasil koneksi");
            }
        }
	}
    private static void lihatdata() throws SQLException {
		String text1 = "\n===Daftar Seluruh Data Pegawai===";
		System.out.println(text1.toUpperCase());
						
		String sql ="SELECT * FROM pegawai";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()){
			System.out.print("\nNo. Pegawai\t\t: ");
            System.out.print(result.getInt("nopegawai"));
            System.out.print("\nNama\t\t\t: ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t\t: ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nGaji pokok\t\t: ");
            System.out.print(result.getDouble("gajipokok"));
            System.out.print("\nJumlah hari masuk\t: ");
            System.out.print(result.getInt("harimasuk"));
            System.out.print("\nPotongan\t\t: ");
            System.out.print(result.getDouble("potongan"));
            System.out.print("\nTotal gaji\t\t: ");
            System.out.print(result.getDouble("totalgaji"));
            System.out.print("\n");
		}
	}
    private static void tambahdata() throws SQLException{
		String text2 = "\n===Tambah Data Pegawai===";
		System.out.println(text2.toUpperCase());
		
		try (Scanner terimaInput = new Scanner (System.in)) {
            try {
            
                System.out.print("No. pegawai\t\t: ");
                nopegawai = terimaInput.nextInt();
                System.out.print("Nama\t\t\t: ");
                String nama = terimaInput.next();
                System.out.println("\n==Pilihan jabatan==");
                System.out.println("1 jika anda seorang direksi");
                System.out.println("2 jika anda seorang direktur");
                System.out.println("3 jika anda seorang manajer");
                System.out.println("4 jika anda seorang karyawan");
                System.out.println("5 jika anda seorang office boy");
                
                do{
                    System.out.println("Jabatan\t\t\t: ");
                    nojabatan= terimaInput.nextInt();
                }
                while(nojabatan<1||nojabatan>5);
                
                if(nojabatan==1){
                    jabatan="Direksi";  
                }else if(nojabatan==2){
                    jabatan="Direktur";  
                }else if(nojabatan==3){
                    jabatan="Manajer";  
                }else if(nojabatan==4){
                    jabatan="Karyawan";  
                }else if(nojabatan==5){
                    jabatan="Office Boy";  
                }
                
                do{
                    System.out.println("Jumlah hari masuk\t: ");
                        harimasuk= terimaInput.nextInt();
                }while(harimasuk<1||harimasuk>30);
   
                switch(nojabatan){
                    case 1: gajipokok=5000000.0; break;
                    case 2: gajipokok=4000000.0; break;
                    case 3: gajipokok=3000000.0; break;
                    case 4: gajipokok=2000000.0; break;
                    case 5: gajipokok=1000000.0; break;
                    default: System.out.println("Masukkan angka yang sesuai");
                } 
                potongan = ((double)(30-harimasuk)/30)*gajipokok;
                totalgaji = gajipokok-potongan;
            
            String sql = "INSERT INTO pegawai (nopegawai, nama, jabatan, gajipokok, harimasuk, potongan, totalgaji) VALUES ('"+nopegawai+"','"+nama+"','"+jabatan+"','"+gajipokok+"','"+harimasuk+"','"+potongan+"','"+totalgaji+"')";
            			
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Berhasil input data");

            } catch (SQLException e) {
                System.err.println("Terjadi kesalahan input data");
            } catch (InputMismatchException e) {
            	System.err.println("Inputlah dengan angka saja");
            }
        }
	}
    private static void ubahdata() throws SQLException{
		String text3 = "\n===Ubah Data Pegawai===";
		System.out.println(text3.toUpperCase());
		
		try (Scanner terimaInput = new Scanner (System.in)) {
            try {
                lihatdata();
                System.out.print("Masukkan No Pegawai yang akan di ubah atau update : ");
                Integer nopegawai = Integer.parseInt(terimaInput.nextLine());
                
                String sql = "SELECT * FROM pegawai WHERE nopegawai = " +nopegawai;
                
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);
                
                if(result.next()){
                    
                    System.out.print("\nNama ["+result.getString("nama")+"]\t\t: ");
                        nama = terimaInput.nextLine();
                        
                        System.out.println("\n==Pilihan jabatan==");
                        System.out.println("1 jika anda seorang direksi");
                        System.out.println("2 jika anda seorang direktur");
                        System.out.println("3 jika anda seorang manajer");
                        System.out.println("4 jika anda seorang karyawan");
                        System.out.println("5 jika anda seorang office boy");
                        
                        do{
                            System.out.println("Jabatan["+result.getString("jabatan")+"]\t: ");
                                nojabatan= terimaInput.nextInt();
                        }while(nojabatan<1||nojabatan>5);
                     
                        if(nojabatan==1){
                            jabatan="Direksi";  
                        }else if(nojabatan==2){
                            jabatan="Direktur";  
                        }else if(nojabatan==3){
                            jabatan="Manajer";  
                        }else if(nojabatan==4){
                            jabatan="Karyawan";  
                        }else if(nojabatan==5){
                            jabatan="Office Boy";  
                        }
   
                        switch(nojabatan){
                            case 1: gajipokok=5000000.0; break;
                            case 2: gajipokok=4000000.0; break;
                            case 3: gajipokok=3000000.0; break;
                            case 4: gajipokok=2000000.0; break;
                            case 5: gajipokok=1000000.0; break;
                            default: System.out.println("Masukkan angka yang sesuai");
                        } 
                      
                        do{
                            System.out.println("Jumlah hari masuk["+result.getInt("harimasuk")+"]\t: ");
                                harimasuk= terimaInput.nextInt();
                        }while(harimasuk<1||harimasuk>30);
   
                        potongan = ((double)(30-harimasuk)/30)*gajipokok;
                        totalgaji = gajipokok-potongan;
                       
                    sql = "UPDATE pegawai SET nama='"+nama+"',jabatan= '"+jabatan+"',gajipokok= '"+gajipokok+"',harimasuk= '"+harimasuk+"',potongan= '"+potongan+"',totalgaji= '"+totalgaji+"' WHERE nopegawai='"+nopegawai+"'";

                    if(statement.executeUpdate(sql) > 0){
                        System.out.println("Berhasil memperbaharui data pegawai (No pegawai "+nopegawai+")");
                    }
                }
                statement.close();        
            } catch (SQLException e) {
                System.err.println("Terjadi kesalahan dalam mengedit data");
                System.err.println(e.getMessage());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
	}
    private static void hapusdata() {
		String text4 = "\n===Hapus Data Pegawai===";
		System.out.println(text4.toUpperCase());
		
		try (Scanner terimaInput = new Scanner (System.in)) {
            try{
                lihatdata();
                System.out.print("Ketik No. pegawai yang akan Anda Hapus : ");
                Integer nopegawai= Integer.parseInt(terimaInput.nextLine());
                
                String sql = "DELETE FROM pegawai WHERE nopegawai = "+ nopegawai;
                Statement statement = conn.createStatement();
                
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil menghapus data pegawai (No pegawai "+nopegawai+")");
                }
   }catch(SQLException e){
                System.out.println("Terjadi kesalahan dalam menghapus data pegawai");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
	}
    private static void caridata () throws SQLException {
		String text5 = "\n===Cari Data Pegawai===";
		System.out.println(text5.toUpperCase());
		
		try (Scanner terimaInput = new Scanner (System.in)) {
            System.out.print("Masukkan Nama Pegawai : ");
            
            String keyword = terimaInput.nextLine();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM pegawai WHERE nama LIKE '%"+keyword+"%'";
            ResultSet result = statement.executeQuery(sql); 
                    
            while(result.next()){
            	System.out.print("\nNo Pegawai\t\t: ");
                    System.out.print(result.getInt("nopegawai"));
                    System.out.print("\nNama\t\t\t: ");
                    System.out.print(result.getString("nama"));
                    System.out.print("\nJabatan\t\t\t: ");
                    System.out.print(result.getString("jabatan"));
                    System.out.print("\nGaji pokok\t\t: ");
                    System.out.print(result.getDouble("gajipokok"));
                    System.out.print("\nJumlah hari masuk\t: ");
                    System.out.print(result.getInt("harimasuk"));
                    System.out.print("\nPotongan\t\t: ");
                    System.out.print(result.getDouble("potongan"));
                    System.out.print("\nTotal gaji\t\t: ");
                    System.out.print(result.getDouble("totalgaji"));
                System.out.print("\n");
            }
        }
	}
}