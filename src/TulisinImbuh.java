
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class TulisinImbuh {
    private Image imgFont=null;
    private int idxFont[];
    
    public TulisinImbuh(){

        init("/font/imbuh.png");
    
    }
    
    private void init(String s){
        Vector vt = new Vector();
        
        imgFont = getImage(s);
        int rgb[] = new int[imgFont.getWidth()*imgFont.getHeight()];
        
        imgFont.getRGB(rgb, 0, imgFont.getWidth(), 0, 0, imgFont.getWidth(), imgFont.getHeight());
        
        // index char
        for (int i=0; i<rgb.length; i++){
            if (rgb[i]==0xff0000ff)
                vt.addElement(Integer.toString(i+1));
            else if (rgb[i]==0xffff0000){
                vt.addElement(Integer.toString(i+1));
                break;
            }
        }

        idxFont = new int[vt.size()];
        for(int i=0; i<vt.size(); i++)
            idxFont[i] = Integer.parseInt((String)vt.elementAt(i));
        
    }
    
    // Nyandak gambar
    private static Image getImage(String s){
        Image img = null;
        
        try{
            img = Image.createImage(s);
        } catch (Exception e) { }
        
        return img;
    }
    
    public int drawChar(Graphics g, char c, int x, int y){
        
        int w, i = charIdx(c);
        
        w = idxFont[i+1]-idxFont[i];
        
        g.drawRegion(imgFont, idxFont[i], 1, w, imgFont.getHeight()-1, Sprite.TRANS_NONE, x, y, g.LEFT | g.TOP);
        
        return w;
    }
    
    public int charWidth(int c){
        int i = charIdx(c);
        return (idxFont[i+1]-idxFont[i]);
    }
    
    private int charIdx(int c){

        switch (c){
            case '\u1b80': return 0;
            case '\u1b81': return 1;
            case '\u1b82': return 2;
            case '\u1ba1': return 3;
            case '\u1ba2': return 4;
            case '\u1ba3': return 5;
            case '\u1ba4': return 6;
            case '\u1ba5': return 7;
            case '\u1ba6': return 8; 
            case '\u1ba7': return 9;
            case '\u1ba8': return 10;
            case '\u1ba9': return 11;
            case '\u1baa': return 12;
            default: return 0;
        }
        
    }
    
}
