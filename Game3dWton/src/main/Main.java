package main;

public class Main {

    public static void main(String[] args) {
    
        try {
            LibraryLoader.loadNativeLibraries();            
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        
        new GamePanel().start(); 
    }
}
