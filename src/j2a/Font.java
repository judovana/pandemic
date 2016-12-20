package j2a;

/**
 *
 * @author jvanek
 */
public interface Font {

    public Font deriveFont(int mod);
            
    public static int getBOLD(){
        return  j2a.java.Font.getBOLD();
    }
    
    public Object getOriginal(); 
    
}
